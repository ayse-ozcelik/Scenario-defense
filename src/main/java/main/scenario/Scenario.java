package main.scenario;
import java.util.*;
import main.mission.Mission;
import main.mission.MissionType;
import main.UAV.UAV;
import main.target.Target;
public class Scenario {
    private List<UAV> uavs;
    private Mission mission;
    private List<Target> target;


    public List<UAV> getUavs() { return uavs; }

    public void setUavs(List<UAV> uavs) { this.uavs = uavs; }


    public Mission getMission() { return mission; }

    public void setMission(Mission mission) { this.mission = mission; }


    public List<Target> getTarget() { return target; }

    public void setTarget(List<Target> target) { this.target = target; }



    public Scenario(Mission mission, Target target){
        this.mission=mission;
        this.target=new ArrayList<>();
        this.uavs=new ArrayList<>();
    }

    public void addUAV(UAV uav){
        this.uavs.add(uav);
    }

    public void addTarget(Target target){
        this.target.add(target);
    }

    public void printInfo() {
        System.out.println("Type of mission: "+mission.getMissionType()+ "Target: " + target.size() + "Number of UAV's : "+uavs.size());
    } //mission ve target için ilgili sınıflara eriştik ve get ile değişkenlerini aldık
    //ama uav için bu sınıfta liste oluşturduğumuz için direkt eriştik ve size ile boyutunu belirledik

}


//clonable yapılabilir araştır