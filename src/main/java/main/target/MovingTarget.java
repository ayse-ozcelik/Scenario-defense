package main.target;

public class MovingTarget extends Target {
    private double speed;
    public MovingTarget(String id, String name, double safetyDistance, double speed) {
        super(id, name, safetyDistance);
        this.speed = speed;
    }

    @Override
    public void track() {
        System.out.println(name + " hareketli hedefi " + speed + " km/s hızla ilerliyor.");
    }
}
