package main.UAV;
import main.munition.Munition;
import main.sensor.Sensor;

import java.util.*;

public abstract class UAV {
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



}

//Sensor (dönüş tipi), sensor(liste), sensor(elimizdeki 1 örnek)
