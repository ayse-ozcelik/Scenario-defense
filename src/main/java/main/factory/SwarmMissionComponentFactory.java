package main.factory;

import main.UAV.AttackUAV;
import main.UAV.UAV;
import main.munition.LightMunition;
import main.munition.Munition;
import main.sensor.EOSensor;
import main.sensor.Sensor;

public class SwarmMissionComponentFactory implements IMissionComponentFactory{
    public SwarmMissionComponentFactory(){}

    private static final UAV prototypeUAV = new AttackUAV("UAV-ATTACK", "Bayraktar TB2", 220.0, 200.0,4);
    private static final Sensor prototypeSensor = new EOSensor("S-E0SENSOR", "Electro Optic ", 50.0, 100.0, 30.0);
    private static final Munition prototypeMunition = new LightMunition("M-LIGHT", "MAM-L", 22, 15);
    @Override
    public UAV createUAV() {
        return prototypeUAV.clone();
    }

    @Override
    public Sensor createSensor() {
        return prototypeSensor.clone();
    }

    @Override
    public Munition createMunition() {
        return prototypeMunition.clone();
    }
}
