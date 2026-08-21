package main.target;

public class TargetFactory {
    public static Target createTarget(String type, String id, String name, double safetyDistance) {
        Target target;
        if (type.equalsIgnoreCase("FIXED")) {
            target = new FixedTarget(id, name, safetyDistance, "Bunker");
        } else if (type.equalsIgnoreCase("MOVING")) {
            target = new MovingTarget(id, name, safetyDistance, 80.0);
        } else if (type.equalsIgnoreCase("MULTIPLE")) {
            target = new MultipleTarget(id, name, safetyDistance, 5);
        } else {
            throw new IllegalArgumentException("Geçersiz Hedef Tipi: " + type);
        }
        return target;


    }
}