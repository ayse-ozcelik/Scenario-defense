
package test;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;
import main.scenario.ScenarioManager;
import main.validator.ScenarioValidator;
import org.junit.jupiter.api.Test;

public class RuleViolationAnalysisTest {

    private final ScenarioValidator validator = new ScenarioValidator();
    private final NaiveScenarioGenerator naiveGenerator = new NaiveScenarioGenerator();
    private final ScenarioManager prototypeManager = ScenarioManager.getInstance();

    @Test
    public void runRuleViolationAnalysis() {
        int[] scales = {100, 1000, 10000, 100000};

        System.out.printf("%-18s | %-18s | %-14s | %-14s | %-16s | %-15s\n",
                "Scenario Scale", "Methodology", "Total Scenario", "Valid", "Invalid", "Valid Rate(%)");
        System.out.println("---------------------------------------------------------------------------------------------------------------");

        for (int n : scales) {
            analyzeGenerator("Baseline (Naive)", n, false);
            analyzeGenerator("Prototype (Manager)", n, true);
            //aşağıdaki kodda kontrolü yapıldı
        }
    }

    private void analyzeGenerator(String methodName, int count, boolean isPrototype) {
        int validCount = 0;
        int invalidCount = 0;

        for (int i = 0; i < count; i++) {
            Scenario scenario = isPrototype ? prototypeManager.generateScenario() : naiveGenerator.generateScenario();
            //isprotoype true ise protoype ile senaryo klonla
            //false ise naive ile yeni senaryo üret


            if (validator.validate(scenario)) {
                validCount++;
            } else {
                invalidCount++;
            }
        }

        double validRate = ((double) validCount / count) * 100.0;

        System.out.printf("%-18d | %-18s | %-14d | %-14d | %-16d | %-15.1f%%\n",
                count, methodName, count, validCount, invalidCount, validRate);
    }
}