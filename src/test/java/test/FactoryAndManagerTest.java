package test;

import main.UAV.AttackUAV;
import main.UAV.UAV;
import main.factory.AttackMissionComponentFactory;
import main.munition.Munition;
import main.scenario.ScenarioManager;
import main.sensor.Sensor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryAndManagerTest {

    @Test
    public void testAttackFactoryCreatesCorrectTypes() {
        AttackMissionComponentFactory factory = new AttackMissionComponentFactory();

        UAV uav = factory.createUAV();
        Sensor sensor = factory.createSensor();
        Munition munition = factory.createMunition();

        assertNotNull(uav);
        assertNotNull(sensor);
        assertNotNull(munition);

        assertInstanceOf(AttackUAV.class, uav, "The UAV to be produced must be of the AttackUAV type");
    }

    @Test
    public void testScenarioManagerIsSingleton() {
        ScenarioManager instance1 = ScenarioManager.getInstance();
        ScenarioManager instance2 = ScenarioManager.getInstance();

        assertNotNull(instance1);
        assertSame(instance1, instance2, "ScenarioManager getInstance() must always return the same object");
    }
}