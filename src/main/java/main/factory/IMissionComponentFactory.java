package main.factory;
import main.UAV.*;
import main.sensor.*;
import main.munition.*;
public interface IMissionComponentFactory {
    UAV createUAV();
    Sensor createSensor();
    Munition createMunition();
}


/*
public class Main {
    public static void main(String[] args) {
        IMissionComponentFactory attackFactory = new AttackMissionComponentFactory();
        IMissionComponentFactory patrolFactory = new PatrolMissionComponentFactory();
        IMissionComponentFactory reconFactory = new ReconMissionComponentFactory();
        IMissionComponentFactory swarmFactory = new SwarmMissionComponentFactory();

        UAV attackUav = attackFactory.createUAV();
        Sensor attackSensor = attackFactory.createSensor();
        Munition attackMun = attackFactory.createMunition();

        UAV reconUav = reconFactory.createUAV();
        Sensor reconSensor = reconFactory.createSensor();
        Munition reconMun = reconFactory.createMunition();

        UAV patrolUav = patrolFactory.createUAV();
        Sensor patrolSensor = patrolFactory.createSensor();
        Munition patrolMun = patrolFactory.createMunition();

        UAV swarmUav = swarmFactory.createUAV();
        Sensor swarmSensor = swarmFactory.createSensor();
        Munition swarmMun = swarmFactory.createMunition();

    }

 */

