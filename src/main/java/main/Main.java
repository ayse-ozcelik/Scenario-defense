package main;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;
import main.scenario.ScenarioManager;

public class Main {
    public static void main(String[] args) {
        ScenarioManager scenarioManager=ScenarioManager.getInstance();
        Scenario scenario= scenarioManager.generateScenario();

        System.out.println("Senaryo: ");
        System.out.println(scenario.toString());
    }
}
