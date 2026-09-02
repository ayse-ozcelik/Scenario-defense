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

## Gün 17 - Süre, Throughput ve Bellek Analizi

### Performans ve Bellek Test Sonuçları (10 İterasyon Ortalama ve STD)

Aşağıdaki veriler JVM Warm-up sonrasında 10 tekrarın ortalaması (Mean) ve Standart Sapması (STD) hesaplanarak elde edilen **gerçek konsol çıktılarıdır**:

| Scale | Method | Avg Time (ms) | STD Time (ms) | Throughput (op/s) | Avg Mem (MB) | STD Mem (MB) | Valid Rate (%) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **100** | Baseline(new) | 0,60 | 0,66 | 166.666,67 | 0,19 | 0,07 | 100,0% |
| **100** | Prototype(clone) | 0,10 | 0,30 | 1.000.000,00 | 0,05 | 0,01 | 100,0% |
| **1000** | Baseline(new) | 1,80 | 0,60 | 555.555,56 | 1,82 | 0,13 | 100,0% |
| **1000** | Prototype(clone) | 0,40 | 0,49 | 2.500.000,00 | 0,44 | 0,01 | 100,0% |
| **10000** | Baseline(new) | 28,60 | 5,80 | 349.650,35 | 17,27 | 0,27 | 100,0% |
| **10000** | Prototype(clone) | 0,60 | 0,49 | 16.666.666,67 | 3,58 | 0,11 | 100,0% |
| **100000** | Baseline(new) | 126,40 | 15,81 | 791.139,24 | 178,28 | 2,51 | 100,0% |
| **100000** | Prototype(clone) | 28,10 | 6,07 | 3.558.718,86 | 35,61 | 0,39 | 100,0% |

---

### Karşılaştırma Grafikleri (ASCII Metin)

#### 1. Çalışma Süresi Karşılaştırması (Ortalama Zaman - Düşük Değer İyidir)

[N = 100.000 Ölçeği Süre Analizi (ms)]

Baseline(new)     : [==================================================] 126.40 ms

Prototype(clone)  : [===========] 28.10 ms (~4.5 Kat Daha Hızlı)

[N = 100.000 Ölçeği İşleme Hacmi (Senaryo / Saniye)]

Baseline(new)     : [===========] 791.139 op/s
Prototype(clone)  : [==================================================] 3.558.718 op/s (~4.5 Kat Daha Yüksek Hacim)

[N = 100.000 Ölçeği Bellek Tüketimi (MB)]
Baseline(new)     : [==================================================] 178.28 MB
Prototype(clone)  : [==========] 35.61 MB (~5 Kat Daha Az Bellek Tüketimi)

---

### Analiz Değerlendirmesi
1. **Zaman Performansı:** Prototype Pattern `.clone()` yaklaşımı karmaşık alt nesneleri baştan `new` ile kurgulamak yerine bellek kopyalaması yaptığı için $N=100.000$ ölçeğinde **28.10 ms** ile **~4.5 kat daha hızlı** tamamlanmıştır.
2. **Bellek Kullanımı:** Prototype yaklaşımı $100.000$ senaryo üretiminde **35.61 MB** harcarken Baseline yöntemi **178.28 MB** bellek tüketmiştir. Bu da çöp toplayıcıya (Garbage Collector) binen yükü **%80 azaltmıştır**.
3. **Standart Sapma (İstikrar):** Bellek standart sapmasının Prototype yönteminde çok düşük çıkması ($0.39$ vs $2.51$), klonlama yaklaşımının bellek yönetiminde ne kadar kararlı ve öngörülebilir çalıştığını doğrulamaktadır.