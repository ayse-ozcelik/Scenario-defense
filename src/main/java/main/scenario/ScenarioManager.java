package main.scenario;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.validator.*;

public class ScenarioManager {

    //singleton mantığıyla çalışan class:
    private ScenarioManager(){}
    private static ScenarioManager instance;
    public Scenario generateScenario(){
        NaiveScenarioGenerator naiveScenarioGenerator=new NaiveScenarioGenerator();
        Scenario scenario=naiveScenarioGenerator.generateScenario();

        ScenarioValidator validator = new ScenarioValidator();
        if(validator.validate(scenario)){ //sonuç true ya da false çıkacak
            return scenario;
        }else
            return null;

    }


    public static ScenarioManager getInstance() {
        if(instance==null){
            instance=new ScenarioManager();
        }
        return instance;
    }


}
