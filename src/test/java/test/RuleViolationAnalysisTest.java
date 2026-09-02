package test;

import main.sensor.EOSensor;
import main.munition.HeavyMunition;
import main.mission.MissionType;
import main.mission.*;
import main.UAV.ReconUAV;
import main.target.Target;
import main.target.FixedTarget;
import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;
import main.scenario.ScenarioManager;
import main.validator.ScenarioValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
        }
    }

    private void analyzeGenerator(String methodName, int count, boolean isPrototype) {
        int validCount = 0;
        int invalidCount = 0;

        for (int i = 0; i < count; i++) {
            Scenario scenario = isPrototype ? prototypeManager.generateScenario() : naiveGenerator.generateScenario();

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

    @Test
    public void testControlledInvalidScenarios() {
        Target target1 = new FixedTarget("T-1", "Sabit Hedef 1", 100.0, "Bunker");
        Target target2 = new FixedTarget("T-2", "Sabit Hedef 2", 100.0, "Siginak");
        Target target3 = new FixedTarget("T-3", "Sabit Hedef 3", 100.0, "Bina");

        // 1- UAV Olmayan Senaryo (invalid)
        Scenario scenarioNoUav = new Scenario.ScenarioBuilder()
                .setMission(new Mission(MissionType.RECON, "Daytime", "Clear", RangeCategory.MEDIUM))
                .addTarget(target1)
                .build();
        assertFalse(validator.validate(scenarioNoUav), "UAV icermeyen senaryo reddedilmelidir.");

        // 2- Target Olmayan Senaryo (invalid)
        EOSensor sensor1 = new EOSensor("EO-1", "Electro-Optical Sensor", 10.0, 50.0, 100.0);
        ReconUAV reconUav1 = new ReconUAV("UAV-1", "Bayraktar TB2", 150.0, 50.0, 1000.0);
        reconUav1.addSensor(sensor1);

        Scenario scenarioNoTarget = new Scenario.ScenarioBuilder()
                .setMission(new Mission(MissionType.RECON, "Daytime", "Clear", RangeCategory.MEDIUM))
                .addUAV(reconUav1)
                .build();
        assertFalse(validator.validate(scenarioNoTarget), "Target icermeyen senaryo reddedilmelidir.");

        // 3- RECON Görevinde Mühimmat Taşıyan Senaryo (invalid)
        EOSensor sensor2 = new EOSensor("EO-2", "Electro-Optical Sensor 2", 10.0, 50.0, 100.0);
        ReconUAV reconUavWithMunition = new ReconUAV("Recon-1", "ANKA-S", 150.0, 50.0, 1000.0);
        reconUavWithMunition.addSensor(sensor2);
        reconUavWithMunition.addMunition(new HeavyMunition("M-1", "MAM-L", 2, 25.0));

        Scenario scenarioInvalidRecon = new Scenario.ScenarioBuilder()
                .setMission(new Mission(MissionType.RECON, "Daytime", "Clear", RangeCategory.MEDIUM))
                .addTarget(target2)
                .addUAV(reconUavWithMunition)
                .build();
        assertFalse(validator.validate(scenarioInvalidRecon), "RECON gorevinde muhimmat tasiyan senaryo reddedilmelidir.");

        // 4- Mission Bilgisi Olmayan (null) Senaryo (invalid)
        EOSensor sensor3 = new EOSensor("EO-3", "Electro-Optical Sensor 3", 10.0, 50.0, 100.0);
        ReconUAV reconUav2 = new ReconUAV("UAV-2", "HERON", 150.0, 50.0, 1000.0);
        reconUav2.addSensor(sensor3);

        Scenario scenarioNoMission = new Scenario.ScenarioBuilder()
                .setMission(null)
                .addTarget(target3)
                .addUAV(reconUav2)
                .build();
        assertFalse(validator.validate(scenarioNoMission), "Mission bilgisi null olan senaryo reddedilmelidir.");
    }
}