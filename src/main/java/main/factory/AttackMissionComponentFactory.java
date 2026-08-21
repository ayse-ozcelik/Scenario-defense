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

public class AttackMissionComponentFactory implements IMissionComponentFactory{

    public AttackMissionComponentFactory(){}

    @Override
    public UAV createUAV() {
        return new AttackUAV("UAV-ATTACK", "Bayraktar TB2", 220.0, 200.0,4);
    }

    @Override
    public Sensor createSensor() {
        return new RadarSensor("S-RADAR", "AESA Radar ",20.0,30.0,430);
    }

    @Override
    public Munition createMunition() {
        return new HeavyMunition("M-HEAVY", "MK-82", 230, 500);
    }
}
