package main.munition;

import main.sensor.Sensor;

public abstract class Munition implements Cloneable{
    protected String id;
    protected String name;
    protected int numberOfMunition;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getNumberOfMunition() { return numberOfMunition; }

    public void setNumberOfMunition(int numberOfMunition) { this.numberOfMunition = numberOfMunition;}



    public Munition(String id, String name, int numberOfMunition){
        this.id=id;
        this.name=name;
        this.numberOfMunition=numberOfMunition;
    }


    public abstract void fire();
    //abstract method, abstract class bunu yazmak (overrride etmek) zorunda.

    public void printInfo(){
        System.out.println("Munition id: "+id+ "\nName: "+name+"\nNumber Of Munition: "+numberOfMunition);
    }

    @Override
    public Munition clone() {
        Munition munition = null;
        try {
            munition = (Munition) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("An error occurred while copying the munition", e);
        }

        return munition;
    }

}
