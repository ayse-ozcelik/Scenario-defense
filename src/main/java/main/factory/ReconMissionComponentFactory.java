package main.factory;

import main.UAV.ReconUAV;
import main.UAV.UAV;
import main.munition.Munition;
import main.sensor.EOSensor;
import main.sensor.Sensor;

public class ReconMissionComponentFactory implements IMissionComponentFactory{
    public ReconMissionComponentFactory(){}


    private static final UAV prototypeUAV = new ReconUAV("UAV-RECON", "Anka-S", 220.0, 300.0,5000.0);
    private static final Sensor prototypeSensor = new EOSensor("S-E0SENSOR", "Electro Optic ", 50.0, 100.0, 30.0);

    @Override
    public UAV createUAV() {
        return prototypeUAV;
    }
    @Override
    public Sensor createSensor() {
        return prototypeSensor;
    }

    @Override
    public Munition createMunition() {
        return null;
    }

    //havada kalış süresini azamiye çıkarmak için bu aşamada mühimmat eklemiyoruz.
}
