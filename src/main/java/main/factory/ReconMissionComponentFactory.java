package main.factory;

import main.UAV.ReconUAV;
import main.UAV.UAV;
import main.munition.Munition;
import main.sensor.EOSensor;
import main.sensor.Sensor;

public class ReconMissionComponentFactory implements IMissionComponentFactory{
    public ReconMissionComponentFactory(){}

    @Override
    public UAV createUAV() {
        return new ReconUAV("UAV-RECON", "Anka-S", 220.0, 300.0,5000.0);
    }

    @Override
    public Sensor createSensor() {
        return new EOSensor("S-E0SENSOR", "Electro Optic ", 50.0, 100.0, 30.0);
    }

    @Override
    public Munition createMunition() {
        return null;
    }

    //havada kalış süresini azamiye çıkarmak için burada mühimmat eklemiyoruz.
}
