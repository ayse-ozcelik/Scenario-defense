package main.munition;

public class GuidedMunition extends Munition {
    private String guidanceType;

    public GuidedMunition(String id, String name, double numberOfMunition, String guidanceType) {
        super(id, name, numberOfMunition);
        this.guidanceType = guidanceType;
    }

    @Override
    public void fire() {
        System.out.println(name + " mühimmatı " + guidanceType + " güdümü ile hedefe kilitlendi.");    }
}
