package main.validator;

import main.mission.MissionType;
import main.scenario.Scenario;
import main.UAV.UAV;

public class ScenarioValidator {

    public boolean validate(Scenario scenario) {
        //Güvenlik: Senaryo null ise direkt false döndür
        if (scenario == null) return false;

        //İHA kontrolü
        if (scenario.getUavs() == null || scenario.getUavs().isEmpty()) {
            System.out.println("Can not found the UAV");
            return false;
        }

        //Hedef kontrolü
        if (scenario.getTargets() == null || scenario.getTargets().isEmpty()) {
            System.out.println("Can not found the target");
            return false;
        }

        //Keşif İHA'sı mühimmat taşıyamaz:
        if (scenario.getMission() != null && scenario.getMission().getMissionType() == MissionType.RECON) {
            for (UAV uav : scenario.getUavs()) {
                if (uav.getMunitions() != null && !uav.getMunitions().isEmpty()) {
                    System.out.println("Munition cannot be carried on a reconnaissance mission");
                    return false;
                }
            }
        }

        System.out.println("The scenario is available, for now");
        return true;
    }
}