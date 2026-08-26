---

## Deney Protokolü ve Performans Test Planı

Bu protokol, **Prototype Pattern** (Klonlama) yaklaşımının **Naive/Baseline** (Sıfırdan `new` ile oluşturma) yaklaşımına göre sunduğu performans, bellek ve işleme hacmi avantajlarını bilimsel olarak ölçmek amacıyla hazırlanmıştır.

### 1. Deney Grupları
* **Baseline (Kontrol Grubu):** `NaiveScenarioGenerator` kullanılarak nesnelerin sıfırdan `new` anahtar kelimesiyle üretildiği yapı.
* **Prototype (Deney Grubu):** `ScenarioManager` deposundaki prototiplerin `.clone()` metoduyla çoğaltıldığı yapı.

### 2. Ölçüm Metrikleri ve Hesaplama Formülleri
* **Çalışma Süresi (Execution Time - ms):** $N$ adet senaryonun üretimi için geçen toplam süre.
* **Throughput (İşleme Hacmi - Senaryo/sn):** 
  $$\text{Throughput} = \frac{N}{\text{Toplam Süre (saniye)}}$$
* **Bellek Tüketimi (Memory Usage - MB):**
  $$\text{Harcanan MB} = \frac{\text{TotalMemory} - \text{FreeMemory}}{1024 \times 1024}$$
* **Doğruluk Oranı (Validation Rate - %):** Üretilen senaryoların `ScenarioValidator.validate()` testinden geçme yüzdesi.

### 3. Test Koşulları ve Metodoloji
* **JVM Warm-up:** JIT derleyici ve sınıf yükleme etkilerini nötrlemek adına ana ölçümler öncesi 1.000 adetlik ön çalıştırma yapılır.
* **İstatistiksel Güvenilirlik:** Donanım sapmalarını engellemek için her veri ölçeği 10 kez çalıştırılır ve ortalama ($Mean$) değerler kaydedilir.

### 4. Deney Matrisi (Ölçek Tablosu)

| Senaryo Ölçeği ($N$) | Yöntem | Ölçülecek Değerler | Tekrar Sayısı |
| :--- | :--- | :--- | :--- |
| **100** | Baseline (`new`) vs. Prototype (`clone`) | Süre (ms), Bellek (MB), Valid % | 10 Defa |
| **1.000** | Baseline (`new`) vs. Prototype (`clone`) | Süre (ms), Bellek (MB), Valid % | 10 Defa |
| **10.000** | Baseline (`new`) vs. Prototype (`clone`) | Süre (ms), Bellek (MB), Valid % | 10 Defa |
| **100.000** | Baseline (`new`) vs. Prototype (`clone`) | Süre (ms), Bellek (MB), Valid % | 10 Defa |
