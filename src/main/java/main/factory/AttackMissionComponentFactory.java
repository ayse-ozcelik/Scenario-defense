package main.factory;

import main.UAV.AttackUAV;
import main.UAV.CarrierUAV;
import main.UAV.ReconUAV;
import main.UAV.UAV;
import main.mission.MissionType;
import main.munition.HeavyMunition;
import main.munition.Munition;
import main.sensor.RadarSensor;
import main.sensor.Sensor;
import main.target.Target;

public class AttackMissionComponentFactory implements IMissionComponentFactory{

    private static final UAV prototypeUAV=new AttackUAV("UAV-ATTACK", "Bayraktar TB2", 220.0, 200.0, 4);
    private static final Sensor prototypeSensor=new RadarSensor("S-RADAR", "AESA Radar", 20.0, 30.0, 430);

    private static final Munition prototypeMunition=new HeavyMunition("M-HEAVY", "MK-82", 230, 500);

    public AttackMissionComponentFactory(){}

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
