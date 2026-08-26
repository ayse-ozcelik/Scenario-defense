package test;

import main.UAV.ReconUAV;
import main.UAV.UAV;
import main.factory.AttackMissionComponentFactory;
import main.factory.ReconMissionComponentFactory;
import main.target.TargetFactory;
import main.mission.Mission;
import main.mission.MissionType;
import main.mission.RangeCategory;
import main.munition.Munition;
import main.scenario.Scenario;
import main.validator.ScenarioValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScenarioValidatorTest {

    private final ScenarioValidator validator = new ScenarioValidator();

    @Test
    public void testNullOrEmptyScenarioHandling() {
        assertFalse(validator.validate(null), "The validator should return false for a null scenario");

        Scenario emptyScenario = new Scenario.ScenarioBuilder().build();
        assertFalse(validator.validate(emptyScenario), "An empty scenario must not be validated, must return false");
    }

    @Test
    public void testReconUAVWithMunitionFailsValidation() {
        ReconMissionComponentFactory reconFactory = new ReconMissionComponentFactory();
        AttackMissionComponentFactory attackFactory = new AttackMissionComponentFactory();
        TargetFactory targetFactory=new TargetFactory();

        UAV reconUAV = reconFactory.createUAV();
        Munition heavyMunition = attackFactory.createMunition();

        if (heavyMunition != null) {
            reconUAV.addMunition(heavyMunition);
        }

        Mission mission = new Mission(MissionType.RECON, "Keşif Görevi", "Açık", RangeCategory.SHORT);
        Scenario invalidScenario = new Scenario.ScenarioBuilder()
                .setMission(mission)
                .addUAV(reconUAV)
                .addTarget(TargetFactory.createFixedTarget()) //sabit hedef verdik
                .build();

        assertFalse(validator.validate(invalidScenario), "A reconnaissance UAV carrying munitions must not pass validation");
    }

    @Test
    public void testZeroTargetsFailsValidation() {
        AttackMissionComponentFactory factory = new AttackMissionComponentFactory();
        Mission mission = new Mission(MissionType.ATTACK, "Saldırı", "Açık", RangeCategory.SHORT);

        Scenario noTargetScenario = new Scenario.ScenarioBuilder()
                .setMission(mission)
                .addUAV(factory.createUAV())
                .build();

        assertFalse(validator.validate(noTargetScenario), "A scenario with zero targets should not be accepted");
    }

    @Test
    public void testExcessiveTargetsHandling() {
        AttackMissionComponentFactory factory = new AttackMissionComponentFactory();
        TargetFactory targetFactory = new TargetFactory();
        Mission mission = new Mission(MissionType.ATTACK, "Saldırı", "Açık", RangeCategory.SHORT);

        Scenario.ScenarioBuilder builder = new Scenario.ScenarioBuilder()
                .setMission(mission)
                .addUAV(factory.createUAV());

        for (int i = 0; i < 10000; i++) {
            builder.addTarget(targetFactory.createFixedTarget());
        }

        Scenario overLoadedScenario = builder.build();

        // 10.000 hedef yüklenirken sistemin çökmediğini ve doğrulamanın çalıştığını teyit ediyoruz
        assertDoesNotThrow(() -> validator.validate(overLoadedScenario), "The code must not crash in an overshoot scenario");
        assertNotNull(overLoadedScenario, "Scenario should be created without out-of-memory errors");
    }
}