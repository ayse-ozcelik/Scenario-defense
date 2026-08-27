package main.scenario;

import main.UAV.*;
import main.mission.*;
import main.target.*;


public class ScenarioTest {
    public static void main(String[] args) {
        Scenario scenario=new Scenario.ScenarioBuilder()
                .addUAV(new AttackUAV("UAV-0", "Bayraktar TB2", 220.0, 200.0, 4))
                .addUAV(new ReconUAV("UAV-1", "Anka-S", 220.0, 300.0, 5000.0))
                .setMission(new Mission(MissionType.ATTACK, "Standart Durum", "Açık Hava", RangeCategory.MEDIUM))
                .addTarget(new FixedTarget("T-1", "Sığınak", 32.0, "Bunker"))
                .build();


        System.out.println(scenario);
    }
}
