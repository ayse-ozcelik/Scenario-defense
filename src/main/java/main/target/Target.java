package main.target;

public abstract class Target {
    protected String id;
    protected String name;
    protected double safetyDistance;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getSafetyDistance() { return safetyDistance; }

    public void setSafetyDistance(double safetyDistance) { this.safetyDistance = safetyDistance; }


    public Target(String id, String name, double safetyDistance ){
        this.id=id;
        this.name=name;
        this.safetyDistance=safetyDistance;
    }

    public abstract void track();
    //abstract method, abstract class bunu yazmak (overrride etmek) zorunda.

    public void printInfo(){
        System.out.println("Target id: "+id+ "\nName: "+name+"\nSafety Distance: "+safetyDistance);
    }



}
