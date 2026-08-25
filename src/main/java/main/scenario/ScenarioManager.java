package main.scenario;

import main.factory.AttackMissionComponentFactory;
import main.factory.IMissionComponentFactory;
import main.target.TargetFactory;
import main.validator.ScenarioValidator;
import main.mission.*;

public class ScenarioManager {

    //singleton mantığıyla çalışan class:
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

        baseScenarioPrototype = new Scenario.ScenarioBuilder()
                .setMission(baseMission)
                .addUAV(attackFactory.createUAV())
                .addTarget(targetFactory.createFixedTarget())
                .build();
        //target factory ile bir hedef oluşturuldu, attack factory ile uav oluşturuldu
        //birleştirildi ve base scenario elde edildi
    }
    public Scenario generateScenario() {
        Scenario scenario = baseScenarioPrototype.clone();

        ScenarioValidator validator = new ScenarioValidator();
        if (validator.validate(scenario)) {
            return scenario;
        } else {
            return null;
        }
    }

    public static ScenarioManager getInstance() {
        if (instance == null) {
            instance = new ScenarioManager();
        }
        return instance;
    }
}
