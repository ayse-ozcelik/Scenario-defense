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



C:\Users\aayse\.jdks\openjdk-21.0.1\bin\java.exe -ea -Didea.test.cyclic.buffer.size=1048576 "-javaagent:C:\Users\aayse\AppData\Local\JetBrains\IntelliJ IDEA Community Edition 2023.2.5\lib\idea_rt.jar=52221:C:\Users\aayse\AppData\Local\JetBrains\IntelliJ IDEA Community Edition 2023.2.5\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "C:\Users\aayse\.m2\repository\org\junit\platform\junit-platform-launcher\1.9.2\junit-platform-launcher-1.9.2.jar;C:\Users\aayse\AppData\Local\JetBrains\IntelliJ IDEA Community Edition 2023.2.5\lib\idea_rt.jar;C:\Users\aayse\AppData\Local\JetBrains\IntelliJ IDEA Community Edition 2023.2.5\plugins\junit\lib\junit5-rt.jar;C:\Users\aayse\AppData\Local\JetBrains\IntelliJ IDEA Community Edition 2023.2.5\plugins\junit\lib\junit-rt.jar;C:\Users\aayse\IdeaProjects\scenariodefense\target\test-classes;C:\Users\aayse\IdeaProjects\scenariodefense\target\classes;C:\Users\aayse\.m2\repository\org\openjfx\javafx-controls\21-ea+24\javafx-controls-21-ea+24.jar;C:\Users\aayse\.m2\repository\org\openjfx\javafx-controls\21-ea+24\javafx-controls-21-ea+24-win.jar;C:\Users\aayse\.m2\repository\org\openjfx\javafx-graphics\21-ea+24\javafx-graphics-21-ea+24.jar;C:\Users\aayse\.m2\repository\org\openjfx\javafx-graphics\21-ea+24\javafx-graphics-21-ea+24-win.jar;C:\Users\aayse\.m2\repository\org\openjfx\javafx-base\21-ea+24\javafx-base-21-ea+24.jar;C:\Users\aayse\.m2\repository\org\openjfx\javafx-base\21-ea+24\javafx-base-21-ea+24-win.jar;C:\Users\aayse\.m2\repository\org\openjfx\javafx-fxml\21-ea+24\javafx-fxml-21-ea+24.jar;C:\Users\aayse\.m2\repository\org\openjfx\javafx-fxml\21-ea+24\javafx-fxml-21-ea+24-win.jar;C:\Users\aayse\.m2\repository\org\junit\jupiter\junit-jupiter-api\5.9.2\junit-jupiter-api-5.9.2.jar;C:\Users\aayse\.m2\repository\org\opentest4j\opentest4j\1.2.0\opentest4j-1.2.0.jar;C:\Users\aayse\.m2\repository\org\junit\platform\junit-platform-commons\1.9.2\junit-platform-commons-1.9.2.jar;C:\Users\aayse\.m2\repository\org\apiguardian\apiguardian-api\1.1.2\apiguardian-api-1.1.2.jar;C:\Users\aayse\.m2\repository\org\junit\jupiter\junit-jupiter-engine\5.9.2\junit-jupiter-engine-5.9.2.jar;C:\Users\aayse\.m2\repository\org\junit\platform\junit-platform-engine\1.9.2\junit-platform-engine-1.9.2.jar" com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit5 test.RuleViolationAnalysisTest
Scenario Scale     | Methodology        | Total Scenario | Valid          | Invalid          | Valid Rate(%)  
---------------------------------------------------------------------------------------------------------------
100                | Baseline (Naive)   | 100            | 100            | 0                | 100,0          %
100                | Prototype (Manager) | 100            | 100            | 0                | 100,0          %
1000               | Baseline (Naive)   | 1000           | 1000           | 0                | 100,0          %
1000               | Prototype (Manager) | 1000           | 1000           | 0                | 100,0          %
10000              | Baseline (Naive)   | 10000          | 10000          | 0                | 100,0          %
10000              | Prototype (Manager) | 10000          | 10000          | 0                | 100,0          %
100000             | Baseline (Naive)   | 100000         | 100000         | 0                | 100,0          %
100000             | Prototype (Manager) | 100000         | 100000         | 0                | 100,0          %

Process finished with exit code 0
