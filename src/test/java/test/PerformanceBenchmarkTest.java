package test;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;
import main.scenario.ScenarioManager;
import main.validator.ScenarioValidator;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class PerformanceBenchmarkTest{
    private final ScenarioValidator validator=new ScenarioValidator();
    private final NaiveScenarioGenerator naiveScenarioGenerator=new NaiveScenarioGenerator();
    private final ScenarioManager prototypeManager= ScenarioManager.getInstance();


    @Test
    public void runFullBenchmarkSuite() {
        System.out.println("Benchmark starting...");
        runWarmUp();
        int scales[] = {100, 1000, 10000, 100000};
        //dizi ile döngüde sırasıyla 100, 1000... tane senaryo testi çalışacak
        int iterations = 10;

        System.out.println("\nScale    | Method             | Avg Time(ms) | STD Time(ms) | Throughput(op/s) | Avg Mem(MB)   | Std Mem(MB)    | Valid Rate(%)");        System.out.println("---------------------------------------------------------");

        for (int n : scales) {
            measurePerformance("Baseline(new)", n, iterations, false); //parametreler: metin,sayı, iterasyon sayısı, boolean
            measurePerformance("Prototype(clone)", n, iterations, true);//measurePerformance methodundan değerler alındı
        }

    }
        public void runWarmUp() {
            System.out.println("JVM Warp up is runnig...");
            for(int i=0; i<1000; i++ ){
                naiveScenarioGenerator.generateScenario();
                prototypeManager.generateScenario();
                //1000 tane senaryo oluşturuldu
            }
            System.gc(); //hafıza temizlensin
            System.out.println("Warp-up has done");
        }

        private void measurePerformance(String methodName, int count, int iterations, boolean isPrototype) {
            double[] times = new double[iterations];
            double[] memories = new double[iterations];

            long totalTime= 0;
            double totalMemory= 0;
            int totalValidCount= 0;

            for (int iter= 0; iter< iterations; iter++) {
                System.gc();
                //işlemin temiz olması için turun başında hafıza temizlendi
                Runtime jvmRunTime= Runtime.getRuntime(); //JVM
                long startMem= jvmRunTime.totalMemory()- jvmRunTime.freeMemory();
                long startTime= System.currentTimeMillis();

                List<Scenario> scenarios= new ArrayList<>(count);
                int validInThisRun= 0;

                for (int i = 0; i < count; i++) {
                    Scenario scenario= isPrototype ? prototypeManager.generateScenario() : naiveScenarioGenerator.generateScenario();
                    scenarios.add(scenario);

                    if (validator.validate(scenario)) {
                        validInThisRun++;
                    }
                }

                long endTime= System.currentTimeMillis();
                long endMem= jvmRunTime.totalMemory() - jvmRunTime.freeMemory();

                long runTime= (endTime - startTime);
                long usedMemBytes= Math.max(0, endMem - startMem);
                double runMemMb= usedMemBytes / (1024.0*1024.0);

                times[iter]=runTime;
                memories[iter] = runMemMb;

                totalTime+= runTime;
                totalMemory+= usedMemBytes;
                totalValidCount+= validInThisRun;
            }

            double avgTimeMs= totalTime / (double) iterations;
            double avgMemMb= (totalMemory / (double) iterations) / (1024.0 * 1024.0);
            double avgValidRate= ((double) totalValidCount / (count * iterations)) * 100.0;
            double throughput = avgTimeMs > 0 ? (count / (avgTimeMs / 1000.0)) : (count * 1000.0);

            double sumSqTime = 0;
            double sumSqMem = 0;
            for (int i = 0; i < iterations; i++) {
                sumSqTime += Math.pow(times[i] - avgTimeMs, 2);
                sumSqMem += Math.pow(memories[i] - avgMemMb, 2);
            }
            double stdTimeMs = Math.sqrt(sumSqTime / iterations);
            double stdMemMb = Math.sqrt(sumSqMem / iterations);

            System.out.printf("%-8d | %-18s | %-12.2f | %-12.2f | %-16.2f | %-13.2f | %-14.2f | %-11.1f%%\n",
                    count, methodName, avgTimeMs, stdTimeMs, throughput, avgMemMb, stdMemMb, avgValidRate);
    }
}

