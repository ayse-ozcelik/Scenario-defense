package test;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;
import main.scenario.ScenarioManager;
import main.validator.ScenarioValidator;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class PerformanceBenchmarkTest {

    private static final int WARMUP_ITERATIONS = 20_000;
    private static final int REPETITIONS = 30;

    private final ScenarioValidator validator = new ScenarioValidator();
    private final NaiveScenarioGenerator naiveScenarioGenerator = new NaiveScenarioGenerator();
    private final ScenarioManager prototypeManager = ScenarioManager.getInstance();

    // Her iki yöntemde de iş yükü eşittir: 1 senaryo üretimi + 1 doğrulama.
    private Scenario generate(boolean isPrototype) {
        return isPrototype
                ? prototypeManager.generateScenarioWithoutValidation()
                : naiveScenarioGenerator.generateScenario();
    }

    @Test
    public void runFullBenchmarkSuite() {
        System.out.println("Benchmark starting...");
        runWarmUp();
        int[] scales = {100, 1000, 10000, 100000};

        System.out.println("\nScale    | Method             | Avg Time(ms) | STD Time(ms) | Throughput(op/s) | Avg Mem(MB)   | Std Mem(MB)    | Valid Rate(%)");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        for (int n : scales) {
            double[] baseTime = new double[REPETITIONS], baseMem = new double[REPETITIONS];
            double[] protoTime = new double[REPETITIONS], protoMem = new double[REPETITIONS];
            long baseValid = 0, protoValid = 0;

            for (int iter = 0; iter < REPETITIONS; iter++) {
                // Sıra etkisini gidermek için yöntem sırası her turda dönüşümlü değişir
                boolean protoFirst = (iter % 2 == 1);
                for (int k = 0; k < 2; k++) {
                    boolean isPrototype = (k == 0) == protoFirst;
                    double[] r = runOnce(isPrototype, n);
                    if (isPrototype) {
                        protoTime[iter] = r[0]; protoMem[iter] = r[1]; protoValid += (long) r[2];
                    } else {
                        baseTime[iter] = r[0]; baseMem[iter] = r[1]; baseValid += (long) r[2];
                    }
                }
            }
            report("Baseline(new)", n, baseTime, baseMem, baseValid);
            report("Prototype(clone)", n, protoTime, protoMem, protoValid);
        }
    }

    public void runWarmUp() {
        System.out.println("JVM Warm-up is running (" + WARMUP_ITERATIONS + " iterations per method)...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            validator.validate(generate(false));
            validator.validate(generate(true));
        }
        System.gc();
        System.out.println("Warm-up has done");
    }

    // Döndürür: {süre(ms), bellek farkı(MB), geçerli senaryo sayısı}
    private double[] runOnce(boolean isPrototype, int count) {
        System.gc();
        Runtime rt = Runtime.getRuntime();
        long startMem = rt.totalMemory() - rt.freeMemory();
        long start = System.nanoTime();

        List<Scenario> scenarios = new ArrayList<>(count);
        int valid = 0;
        for (int i = 0; i < count; i++) {
            Scenario scenario = generate(isPrototype);
            scenarios.add(scenario);
            if (validator.validate(scenario)) {
                valid++;
            }
        }

        long end = System.nanoTime();
        long endMem = rt.totalMemory() - rt.freeMemory();
        double timeMs = (end - start) / 1_000_000.0;
        double memMb = Math.max(0, endMem - startMem) / (1024.0 * 1024.0);
        if (scenarios.size() != count) throw new IllegalStateException();
        return new double[]{timeMs, memMb, valid};
    }

    private void report(String name, int count, double[] times, double[] mems, long totalValid) {
        double avgT = mean(times), avgM = mean(mems);
        double stdT = std(times, avgT), stdM = std(mems, avgM);
        double throughput = avgT > 0 ? count / (avgT / 1000.0) : 0;
        double validRate = (double) totalValid / ((double) count * times.length) * 100.0;
        System.out.printf("%-8d | %-18s | %-12.2f | %-12.2f | %-16.2f | %-13.2f | %-14.2f | %-11.1f%%\n",
                count, name, avgT, stdT, throughput, avgM, stdM, validRate);
    }

    private static double mean(double[] a) {
        double s = 0; for (double v : a) s += v; return s / a.length;
    }

    // Popülasyon standart sapması (rapordaki tanımla uyumlu)
    private static double std(double[] a, double m) {
        double s = 0; for (double v : a) s += (v - m) * (v - m); return Math.sqrt(s / a.length);
    }
}