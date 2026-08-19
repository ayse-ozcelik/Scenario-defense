package main.munition;

public abstract class Munition {
    protected String id;
    protected String name;
    protected double numberOfMunition;


    public Munition(String id, String name, double numberOfMunition){
        this.id=id;
        this.name=name;
        this.numberOfMunition=numberOfMunition;
    }


    public abstract void fire();
    //abstract method, abstract class bunu yazmak (overrride etmek) zorunda.

    public void printInfo(){
        System.out.println("Munition id: "+id+ "\nName: "+name+"\nNumber Of Munition: "+numberOfMunition);
    }

//Get set methodlarını yaz
}
