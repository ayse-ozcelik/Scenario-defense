package test;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;
import main.mission.MissionType;
import main.UAV.UAV;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NaiveScenarioGeneratorTest {

    @Test
    public void testScenarioNotNull() {
        NaiveScenarioGenerator generator = new NaiveScenarioGenerator();
        Scenario scenario = generator.generateScenario();

        assertNotNull(scenario);
        assertNotNull(scenario.getMission());
    }

    @Test
    public void testUavListNotEmpty() {
        NaiveScenarioGenerator generator = new NaiveScenarioGenerator();
        Scenario scenario = generator.generateScenario();

        assertTrue(scenario.getUavs().size() > 0);
    }

    @Test
    public void testReconHasNoMunition() {
        NaiveScenarioGenerator generator = new NaiveScenarioGenerator();
        Scenario scenario;

        do {
            scenario = generator.generateScenario();
        } while (scenario.getMission().getMissionType() != MissionType.RECON);

        for (UAV uav : scenario.getUavs()) {
            assertEquals(0, uav.getMunitions().size());
        }
    }
}