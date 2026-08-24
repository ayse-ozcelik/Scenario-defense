package main.validator;
import main.mission.MissionType;
import main.scenario.Scenario;
import main.UAV.UAV;
import main.mission.Mission;
public class ScenarioValidator {
    public boolean validate(Scenario scenario){
        if(scenario.getUavs()==null || scenario.getUavs().isEmpty()){
            System.out.println("İHA bulunamadı");
            return false;
        }
        if (scenario.getTargets()==null || scenario.getTargets().isEmpty()){
            System.out.println("Hedef bulunamadı");
            return false;
        }
        if (scenario.getMission().getMissionType()== MissionType.RECON){
            for(UAV uav: scenario.getUavs()){
                if(uav.getMunitions()!=null && !uav.getMunitions().isEmpty()){
                    System.out.println("Keşif (recon) görevinde mühimmat taşınamaz");
                    return false;
                }
            }
        }


        System.out.println("Senaryo şimdilik kurallara uygun");
        return true;
    }
}
