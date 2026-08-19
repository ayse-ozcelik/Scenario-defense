package main.UAV;

public class AttackUAV extends UAV {
    private int missileCapacity;

    public AttackUAV(String id, String name,  double maxWeightCapacity, double maxSpeed, int missileCapacity) {
        super(id, name, maxWeightCapacity,maxSpeed);
        this.missileCapacity = missileCapacity;
    }

    @Override
    public void fly() {
        System.out.println(name + " taarruz İHA'sı " + missileCapacity + " adet mühimmat ile devriye atıyor.");
    }
}
