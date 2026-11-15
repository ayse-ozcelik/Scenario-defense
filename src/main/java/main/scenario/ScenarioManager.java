package main.scenario;

import main.factory.AttackMissionComponentFactory;
import main.factory.IMissionComponentFactory;
import main.target.TargetFactory;
import main.validator.ScenarioValidator;
import main.mission.*;
import main.UAV.*;
import main.target.*;
import main.sensor.*;
import main.munition.*;

public class ScenarioManager { //singleton mantığıyla çalışacak class:

    private static ScenarioManager instance;
    private static Scenario baseScenarioPrototype;

    private ScenarioManager() {
        if (baseScenarioPrototype == null) {
            initializePrototype();
        }
    }

    private void initializePrototype() {
        IMissionComponentFactory attackFactory = new AttackMissionComponentFactory();
        TargetFactory targetFactory = new TargetFactory();

        Mission baseMission = new Mission(
                MissionType.ATTACK,
                "Operasyonel Devriye",
                "Açık Hava",
                RangeCategory.MEDIUM
        );

        UAV uav = attackFactory.createUAV();     // clone() ile prototypeUAV kopyalanır
        Sensor sensor = attackFactory.createSensor(); // clone() ile prototypeSensor kopyalanır
        Munition munition = attackFactory.createMunition(); // clone() ile prototypeMunition kopyalanır

        uav.addSensor(sensor);
        uav.addMunition(munition);

        baseScenarioPrototype = new Scenario.ScenarioBuilder()
                .setMission(baseMission)
                .addUAV(uav)
                .addTarget(targetFactory.createFixedTarget())
                .build();
    }
    //target factory ile bir hedef oluşturuldu, attack factory ile uav oluşturuldu
    //birleştirildi ve base scenario elde edildi
    public Scenario generateScenario() {
        Scenario scenario = baseScenarioPrototype.clone();

        ScenarioValidator validator = new ScenarioValidator();
        if (validator.validate(scenario)) {
            return scenario;
        } else {
            return null;
        }
    }

    // Benchmark için: dâhili doğrulama yapmadan yalnızca prototipi klonlar
    public Scenario generateScenarioWithoutValidation() {
        return baseScenarioPrototype.clone();
    }

    public static ScenarioManager getInstance() {
        if (instance == null) {
            instance = new ScenarioManager();
        }
        return instance;
    }
}