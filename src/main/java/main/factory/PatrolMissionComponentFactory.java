package main.factory;
import main.UAV.AttackUAV;
import main.UAV.CarrierUAV;
import main.UAV.UAV;
import main.mission.*;
import main.munition.Munition;
import main.sensor.Sensor;
import main.sensor.ThermalSensor;

public class PatrolMissionComponentFactory implements IMissionComponentFactory {
    public PatrolMissionComponentFactory() {
    }


    @Override
    public UAV createUAV() {
        return new CarrierUAV("UAV-CARRIER", "Kargo İHA", 150.0, 250.0,4.0);
    }

    @Override
    public Sensor createSensor() {
        return new ThermalSensor("S-THERMAL", "Thermal ",20.0,30.0,20.0);
    }

    @Override
    public Munition createMunition() {
        return null;
    }
}
