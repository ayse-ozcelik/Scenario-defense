package main.naiveScenarioGenerator;

import main.mission.*;
import main.scenario.Scenario;
import main.UAV.*;
import main.sensor.*;
import main.munition.*;
import main.target.*;
import java.util.*;

public class NaiveScenarioGenerator {
    private Random random=new Random();

    public Scenario generateScenario() {
        MissionType[] types =MissionType.values();
        MissionType chosenType =types[random.nextInt(types.length)];

        RangeCategory[] categories =RangeCategory.values();
        RangeCategory chosenCategory =categories[random.nextInt(categories.length)];

        Mission mission =new Mission(chosenType, "Standart Durum", "Açık Hava", chosenCategory);

        Target target =null;
        int targetType =random.nextInt(3); // 0, 1 veya 2 üretir

        if (targetType== 0) {
            target =new FixedTarget("T-1", "Sığınak", 32.0, "Bunker");
        } else if (targetType== 1) {
            target =new MovingTarget("T-2", "Konvoy", 40.1, 80.0);
        } else {
            target =new MultipleTarget("T-3", "Grubu", 41.0, 5);
        }

        Scenario scenario =new Scenario(mission, target);

        int uavCount =random.nextInt(5) + 1; // 1 ile 5 arası İHA üretir

        for (int i= 0; i< uavCount; i++) {
            UAV uav;
            int uavType =random.nextInt(3);

            if (uavType== 0) {
                uav =new AttackUAV("UAV-" + i, "Bayraktar TB2", 220.0, 4,2);
            } else if (uavType== 1) {
                uav =new ReconUAV("UAV-" + i, "Anka-S", 200.0, 5000.0,3);
            } else {
                uav =new CarrierUAV("UAV-" + i, "Kargo İHA", 150.0, 10.0,4);
            }

            int sensorCount =random.nextInt(3) + 1;
            for (int j= 0; j< sensorCount; j++) {
                Sensor sensor =new EOSensor("S-" + j, "ElectroOptic", 50.0, 100.0, 30.0);
                uav.addSensor(sensor);
            }

            int munitionCount =random.nextInt(3);
            for (int k= 0; k< munitionCount; k++) {
                Munition munition =new HeavyMunition("M-" + k, "MK-82", 230.0, 500.0);
                uav.addMunition(munition);
            }

            scenario.addUAV(uav);
        }
        return scenario;
    }
}