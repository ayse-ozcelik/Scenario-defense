package main.naiveScenarioGenerator;

import main.UAV.AttackUAV;
import main.UAV.UAV;
import main.mission.Mission;
import main.mission.MissionType;
import main.mission.RangeCategory;
import main.munition.HeavyMunition;
import main.munition.Munition;
import main.scenario.Scenario;
import main.sensor.RadarSensor;
import main.sensor.Sensor;
import main.target.FixedTarget;
import main.target.Target;

public class NaiveScenarioGenerator {

    public Scenario generateScenario() {
        // 1. Mission (ScenarioManager ile birebir aynı değerler)
        Mission mission = new Mission(
                MissionType.ATTACK,
                "Operasyonel Devriye",
                "Açık Hava",
                RangeCategory.MEDIUM
        );

        // 2. FixedTarget (TargetFactory.fixedTargetPrototype ile birebir aynı değerler, saf new ile)
        Target target = new FixedTarget("T-FIXED", "Sabit Hedef", 100.0, "Bunker");

        // 3. AttackUAV (prototypeUAV ile birebir aynı değerler, saf new ile)
        UAV uav = new AttackUAV("UAV-ATTACK", "Bayraktar TB2", 220.0, 200.0, 4);

        // 4. RadarSensor (prototypeSensor ile birebir aynı değerler, saf new ile)
        Sensor sensor = new RadarSensor("S-RADAR", "AESA Radar", 20.0, 30.0, 430.0);
        uav.addSensor(sensor);

        // 5. HeavyMunition (prototypeMunition ile birebir aynı değerler, saf new ile)
        Munition munition = new HeavyMunition("M-HEAVY", "MK-82", 230, 500.0);
        uav.addMunition(munition);

        // 6. ScenarioBuilder ile İnşa Etme
        return new Scenario.ScenarioBuilder()
                .setMission(mission)
                .addTarget(target)
                .addUAV(uav)
                .build();
    }
}