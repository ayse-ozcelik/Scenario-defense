package main.UAV;
import main.munition.Munition;
import main.sensor.Sensor;
import main.target.Target;

import java.util.*;

public abstract class UAV implements Cloneable {
    protected String id;
    protected String name;
    protected double maxWeightCapacity; //azami taşıma kapasitesi
    protected double maxSpeed;
    protected List<Sensor> sensors;
    protected List<Munition> munitions; //list tanımlama


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }


    public double getMaxWeightCapacity() { return maxWeightCapacity; }

    public void setMaxWeightCapacity(double maxWeightCapacity) { this.maxWeightCapacity = maxWeightCapacity; }


    public double getMaxSpeed() { return maxSpeed; }

    public void setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }

    public void setSensors(List<Sensor> sensors) { this.sensors = sensors; }


    public List<Munition> getMunitions() { return munitions; }

    public void setMunitions(List<Munition> munitions) { this.munitions = munitions; }

    public UAV(String id, String name, double maxWeightCapacity, double maxSpeed ){
        this.id=id;
        this.name=name;
        this.maxWeightCapacity=maxWeightCapacity;
        this.maxSpeed=maxSpeed;

        this.sensors=new ArrayList<>();
        this.munitions=new ArrayList<>();

    }


    //değişkenleri ekleme görevi görecek yardımcı method
    public void addSensor(Sensor sensor){
        this.sensors.add(sensor);
    }

    public void addMunition(Munition munition){
       this.munitions.add(munition);
    }
    public List<Sensor> getSensors(){ return sensors;
    }
    public abstract void fly();
    //abstract method, abstract class bunu yazmak (overrride etmek) zorunda.

    public void printInfo(){
        System.out.println("Sensor id: "+id+ "\nName: "+name+"\nMax Weight Capacity: "+maxWeightCapacity+"\nMax Speed: "+maxSpeed);
    }

    @Override
    public UAV clone() {
        UAV uav = null;
        try {
            uav = (UAV) super.clone();

            uav.sensors = new ArrayList<>();
            for (Sensor s : this.sensors) {
                uav.sensors.add(s.clone());
            }
            uav.munitions = new ArrayList<>();
            for (Munition s : this.munitions) {
                uav.munitions.add(s.clone());
            }
            return uav;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("An error occurred while copying the UAV", e);
        }
    }


}

//Sensor (dönüş tipi), sensor(liste), sensor(elimizdeki 1 örnek)
