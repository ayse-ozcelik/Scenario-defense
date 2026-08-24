package main.sensor;

public abstract class Sensor implements Cloneable {
    protected String id;
    protected String name;
    protected double range;
    protected double powerConsumption; //güç tüketimi

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }


    public double getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }


    public Sensor(String id, String name, double range, double powerConsumption) {
        this.id = id;
        this.name = name;
        this.range = range;
        this.powerConsumption = powerConsumption;
    }

    public abstract void scan();
    //abstract method, abstract class bunu yazmak (overrride etmek) zorunda.

    public void printInfo() {
        System.out.println("Sensor id: " + id + "\nName: " + name + "\nRange: " + range + "\nPower Consumption: " + powerConsumption);


    }

    @Override
    public Sensor clone() {
        Sensor sensor = null;
        try {
            sensor = (Sensor) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("An error occurred while copying the sensor", e);
        }

        return sensor;


    }
}