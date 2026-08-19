package main.target;

public class MultipleTarget extends Target {
    private int targetCount;
    public MultipleTarget(String id, String name, double safetyDistance, int targetCount) {
        super(id, name, safetyDistance);
        this.targetCount = targetCount;
    }

    @Override
    public void track() {
        System.out.println(name + " konvoyu " + targetCount + " adet alt hedeften oluşuyor.");    }
}
