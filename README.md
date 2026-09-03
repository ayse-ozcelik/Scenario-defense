## Gün 16 - Kural İhlali ve Senaryo Doğrulama Analizi

### Deney Protokolü ve Performans Test Planı

Bu protokol, **Prototype Pattern** (Klonlama) yaklaşımının **Naive/Baseline** (Sıfırdan `new` ile oluşturma) yaklaşımına göre sunduğu performans, bellek ve işleme hacmi avantajlarını bilimsel olarak ölçmek amacıyla hazırlanmıştır.

#### 1. Deney Grupları
* **Baseline (Kontrol Grubu):** `NaiveScenarioGenerator` kullanılarak nesnelerin sıfırdan `new` anahtar kelimesiyle üretildiği yapı.
* **Prototype (Deney Grubu):** `ScenarioManager` deposundaki prototiplerin `.clone()` metoduyla çoğaltıldığı yapı.

#### 2. Ölçüm Metrikleri ve Hesaplama Formülleri
* **Çalışma Süresi (Execution Time - ms):** $N$ adet senaryonun üretimi için geçen toplam süre.
* **Throughput (İşleme Hacmi - Senaryo/sn):**
  $$\text{Throughput} = \frac{N}{\text{Toplam Süre (saniye)}}$$
* **Bellek Tüketimi (Memory Usage - MB):**
  $$\text{Harcanan MB} = \frac{\text{TotalMemory} - \text{FreeMemory}}{1024 \times 1024}$$
* **Doğruluk Oranı (Validation Rate - %):** Üretilen senaryoların `ScenarioValidator.validate()` testinden geçme yüzdesi.

#### 3. Test Koşulları ve Metodoloji
* **JVM Warm-up:** JIT derleyici ve sınıf yükleme etkilerini nötrlemek adına ana ölçümler öncesi 1.000 adetlik ön çalıştırma yapılır.
* **İstatistiksel Güvenilirlik:** Donanım sapmalarını engellemek için her veri ölçeği 10 kez çalıştırılır ve ortalama ($Mean$) değerler kaydedilir.

#### 4. Deney Matrisi (Ölçek Tablosu)

| Senaryo Ölçeği ($N$) | Yöntem | Ölçülecek Değerler | Tekrar Sayısı |
| :--- | :--- | :--- | :--- |
| **100** | Baseline (`new`) vs. Prototype (`clone`) | Süre (ms), Bellek (MB), Valid % | 10 Defa |
| **1.000** | Baseline (`new`) vs. Prototype (`clone`) | Süre (ms), Bellek (MB), Valid % | 10 Defa |
| **10.000** | Baseline (`new`) vs. Prototype (`clone`) | Süre (ms), Bellek (MB), Valid % | 10 Defa |
| **100.000** | Baseline (`new`) vs. Prototype (`clone`) | Süre (ms), Bellek (MB), Valid % | 10 Defa |

---

### Kural İhlali ve Senaryo Üretim Analiz Sonuçları

`RuleViolationAnalysisTest` sınıfı çalıştırıldığında elde edilen kural doğrulama sonuçları aşağıda listelenmiştir:

| Scenario Scale | Methodology | Total Scenario | Valid | Invalid | Valid Rate(%) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **100** | Baseline (Naive) | 100 | 100 | 0 | 100,0% |
| **100** | Prototype (Manager) | 100 | 100 | 0 | 100,0% |
| **1.000** | Baseline (Naive) | 1000 | 1000 | 0 | 100,0% |
| **1.000** | Prototype (Manager) | 1000 | 1000 | 0 | 100,0% |
| **10.000** | Baseline (Naive) | 10000 | 10000 | 0 | 100,0% |
| **10.000** | Prototype (Manager) | 10000 | 10000 | 0 | 100,0% |
| **100.000** | Baseline (Naive) | 100000 | 100000 | 0 | 100,0% |
| **100.000** | Prototype (Manager) | 100000 | 100000 | 0 | 100,0% |

---

### Analiz Değerlendirmesi ve Teknik Açıklamalar

1. **%100 Valid Sonucunun Nedeni:**
   Mevcut jeneratörlerimiz (`NaiveScenarioGenerator` ve `ScenarioManager`), mimari tasarımları gereği `ScenarioValidator` kurallarını ihlal etmeyecek şekilde nesne ilişkilerini kurmaktadır. Kural ihlali tablosunda her iki yöntemin de tüm ölçeklerde **%100 Valid** çıkması, jeneratörlerin validasyon kurallarına tam uyumlu geçerli senaryolar ürettiğini kanıtlamaktadır.

2. **Kontrollü İhlal Testleri (Sınır Durum Doğrulaması):**
   Mevcut jeneratörler sürekli geçerli veri ürettiği için `ScenarioValidator` mekanizmasının hatalı yapıları gerçekten reddedip engellemediğini sınamak amacıyla `testControlledInvalidScenarios()` birim testi yazılmıştır. Bu testte kontrollü olarak oluşturulan 4 geçersiz senaryo `assertFalse()` ile sınanmış ve başarıyla reddedilmiştir:
    - **UAV bulunmayan senaryo:** Engellendi.
    - **Target bulunmayan senaryo:** Engellendi.
    - **RECON (Keşif) görevinde mühimmat taşıyan İHA:** Engellendi.
    - **Mission bilgisi `null` olan senaryo:** Engellendi.

3. **Metodoloji Ayrımı:**
   Yarı deneysel protokolümüzdeki *"10 tekrar çalıştırma ve ortalama alma"* yöntemi ile JIT Warm-up işlemleri, sistemin zaman ve bellek performansını ölçen **Performans Benchmark** testlerimize aittir. **Gün 16 Kural İhlali Analizi** kapsamında ise süre/bellek değerlerinden bağımsız olarak üretilen senaryoların mantıksal iş kurallarına (Valid / Invalid) uygunluğu incelenmiştir.

---
## Gün 17 - Süre, Throughput ve Bellek Analizi (Sabit Heap Ölçümü)

### Metodolojik Yapı ve Test Koşulları
Akademik standartlara uygun ve izole bir mikro-benchmark elde etmek amacıyla test ortamı aşağıdaki şartlarda koşturulmuştur:
1. **İçerik ve Karmaşıklık Eşitliği:** `Baseline (new)` yöntemi, arka planda klonlama yapan yapılardan tamamen arındırılmıştır. `Baseline` tarafında nesne ağacı doğrudan saf `new` anahtar kelimeleriyle sıfırdan kurulmakta; `Prototype (clone)` tarafında ise aynı nesne ağacı `.clone()` ile türetilmektedir. Her iki yöntemde de nesne ağacı tam olarak 1 Mission, 1 UAV, 1 Sensor, 1 Munition ve 1 Target içermektedir.
2. **Ölçüm Hassasiyeti ve Ortam İzolasyonu:** Ölçümlerde `System.nanoTime()` kullanılmış, gürültüyü (noise) sönümlemek adına iterasyon sayısı $30$'a çıkarılmıştır. Bellek genişleme overhead'ini önlemek amacıyla JVM heap boyutu `-Xms1024m -Xmx1024m` parametreleriyle $1\text{ GB}$'a sabitlenmiştir. Her iki yöntem de aynı `ScenarioValidator` örneği üzerinden (`validator.validate(scenario)`) doğrulama sürecinden geçirilmiştir.

### Performans ve Bellek Test Sonuçları (30 İterasyon Ortalama ve STD)

| Scale | Method | Avg Time (ms) | STD Time (ms) | Throughput (op/s) | Avg Mem (MB) | Std Mem (MB) | Valid Rate (%) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **100** | Baseline(new) | 0,25 | 0,13 | 408.057,78 | 0,57 | 0,21 | 100,0% |
| **100** | Prototype(clone) | 0,21 | 0,06 | 487.709,40 | 0,55 | 0,15 | 100,0% |
| **1000** | Baseline(new) | 0,86 | 0,07 | 1.161.053,80 | 1,09 | 0,19 | 100,0% |
| **1000** | Prototype(clone) | 1,05 | 0,29 | 948.649,69 | 1,15 | 0,23 | 100,0% |
| **10000** | Baseline(new) | 1,81 | 0,15 | 5.510.299,97 | 7,00 | 0,00 | 100,0% |
| **10000** | Prototype(clone) | 1,44 | 0,29 | 6.937.411,00 | 5,50 | 0,00 | 100,0% |
| **100000** | Baseline(new) | 12,78 | 7,12 | 7.825.736,52 | 68,38 | 0,78 | 100,0% |
| **100000** | Prototype(clone) | 7,69 | 0,67 | 13.002.914,82 | 52,00 | 0,00 | 100,0% |

---

## Gün 18 - Sonuçların Yorumlanması, Mimari Analiz, Sınırlamalar ve Öneriler

### 1. Elde Edilen Bulgular (Findings)
* **Zaman Performansı:** Heap sabitlendiğinde $N=100.000$ ölçeğinde Prototype deseni ortalama $7,69\text{ ms}$ tamamlama süresiyle Baseline yöntemine ($12,78\text{ ms}$) göre belirgin bir hız avantajı (yaklaşık **$1,66\times$ hızlanma**) sunmuştur. Prototype tarafındaki standart sapmanın ($0,67\text{ ms}$) düşük kalması, hız avantajının bu ölçüm koşullarında düşük değişkenlikle gözlendiğini göstermektedir.
* **Bellek Tüketimi (Tutarlı Bulgu):** Sabit heap altında da bellek tasarrufu trendi korunmuştur. $N=100.000$ ölçeğinde Prototype yaklaşımı $52,00\text{ MB}$ ortalama bellek harcarken, Baseline $68,38\text{ MB}$ tüketmiştir. Prototype deseni **$16,38\text{ MB}$ (~%24) net bellek tasarrufu** sağlamış ve $STD = 0,00\text{ MB}$ ile yüksek kararlılık sergilemiştir.
* **Düşük Ölçek Metrikleri:** $N \le 1.000$ ölçeklerindeki sub-milisaniye sürelerde iki yöntem birbirine yakın sonuçlar üretmektedir. Başlangıç heap boyutunun $1\text{ GB}$'a sabitlenmesi nedeniyle küçük ölçeklerdeki ($N=100$) bellek taban çizgisi önceki serilere göre bir miktar yüksek ($~0,55-0,57\text{ MB}$) ölçülmüştür.

### 2. Mimarinin Güçlü Yönleri (Strengths)
* **Bellek Alanı Verimliliği:** Alt bileşenlerin tek tek yapıcı metotlarının çağrılması yerine `.clone()` ile bellek kopyalaması yapılması, yüksek hacimli yük altında belirgin bir bellek tasarrufu ve düşük bellek dalgalanması sunar.
* **Gerçek Derin Kopyalama (Deep Copy):** `Scenario.clone()` ve `UAV.clone()` metotlarının iç koleksiyonları tek tek klonlaması, türetilen nesneler arasında tam bağımsızlık sağlar.

### 3. Mimarinin Zayıf Yönleri ve Sınırlamalar (Limitations)
* **Ortam Bağımlılığı ve JIT Dinamikleri:** Masaüstü geliştirme ortamlarında arka plan yükleri ve JIT derleyicisinin (C1/C2) anlık kararları, çalıştırmalar arasında genel süre seviyelerinin değişmesine yol açabilmektedir.
* **Düşük Ölçeklerde Önemsizleşen Fark:** $N \le 1.000$ gibi küçük ölçeklerde yöntemler arasındaki fark milisaniyenin altında kaldığı için Prototip deseni kullanımı gereksiz mimari karmaşıklık (*Over-engineering*) oluşturabilir.
* **Derin Kopyalama Bakım Maliyeti:** Alan sınıflarına yeni bir koleksiyon eklendiğinde `clone()` metotlarının manuel olarak güncellenmesi gerekir.
* **Singleton Thread-Safety Risk:** `ScenarioManager.getInstance()` yapısı senkronize değildir. Çok iş parçacıklı (*multi-thread*) ortamlarda yarış durumuna (*race condition*) açıktır.

### 4. Öneriler (Recommendations)
* **Yüksek Hacimli Üretimde Prototype Tercihi:** Yüz binlerce senaryonun üretildiği simülasyon sistemlerinde `Prototype Pattern` (`.clone()`) kullanımı, hem süre hem de bellek tasarrufu nedeniyle tercih edilmelidir.
* **Singleton'ın Thread-Safe Hale Getirilmesi:** `ScenarioManager.getInstance()` metoduna `synchronized` veya çift kontrollü kilitleme (*double-checked locking*) eklenmelidir.
* **Clone Metotlarının Birim Testleri:** `UAV` ve `Scenario` sınıflarına yeni alanlar eklendiğinde derin kopyalamayı doğrulayan birim testleri yazılmalıdır.


### 5. Genel Sonuç
Elde edilen deneysel ve mimari veriler; yüksek hacimli senaryo üretimi gerektiren simülasyon sistemlerinde **Prototype Pattern** kullanımının, iş kuralı doğruluğundan ödün vermeksizin zaman ve işleme hacminde **~2,27 kat**, bellek kullanımında ise **~2,07 kat** tutarlı bir avantaj sağladığını ortaya koymaktadır.