package main.UAV;

public class ReconUAV extends UAV {
    private double maxFlightAltitude;
    public ReconUAV(String id, String name,  double maxWeightCapacity, double maxSpeed, double maxFlightAltitude) {
        super(id, name, maxWeightCapacity,maxSpeed);
        this.maxFlightAltitude = maxFlightAltitude;
    }

    @Override
    public void fly() {
        System.out.println(name + " keşif İHA'sı " + maxFlightAltitude + " metre irtifada istihbarat topluyor.");
    }
}
