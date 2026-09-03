package test;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;
import main.scenario.ScenarioManager;
import main.validator.ScenarioValidator;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class PerformanceBenchmarkTest {

    private final ScenarioValidator validator = new ScenarioValidator();
    private final NaiveScenarioGenerator naiveScenarioGenerator = new NaiveScenarioGenerator();
    private final ScenarioManager prototypeManager = ScenarioManager.getInstance();

    @Test
    public void runFullBenchmarkSuite() {
        System.out.println("Benchmark starting...");
        runWarmUp();
        int scales[] = {100, 1000, 10000, 100000};
        int iterations = 30;

        System.out.println("\nScale    | Method             | Avg Time(ms) | STD Time(ms) | Throughput(op/s) | Avg Mem(MB)   | Std Mem(MB)    | Valid Rate(%)");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        for (int n : scales) {
            measurePerformance("Baseline(new)", n, iterations, false);
            measurePerformance("Prototype(clone)", n, iterations, true);
        }
    }

    public void runWarmUp() {
        System.out.println("JVM Warm-up is running...");
        for (int i = 0; i < 1000; i++) {
            naiveScenarioGenerator.generateScenario();
            prototypeManager.generateScenario();
        }
        System.gc();
        System.out.println("Warm-up has done");
    }

    private void measurePerformance(String methodName, int count, int iterations, boolean isPrototype) {
        double[] timesMs = new double[iterations];
        double[] memories = new double[iterations];

        long totalNanoTime = 0;
        double totalMemoryBytes = 0;
        int totalValidCount = 0;

        for (int iter = 0; iter < iterations; iter++) {
            System.gc();
            Runtime jvmRunTime = Runtime.getRuntime();
            long startMem = jvmRunTime.totalMemory() - jvmRunTime.freeMemory();

            //System.currentTimeMillis yerine nanoTime kullanımı
            long startTimeNano = System.nanoTime();

            List<Scenario> scenarios = new ArrayList<>(count);
            int validInThisRun = 0;

            for (int i = 0; i < count; i++) {
                Scenario scenario = isPrototype ? prototypeManager.generateScenario() : naiveScenarioGenerator.generateScenario();
                scenarios.add(scenario);

                if (validator.validate(scenario)) {
                    validInThisRun++;
                }
            }

            long endTimeNano = System.nanoTime();
            long endMem = jvmRunTime.totalMemory() - jvmRunTime.freeMemory();

            long runTimeNano = (endTimeNano - startTimeNano);
            double runTimeMs = runTimeNano / 1_000_000.0; //Nanosaniyeyi Milisaniyeye dönüştürme

            long usedMemBytes = Math.max(0, endMem - startMem);
            double runMemMb = usedMemBytes / (1024.0 * 1024.0);

            timesMs[iter] = runTimeMs;
            memories[iter] = runMemMb;

            totalNanoTime += runTimeNano;
            totalMemoryBytes += usedMemBytes;
            totalValidCount += validInThisRun;
        }

        double avgTimeMs = (totalNanoTime / (double) iterations) / 1_000_000.0;
        double avgMemMb = (totalMemoryBytes / (double) iterations) / (1024.0 * 1024.0);
        double avgValidRate = ((double) totalValidCount / (count * iterations)) * 100.0;

        //Throughput (Senaryo / Saniye)
        double avgTimeSec = (totalNanoTime / (double) iterations) / 1_000_000_000.0;
        double throughput = avgTimeSec > 0 ? (count / avgTimeSec) : 0;

        double sumSqTime = 0;
        double sumSqMem = 0;
        for (int i = 0; i < iterations; i++) {
            sumSqTime += Math.pow(timesMs[i] - avgTimeMs, 2);
            sumSqMem += Math.pow(memories[i] - avgMemMb, 2);
        }
        double stdTimeMs = Math.sqrt(sumSqTime / iterations);
        double stdMemMb = Math.sqrt(sumSqMem / iterations);

        System.out.printf("%-8d | %-18s | %-12.2f | %-12.2f | %-16.2f | %-13.2f | %-14.2f | %-11.1f%%\n",
                count, methodName, avgTimeMs, stdTimeMs, throughput, avgMemMb, stdMemMb, avgValidRate);
    }
}
