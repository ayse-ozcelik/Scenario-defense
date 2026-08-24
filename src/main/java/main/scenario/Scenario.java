package main.scenario;
import java.util.*;
import main.mission.Mission;
import main.UAV.UAV;
import main.munition.Munition;
import main.sensor.Sensor;
import main.target.Target;
public class Scenario implements Cloneable{
    private List<UAV> uavs;
    private Mission mission;
    private List<Target> targets;


    public List<UAV> getUavs() {
        return uavs;
    }

    public void setUavs(List<UAV> uavs) {
        this.uavs = uavs;
    }


    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }


    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }


    private Scenario(List<UAV> uavs, Mission mission, List<Target> targets) {
        this.mission = mission;
        this.targets = targets;
        this.uavs = uavs;
        //this.addTarget(targets);
    }

    public void addUAV(UAV uav) {
        this.uavs.add(uav);
    }

    public void addTarget(Target target) {
        this.targets.add(target);
    }

    public void printInfo() {
        System.out.println("Type of mission: " + mission.getMissionType() + "Target: " + targets.size() + "Number of UAV's : " + uavs.size());
    } //mission ve target için ilgili sınıflara eriştik ve get ile değişkenlerini aldık
    //ama uav için bu sınıfta liste oluşturduğumuz için direkt eriştik ve size() ile boyutunu belirledik


    public static class ScenarioBuilder {
        private List<UAV> uavs = new ArrayList<>();
        private Mission mission;
        private List<Target> targets = new ArrayList<>();

        public ScenarioBuilder() {
        }

        public ScenarioBuilder(List<UAV> uavs, Mission mission, List<Target> targets) {
            this.uavs = uavs;
            this.mission = mission;
            this.targets = targets;
        }

        public ScenarioBuilder addUAV(UAV uav) {
            this.uavs.add(uav);
            return this;
        }

        public ScenarioBuilder setMission(Mission mission) {
            this.mission = mission;
            return this;
        }

        public ScenarioBuilder addTarget(Target target) {
            this.targets.add(target);
            return this;
        }

        public Scenario build() {
            return new Scenario(uavs, mission, targets);
        }
    }
    @Override
    public String toString(){
        return "Scenario; "+
                "uavs: "+uavs+
                "mission: "+mission+
                "target: "+targets;
    }

    @Override
    public Scenario clone() {
        Scenario scenario = null;
        try {
            scenario = (Scenario) super.clone();

            scenario.uavs = new ArrayList<>();
            for (UAV u : this.uavs) {
                scenario.uavs.add(u.clone());
            }

            scenario.targets = new ArrayList<>();
            for (Target t : this.targets) {
                scenario.targets.add(t.clone());
            }

            return scenario;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("An error occurred while copying the Scenario", e);
        }
    }
}
