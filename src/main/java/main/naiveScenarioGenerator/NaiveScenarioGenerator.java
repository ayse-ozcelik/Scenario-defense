package main.naiveScenarioGenerator;

import main.factory.*;
import main.mission.*;
import main.munition.*;
import main.scenario.Scenario;
import main.sensor.*;
import main.target.*;
import main.UAV.*;

public class NaiveScenarioGenerator {

    public Scenario generateScenario() {
        //1. Sabit Görev Oluşturma
        Mission mission = new Mission(MissionType.ATTACK, "Standart Durum", "Açık Hava", RangeCategory.MEDIUM);
        IMissionComponentFactory factory = new AttackMissionComponentFactory();

        //2. Sabit Hedef Oluşturma
        Target target = TargetFactory.createFixedTarget();

        //3. ScenarioBuilder ile Senaryoyu Başlatma
        Scenario.ScenarioBuilder builder = new Scenario.ScenarioBuilder()
                .setMission(mission)
                .addTarget(target);

        //4. Sabit 1 Adet İHA, Sensör ve Mühimmat Ekleme (Prototip ile eşit sayıda)
        UAV uav = factory.createUAV();
        uav.setId(uav.getId() + "-1");

        Sensor sensor = factory.createSensor();
        if (sensor != null) {
            sensor.setId(sensor.getId() + "-1-1");
            uav.addSensor(sensor);
        }

        Munition munition = factory.createMunition();
        if (munition != null) {
            munition.setId(munition.getId() + "-1-1");
            uav.addMunition(munition);
        }

        builder.addUAV(uav);

        //5. Tamamlanmış Senaryoyu Döndürme
        return builder.build();
    }
}