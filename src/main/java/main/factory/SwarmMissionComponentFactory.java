package main.factory;

import main.UAV.AttackUAV;
import main.UAV.UAV;
import main.munition.LightMunition;
import main.munition.Munition;
import main.sensor.EOSensor;
import main.sensor.Sensor;

public class SwarmMissionComponentFactory implements IMissionComponentFactory{
    public SwarmMissionComponentFactory(){}

    @Override
    public UAV createUAV() {
        return new AttackUAV("UAV-ATTACK", "Bayraktar TB2", 220.0, 200.0,4);
    }

    @Override
    public Sensor createSensor() {
        return new EOSensor("S-E0SENSOR", "Electro Optic ", 50.0, 100.0, 30.0);
    }

    @Override
    public Munition createMunition() {
        return new LightMunition("M-LIGHT", "MAM-L", 22, 15);
    }
}
