# Dinamik Senaryo Üretim ve Doğrulama Mimarisi (Scenario-Defense)

İnsansız Hava Aracı (İHA) savunma görevlerinde (Saldırı, Keşif, Sürü, Devriye) dinamik, ölçeklenebilir ve operasyonel kural kısıtlarına uygun senaryoların üretilmesi ve doğrulanması amacıyla tasarım kalıpları destekli olarak geliştirilmiş nesne yönelimli Java projesidir.

> **Not:** Bu çalışma; mimari tasarımı, kural tabanlı doğrulama yapısını ve tek JVM sürecinde yürütülen sınırlı bir ön başarım karşılaştırmasını sunan bir ön incelemedir.

---

## 1. Mimari Tasarım ve Kullanılan Kalıplar

Sistemde nesne oluşturma ve yönetim süreçleri dört temel yazılım tasarım kalıbının entegrasyonu ile kurgulanmıştır:

* **Abstract Factory (`IMissionComponentFactory`):** Görev türüne (`ATTACK`, `RECON`, `SWARM`, `PATROL`) uygun İHA, sensör ve mühimmat bileşenlerini soyutlayarak üretir.
* **Prototype (`Scenario.clone()`, `UAV.clone()`):** Somut fabrikalarda ve senaryo yöneticisinde tanımlı prototip nesnelerin klonlanarak çoğaltılmasını sağlar.
* **Builder (`ScenarioBuilder`):** Karmaşık senaryo nesnelerinin adım adım ve esnek bir biçimde inşa edilmesini yönetir.
* **Singleton (`ScenarioManager`):** Sistem genelinde senaryo üretimine tekil bir erişim noktası sunar.

---

## 2. Kural Doğrulama (`ScenarioValidator`)

`ScenarioValidator` sınıfı, üretilen senaryoların operasyonel kısıtlara uygunluğunu `validate(Scenario)` metodu üzerinden 5 temel kontrol ile denetler:

1. **Senaryo Varlık Kontrolü:** Senaryo nesnesinin `null` olmaması.
2. **İHA Listesi Kontrolü:** İHA listesinin (`uavs`) `null` veya boş olmaması.
3. **Hedef Listesi Kontrolü:** Hedef listesinin (`targets`) `null` veya boş olmaması.
4. **Görev Varlık Kontrolü:** Görev (`Mission`) nesnesinin `null` olmaması.
5. **Keşif Mühimmat Kısıtı:** `RECON` görev türündeki senaryolarda hiçbir İHA'nın mühimmat taşımaması.

### Doğrulama ve Birim Test Kapsamı
* **Negatif Test Vakaları (`RuleViolationAnalysisTest`):** İHA'sız senaryo, hedefsiz senaryo, görevsiz senaryo ve mühimmatlı keşif İHA'sı durumları `assertFalse` ile test edilerek doğrulayıcının kural ihlallerini yakaladığı doğrulanmıştır.
* **Birim Stres Testi (`ScenarioValidatorTest`):** Tek bir senaryo içerisine 10.000 adet hedef eklenerek doğrulayıcının büyük veri yapılarında istisna fırlatmadan çalıştığı (`assertDoesNotThrow`) test edilmiştir.
* **Prototip Testi (`ScenarioPrototypeTest`):** Klonlanan senaryolarda üst seviye İHA kimliği (`ID`) değişikliğinin diğer örneğe yansımadığı doğrulanmıştır.

---

## 3. Performans Ön İncelemesi ve Deney Düzeni

Eşit iş yükü altında sıfırdan nesne inşası (`Baseline - new`) ile klonlama (`Prototype - clone()`) yöntemleri karşılaştırılmıştır.

### Test Ortamı ve Parametreler
* **Donanım:** 12th Gen Intel(R) Core(TM) i7-1255U (10 çekirdek, 12 mantıksal işlemci, 1,70 GHz), 32 GB RAM
* **Çalışma Zamanı:** OpenJDK 21.0.1 (Windows)
* **JVM Parametreleri:** `-Xms1024m -Xmx1024m`
* **Ölçüm Kurulumu:** 20.000 iterasyon JVM ısınma (warm-up), 30 tekrar üzerinden ortalama ($\mu$) ve standart sapma ($\sigma$) hesabı. Her turda üretilen her senaryo için 1 üretim ve 1 `ScenarioValidator.validate()` çağrısı yürütülmüştür.

### Ölçüm Sonuçları (`PerformanceBenchmarkTest.runFullBenchmarkSuite`)

| Ölçek ($N$) | Yöntem | Süre (ms) | Throughput (senaryo/sn) | Bellek $\Delta$ (MB) | Geçerlilik Oranı |
| :--- | :--- | :---: | :---: | :---: | :---: |
| **100** | Baseline (`new`) | 0,12 ± 0,11 | 829.072,82 | 0,07 ± 0,02 | %100 |
| **100** | Prototype (`clone`) | 0,20 ± 0,37 | 503.685,30 | 0,08 ± 0,01 | %100 |
| **1.000** | Baseline (`new`) | 0,10 ± 0,04 | 9.639.173,60 | 0,55 ± 0,02 | %100 |
| **1.000** | Prototype (`clone`) | 0,15 ± 0,18 | 6.579.957,45 | 0,55 ± 0,01 | %100 |
| **10.000** | Baseline (`new`) | 9,84 ± 6,53 | 1.015.904,32 | 5,44 ± 0,19 | %100 |
| **10.000** | Prototype (`clone`) | 12,78 ± 6,40 | 782.169,46 | 5,35 ± 0,13 | %100 |
| **100.000** | Baseline (`new`) | 44,92 ± 5,70 | 2.226.383,58 | 55,87 ± 11,47 | %100 |
| **100.000** | Prototype (`clone`) | 48,12 ± 7,64 | 2.077.963,96 | 53,82 ± 0,48 | %100 |

### Değerlendirme ve Sınırlılıklar
* Betimsel ortalamalarda $N \ge 10.000$ ölçeklerinde `Prototype` yönteminde ortalama çalışma süresinin daha yüksek, ortalama bellek farkının ise bir miktar daha düşük olduğu gözlenmiştir.
* Ölçümler tek JVM sürecinde gerçekleştirilmiş ve istatistiksel anlamlılık testi uygulanmamıştır; bu nedenle bulgular keşifsel niteliktedir ve genelleme iddiası taşımamaktadır.

---

## 4. Kurulum ve Çalıştırma

Projeyi yerel ortamınızda derlemek ve testleri çalıştırmak için Maven Wrapper kullanabilirsiniz:

```bash
# Projeyi derleme ve tüm birim/benchmark testlerini çalıştırma
./mvnw test
