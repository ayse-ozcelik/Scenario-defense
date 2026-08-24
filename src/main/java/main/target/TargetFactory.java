package main.target;

import main.factory.*;

public class TargetFactory {
    private static final Target fixedTargetPrototype = new FixedTarget("T-FIXED", "Sabit Hedef", 100.0, "Bunker");
    private static final Target movingTargetPrototype = new MovingTarget("T-MOVING", "Hareketli Hedef", 200.0, 80.0);
    private static final Target multipleTargetPrototype = new MultipleTarget("T-MULTI", "Çoklu Hedef", 150.0, 5);

    public static Target createFixedTarget() {
        return fixedTargetPrototype.clone();
    }

    public static Target createMovingTarget() {
        return movingTargetPrototype.clone();
    }

    public static Target createMultipleTarget() {
        return multipleTargetPrototype.clone();
    }
}