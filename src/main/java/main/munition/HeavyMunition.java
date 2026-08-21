package main.munition;

public class HeavyMunition extends Munition {
    private double explosiveAmount;
    public HeavyMunition(String id, String name, int numberOfMunition, double explosiveAmount) {
        super(id, name, numberOfMunition);
        this.explosiveAmount = explosiveAmount;
    }

    @Override
    public void fire() {
        System.out.println(name + " ağır mühimmatı " + explosiveAmount + " kg TNT patlama etkisi yarattı.");
    }
}
