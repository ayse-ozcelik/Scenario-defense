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

## Gün 17 - Süre, Throughput ve Bellek Analizi (Adil Benchmark)

### Metodolojik İyileştirme ve Test Koşulları
Akademik standartlara uygun ve savunulabilir bir karşılaştırma elde etmek amacıyla test metodolojisinde iki temel düzeltme yapılmıştır:
1. **İçerik ve Karmaşıklık Eşitliği:** `Baseline (new)` ve `Prototype (clone)` yöntemlerinin her ikisi de birebir aynı sayıda ve türde alt nesneye (UAV, Target, Sensor, Munition) sahip senaryolar üretecek şekilde eşitlenmiştir. Rastgele nesne üretimi kaldırılarak saf `new` ve `clone()` farkı ölçülmüştür.
2. **Hassas Zaman Ölçümü:** Ölçüm çözünürlüğünü artırmak adına `System.currentTimeMillis()` yerine nanosaniye hassasiyetindeki `System.nanoTime()` API'sine geçilmiştir.

### Performans ve Bellek Test Sonuçları (10 İterasyon Ortalama ve STD)

Aşağıdaki veriler eşitlenmiş içerik ve JVM Warm-up sonrasında 10 tekrarın ortalaması (Mean) ve Standart Sapması (STD) hesaplanarak elde edilen **güncel konsol çıktılarıdır**:

| Scale | Method | Avg Time (ms) | STD Time (ms) | Throughput (op/s) | Avg Mem (MB) | STD Mem (MB) | Valid Rate (%) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **100** | Baseline(new) | 0,18 | 0,08 | 542.034,80 | 0,08 | 0,03 | 100,0% |
| **100** | Prototype(clone) | 0,08 | 0,03 | 1.320.829,48 | 0,06 | 0,01 | 100,0% |
| **1000** | Baseline(new) | 0,47 | 0,07 | 2.114.164,90 | 0,82 | 0,07 | 100,0% |
| **1000** | Prototype(clone) | 0,29 | 0,08 | 3.501.155,38 | 0,44 | 0,00 | 100,0% |
| **10000** | Baseline(new) | 11,91 | 3,95 | 839.953,57 | 7,36 | 0,38 | 100,0% |
| **10000** | Prototype(clone) | 0,44 | 0,14 | 22.687.054,77 | 3,53 | 0,02 | 100,0% |
| **100000** | Baseline(new) | 62,16 | 19,55 | 1.608.861,09 | 75,70 | 6,33 | 100,0% |
| **100000** | Prototype(clone) | 27,41 | 7,25 | 3.648.933,22 | 36,50 | 2,62 | 100,0% |

---

### Karşılaştırma Grafikleri (ASCII Metin)

**1. Çalışma Süresi Karşılaştırması (Ortalama Zaman - Düşük Değer İyidir)**

* **Baseline (new):** `[==================================================]` **62.16 ms**
* **Prototype (clone):** `[======================]` **27.41 ms** *(~2.27 Kat Daha Hızlı)*

**2. Throughput / İşleme Hacmi Karşılaştırması (Yüksek Değer İyidir)**

* **Baseline (new):** `[======================]` **1.608.861,09 op/s**
* **Prototype (clone):** `[==================================================]` **3.648.933,22 op/s** *(~2.27 Kat Daha Yüksek Hacim)*

**3. Bellek Tüketimi Karşılaştırması (Düşük Değer İyidir)**

* **Baseline (new):** `[==================================================]` **75.70 MB**
* **Prototype (clone):** `[========================]` **36.50 MB** *(~2.07 Kat Daha Az Bellek Tüketimi)*

---

### Analiz Değerlendirmesi

1. **Zaman Performansı:** Senaryo içerikleri ve nesne sayıları tamamen eşitlendiğinde dahi Prototype Pattern (`.clone()`) yaklaşımı; yapıcı metod zincirlerini (constructor) sıfırdan çalıştırma ve alan atama yükünü bypass ettiği için $N=100.000$ ölçeğinde **27.41 ms** ile **~2.27 kat daha hızlı** tamamlanmıştır.
2. **Bellek Kullanımı:** Birebir aynı nesne hiyerarşisine sahip senaryo üretiminde Baseline yöntemi **75.70 MB** bellek tüketirken Prototype yaklaşımı **36.50 MB** harcayarak yaklaşık yarı yarıya bellek tasarrufu sağlamıştır.
3. **Standart Sapma ve İstatistiksel İstikrar:** `System.nanoTime()` hassasiyetinde gerçekleştirilen testlerde Prototype yönteminin bellek standart sapması ($2.62$ MB vs $6.33$ MB) belirgin şekilde düşük çıkmıştır. Bu durum, klonlama yaklaşımının bellek tahsisinde donanım ve yük dalgalanmalarından daha az etkilenerek öngörülebilir bir performans sunduğunu kanıtlamaktadır.

---

## Gün 18 - Sonuçların Yorumlanması, Mimari Analiz ve Sınırlamalar (Limitations)

### 1. Elde Edilen Bulgular (Findings)
* **Ölçeklenebilir Hız ve İşleme Hacmi:** Adil benchmark koşullarında $N=100.000$ ölçeğinde `Prototype (clone)` yöntemi **27,41 ms** sürede **3.648.933,22 op/s** işleme hacmine ulaşırken; `Baseline (new)` yöntemi **62,16 ms** sürede **1.608.861,09 op/s** üretebilmiştir. Prototype deseni **~2,27 kat zaman ve throughput kazancı** sunmuştur.
* **Bellek Tüketimi ve GC Baskısı:** Birebir aynı nesne hiyerarşisinde Prototype deseni **36,50 MB** bellek harcarken Baseline yöntemi **75,70 MB** harcamıştır. Prototip kullanımı bellek harcamasını **~2,07 kat azaltarak** JVM üzerindeki Çöp Toplayıcı (Garbage Collector) yükünü belirgin şekilde hafifletmiştir.
* **İstatistiki Kararlılık:** $N=100.000$ ölçeğinde bellek standart sapması Baseline yönteminde **6,33 MB** iken Prototype yönteminde **2,62 MB** seviyesindedir. Bu durum klonlama yaklaşımının daha öngörülebilir bir bellek haritası sunduğunu göstermektedir.

### 2. Mimarinin Güçlü Yönleri (Strengths)
* **Constructor Zincirinin Bypass Edilmesi:** Nesne ağacı karmaşıklaştıkça sıfırdan `new` ile yapılan atamalar yerine bellek kopyalamasının tercih edilmesi performans kazancının ana nedenidir.
* **Gerçek Derin Kopyalama (Deep Copy):** `UAV.clone()` ve `Scenario.clone()` metotları iç koleksiyonları ve referansları tek tek klonlayarak tam nesne bağımsızlığı sağlar. Bir senaryodaki değişiklik diğer senaryoları etkilemez.
* **Bütünsel Tasarım Deseni Entegrasyonu:** Builder, Abstract Factory, Singleton ve Prototype desenlerinin birlikte kullanımı modüler ve genişletilebilir bir mimari sağlamıştır.

### 3. Mimarinin Zayıf Yönleri ve Sınırlamaları (Limitations)
* **Düşük Ölçeklerde Önemsizleşen Fark:** $N=100$ ve $N=1.000$ ölçeklerinde yöntemler arasındaki zaman farkı $1\text{ ms}$'nin altındadır ($0,18\text{ ms}$ vs $0,08\text{ ms}$). Düşük hacimli uygulamalarda Prototip deseni kurmak mimari karmaşıklığa (Over-engineering) yol açabilir.
* **Derin Kopyalama (Deep Copy) Bakım Maliyeti:** Alan sınıflarına eklenecek yeni koleksiyon veya referans tipi alanların `clone()` metotlarına manuel dahil edilmesi gerekir. İhmal edilen alanlar sığ kopyalama (Shallow Copy) hatalarına ve veri çakışmalarına yol açabilir.
* **Singleton'da Thread-Safety Eksikliği:** `ScenarioManager.getInstance()` metodu senkronizasyon (`synchronized` / *double-checked locking*) barındırmaz. Çok iş parçacıklı (multi-thread) ortamlarda yarış durumuna (*race condition*) açıktır.
* **Manuel GC Çağrılarına Bağımlılık:** Benchmark testlerindeki bellek ölçümleri `System.gc()` çağrısına dayanır; GC'nin tetiklenme garantisi JVM yönetimine bağlı bir kısıttır.

### 4. Genel Sonuç
Elde edilen deneysel ve mimari veriler; yüksek hacimli senaryo üretimi gerektiren simülasyon sistemlerinde **Prototype Pattern** kullanımının, iş kuralı doğruluğundan ödün vermeksizin zaman ve işleme hacminde **~2,27 kat**, bellek kullanımında ise **~2,07 kat** tutarlı bir avantaj sağladığını ortaya koymaktadır.