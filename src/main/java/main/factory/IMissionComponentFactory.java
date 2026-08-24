package main.factory;
import main.UAV.*;
import main.sensor.*;
import main.munition.*;
public interface IMissionComponentFactory {
    UAV createUAV();
    Sensor createSensor();
    Munition createMunition();
}
