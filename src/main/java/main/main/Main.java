package main.main;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;

public class Main {
    public static void main(String[] args) {
        NaiveScenarioGenerator generator=new NaiveScenarioGenerator();
        Scenario randomScenario= generator.generateScenario();

        System.out.println("Type Of Mission: " + randomScenario.getMission().getMissionType());
        System.out.println("Range: " + randomScenario.getMission().getRangeCategory());
        System.out.println("Number of UAV: " + randomScenario.getUavs().size());
    }
}
