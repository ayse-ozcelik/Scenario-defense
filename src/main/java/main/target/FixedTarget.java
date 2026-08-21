package main.target;

public class FixedTarget extends Target {
    private String structureType;
    public FixedTarget(String id, String name, double safetyDistance, String structureType) {
        super(id, name, safetyDistance);
        this.structureType = structureType;
    }

    @Override
    public void track() {
        System.out.println(name + " (" + structureType + ") sabit hedefi koordinatlarında tespit edildi.");
    }
    @Override
    public String toString() {
        return name + " (" + structureType + ") sabit hedefi koordinatlarında tespit edildi.";
    }
}
