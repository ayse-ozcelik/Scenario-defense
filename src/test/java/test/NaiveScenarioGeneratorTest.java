package test;

import main.UAV.AttackUAV;
import main.factory.AttackMissionComponentFactory;
import main.factory.ReconMissionComponentFactory;
import main.munition.Munition;
import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;
import main.mission.MissionType;
import main.mission.Mission;
import main.mission.RangeCategory;
import main.target.Target;
import main.target.TargetFactory;
import main.UAV.UAV;

import main.scenario.ScenarioManager;
import main.sensor.Sensor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class NaiveScenarioGeneratorTest {

    @Test
    public void testScenarioNotNull() {
        Mission mission = new Mission(MissionType.ATTACK, "Standart Durum", "Açık Hava", RangeCategory.SHORT);

        Scenario scenario = new Scenario.ScenarioBuilder()
                .setMission(mission)
                .build();

        assertNotNull(scenario);
        assertNotNull(scenario.getMission());
    }

    @Test
    public void testUavListNotEmpty() {
        AttackMissionComponentFactory factory = new AttackMissionComponentFactory();
        UAV uav = factory.createUAV();
        Mission mission = new Mission(MissionType.ATTACK, "Standart Durum", "Açık Hava", RangeCategory.SHORT);

        Scenario scenario = new Scenario.ScenarioBuilder()
                .setMission(mission)
                .addUAV(uav)
                .build();

        assertTrue(scenario.getUavs().size() > 0);
    }

    @Test
    public void testReconHasNoMunition() {
        ReconMissionComponentFactory factory=new ReconMissionComponentFactory();
        UAV uav=factory.createUAV();

        Mission mission = new Mission(MissionType.RECON, "Standart Durum", "Açık Hava", RangeCategory.SHORT);
        Scenario scenario = new Scenario.ScenarioBuilder()
                .setMission(mission)
                .addUAV(uav)
                .build();

        for (UAV uavs : scenario.getUavs()) {
            assertEquals(0, uav.getMunitions().size());
        }
    }

    @Test
    public void testFactoryCreatesCorrectUAVForAttack(){
        AttackMissionComponentFactory factory=new AttackMissionComponentFactory();

        UAV uav=factory.createUAV();
        Sensor sensor= factory.createSensor();
        Munition munition= factory.createMunition();

        assertNotNull(uav);
        assertNotNull(sensor);
        assertNotNull(munition);

        assertEquals("Bayraktar TB2",uav.getName());

    }

    @Test
    public void testScenarioBuilderCreatesValidScenario(){
        Mission mission = new Mission(MissionType.ATTACK, "Standart Durum", "Açık Hava", RangeCategory.SHORT);

        TargetFactory targetFactory = new TargetFactory();
        Target target = targetFactory.createFixedTarget();
        target.setId("T-1");
        target.setName("Sığınak");
        target.setSafetyDistance(32.0);

        AttackMissionComponentFactory factory = new AttackMissionComponentFactory();
        UAV uav = factory.createUAV();

        Scenario scenario = new Scenario.ScenarioBuilder()
                .setMission(mission)
                .addTarget(target)
                .addUAV(uav)
                .build();

        assertNotNull(scenario);
        assertEquals(mission, scenario.getMission());
        assertEquals(1, scenario.getUavs().size());
    }

     @Test
     public void testScenarioManagerIsSıngleton(){
         ScenarioManager instance1= ScenarioManager.getInstance();
         ScenarioManager instance2= ScenarioManager.getInstance();

         assertSame(instance1,instance2);
     }


}