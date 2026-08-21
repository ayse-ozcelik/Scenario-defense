package main.UAV;

public class CarrierUAV extends UAV {
    private double cargoVolume;
    public CarrierUAV(String id, String name,  double maxWeightCapacity, double maxSpeed, double cargoVolume) {
        super(id, name, maxWeightCapacity,maxSpeed);
        this.cargoVolume = cargoVolume;
    }

    @Override
    public void fly() {
        System.out.println(name + " kargo İHA'sı " + cargoVolume + " m³ yük ile intikal ediyor.");
    }
    @Override
    public String toString() {
       return name + " kargo İHA'sı " + cargoVolume + " m³ yük ile intikal ediyor.";
    }

}
