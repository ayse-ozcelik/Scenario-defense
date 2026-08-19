package main.sensor;

public abstract class Sensor {

    protected String id;
    protected String name;
    protected double range;
    protected double powerConsumption; //güç tüketimi


    public Sensor(String id, String name, double range, double powerConsumption ){
        this.id=id;
        this.name=name;
        this.range=range;
        this.powerConsumption=powerConsumption;
    }

    public abstract void scan();
    //abstract method, abstract class bunu yazmak (overrride etmek) zorunda.

    public void printInfo(){
        System.out.println("Sensor id: "+id+ "\nName: "+name+"\nRange: "+range+"\nPower Consumption: "+powerConsumption);
    }


//Get set methodlarını yaz

}
