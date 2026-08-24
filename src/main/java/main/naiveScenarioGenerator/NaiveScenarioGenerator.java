package main.naiveScenarioGenerator;

import main.mission.*;
import main.scenario.Scenario;
import main.UAV.*;
import main.sensor.*;
import main.munition.*;
import main.target.*;
import java.util.*;
import main.factory.*;

public class NaiveScenarioGenerator {
    private Random random=new Random();

    private IMissionComponentFactory selectFactory(MissionType missionType) {
        switch (missionType) {
            case ATTACK:
                return new AttackMissionComponentFactory();
            case RECON:
                return new ReconMissionComponentFactory();
            case PATROL:
                return new PatrolMissionComponentFactory();
            case SWARM:
                return new SwarmMissionComponentFactory();
            default:
                throw new IllegalArgumentException("Bilinmeyen Görev Tipi: " + missionType);
        }
    }

    public Scenario generateScenario() {
        MissionType[] types =MissionType.values();
        MissionType chosenType =types[random.nextInt(types.length)];

        RangeCategory[] categories =RangeCategory.values();
        RangeCategory chosenCategory =categories[random.nextInt(categories.length)];

        Mission mission =new Mission(chosenType, "Standart Durum", "Açık Hava", chosenCategory);

        IMissionComponentFactory factory = selectFactory(chosenType);

        Target target;
        int targetType = random.nextInt(3);

        if (targetType == 0) {
            target = TargetFactory.createTarget("FIXED", "T-1", "Sığınak", 32.0);
        } else if (targetType == 1) {
            target = TargetFactory.createTarget("MOVING", "T-2", "Konvoy", 40.1);
        } else {
            target = TargetFactory.createTarget("MULTIPLE", "T-3", "Grubu", 41.0);
        }

        Scenario.ScenarioBuilder builder =new Scenario.ScenarioBuilder()
                .setMission(mission)
                .addTarget(target);

        int uavCount =random.nextInt(5) + 1; //1-5 arası İHA üretsin

        for (int i= 0; i< uavCount; i++) {
            UAV uav=factory.createUAV();
            uav.setId(uav.getId()+"-"+(i + 1));


            //sensör için:
            //Mission recon ise EO sensör
            //Mission attack ise attack sensör
            //Mission swarm için ise random bir sensör atanmalı
            int sensorCount =random.nextInt(3) + 1;
            for (int j= 0; j< sensorCount; j++) {
                Sensor sensor=factory.createSensor();
                if (sensor != null) {
                    sensor.setId(sensor.getId()+"-"+(i + 1)+"-"+(j + 1));
                    uav.addSensor(sensor);
                }
            }

            int munitionCount = random.nextInt(3);

            for (int k = 0; k < munitionCount; k++) {
                Munition munition = factory.createMunition();
                if (munition != null) {
                    munition.setId(munition.getId()+ "-" +(i + 1)+"-"+(k + 1));
                    uav.addMunition(munition);
                }
            }
            builder.addUAV(uav);
        }
        return builder.build();
    }
}