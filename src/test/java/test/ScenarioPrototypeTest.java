package test;

import main.UAV.UAV;
import main.scenario.Scenario;
import main.scenario.ScenarioManager;
import main.mission.Mission;
import main.mission.MissionType;
import main.mission.RangeCategory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScenarioPrototypeTest {

    @Test
    public void testScenarioCloningIsDeepAndIndependent() {
        ScenarioManager manager = ScenarioManager.getInstance();
        Scenario scenario1 = manager.generateScenario();
        Scenario scenario2 = manager.generateScenario();

        assertNotSame(scenario1, scenario2, "Scenarios must be located at different addresses in memory");

        if (!scenario1.getUavs().isEmpty()) {
            scenario1.getUavs().get(0).setId("Changed-ID-999");
        }

        if (scenario1.getMission() != null) {
            scenario1.setMission(new Mission(
                    MissionType.ATTACK,
                    "Modified Title",
                    "Modified Description",
                    RangeCategory.LONG
            ));
        }

        if (!scenario1.getUavs().isEmpty() && !scenario2.getUavs().isEmpty()) {
            assertNotEquals(
                    scenario1.getUavs().get(0).getId(),
                    scenario2.getUavs().get(0).getId(),
                    "The UAV ID change in Scenario 1 should not affect Scenario 2"
            );
        }

        if (scenario1.getMission() != null && scenario2.getMission() != null) {
            assertNotSame(
                    scenario1.getMission(),
                    scenario2.getMission(),
                    "Mission objects must be independent copies in RAM"
            );
        }
    }
}