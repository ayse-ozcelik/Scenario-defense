package main;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;
import main.scenario.ScenarioManager;

public class Main {
    public static void main(String[] args) {
        ScenarioManager scenarioManager=ScenarioManager.getInstance();
        Scenario scenario1= scenarioManager.generateScenario();
        Scenario scenario2= scenarioManager.generateScenario();

        System.out.println("Scenario 1");
        if(scenario1!=null){
            System.out.println(scenario1.toString());
        }

        System.out.println("Scenario 2");
        if(scenario2!=null){
            System.out.println(scenario2.toString());
        }

        System.out.println("\nAre the scenarios have different object?  "+(scenario1!=scenario2));

    }
}
