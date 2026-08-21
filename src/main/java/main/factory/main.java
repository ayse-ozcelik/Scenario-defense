package main.factory;

import main.naiveScenarioGenerator.NaiveScenarioGenerator;
import main.scenario.Scenario;

public class main {
    public static void main(String[] args) {
        // NaiveScenarioGenerator nesnesi oluşturuyoruz
        NaiveScenarioGenerator generator = new NaiveScenarioGenerator();

        // Senaryoyu Abstract Factory & Builder ile üretiyoruz
        Scenario scenario = generator.generateScenario();

        System.out.println("Senaryo başarıyla üretildi!");
    }
}