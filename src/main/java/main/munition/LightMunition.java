package main.munition;

public class LightMunition extends Munition {
    private int caliber;
    public LightMunition(String id, String name, int numberOfMunition, int caliber) {
        super(id, name, numberOfMunition);
        this.caliber = caliber;
    }

    @Override
    public void fire() {
        System.out.println(name + " (" + caliber + "mm) seri atış modunda ateşlendi.");
    }
}
