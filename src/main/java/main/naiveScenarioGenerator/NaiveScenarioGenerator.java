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
        int targetType = random.nextInt(3); // 0, 1 veya 2 üretir

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

           /* if (chosenType== MissionType.ATTACK) {
                uav =new AttackUAV("UAV-" + i, "Bayraktar TB2", 220.0, 200.0,4);
            } else if (chosenType== MissionType.RECON) {
                uav =new ReconUAV("UAV-" + i, "Anka-S", 220.0, 300.0,5000.0);
            } else {
                uav =new CarrierUAV("UAV-" + i, "Kargo İHA", 150.0, 250.0,4.0);
            }

            */

            //sensör için:
            //Mission recon ise EO sensör
            //Mission attack ise attack sensör
            //Mission swarm için ise random bir sensör atanmalı
            int sensorCount =random.nextInt(3) + 1; //rastgele sensör sayısı
            for (int j= 0; j< sensorCount; j++) {
                Sensor sensor=factory.createSensor();
                if (sensor != null) {
                    uav.addSensor(sensor);
                }
               /* if (chosenType==MissionType.RECON){
                    if (random.nextBoolean()) {
                        sensor = new EOSensor("S-" + j, "Electro Optic ", 50.0, 100.0, 30.0);
                    }else
                        sensor=new ThermalSensor("S-"+j, "Thermal ",20.0,30.0,20.0);

                }else if(chosenType==MissionType.ATTACK){
                    sensor=new RadarSensor("S-"+j, "AESA Radar ",20.0,30.0,430);
                }else{
                    int randomSensor = random.nextInt(3);
                    if (randomSensor == 0) {
                        sensor = new EOSensor("S-" + j, "Electro Optic", 50.0, 100.0, 30.0);
                    } else if (randomSensor == 1) {
                        sensor = new ThermalSensor("S-" + j, "Thermal", 20.0, 30.0, 20.0);
                    } else {
                        sensor = new RadarSensor("S-" + j, "AESA Radar", 20.0, 30.0, 430);
                    }
                }

                */
            }
            int munitionCount = random.nextInt(3);

            for (int k = 0; k < munitionCount; k++) {
                Munition munition=factory.createMunition();
                if (munition != null) {
                    uav.addMunition(munition);
                }
                /*
                if (chosenType == MissionType.RECON) {
                    continue; //RECONda mühimmat ekleme döngüyü atla
                } else if (chosenType == MissionType.ATTACK) {
                    munition = new HeavyMunition("M-" + k, "MK-82", 230, 500);
                } else {
                    munition = new LightMunition("M-" + k, "MAM-L", 22, 15);
                }

                 */
            }
            builder.addUAV(uav);
        }
        return builder.build();
    }
}