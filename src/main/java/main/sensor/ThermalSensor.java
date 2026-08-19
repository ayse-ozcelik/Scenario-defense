package main.sensor;

public class ThermalSensor extends Sensor {
    private double minTemperatureSensitivity;
    public ThermalSensor(String id, String name, double range, double powerConsumption, double minTemperatureSensitivity) {
        super(id, name, range, powerConsumption);
        this.minTemperatureSensitivity = minTemperatureSensitivity;
    }

    @Override
    public void scan() {
        System.out.println(name + " ısı imzası algılama modu aktif (Hassasiyet: " + minTemperatureSensitivity + "°C).");
    }
}
