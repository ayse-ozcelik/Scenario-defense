package main.sensor;

public class RadarSensor extends Sensor {
    private double operatingFrequency;
    public RadarSensor(String id, String name, double range, double powerConsumption, double operatingFrequency){
        super(id,name,range,powerConsumption);
        this.operatingFrequency=operatingFrequency;
    }

    @Override
    public void scan(){
        System.out.println(name + ", " + operatingFrequency + " GHz frekansında radar taraması yapıyor.");    }
    @Override
    public String toString(){
        return name + ", " + operatingFrequency + " GHz frekansında radar taraması yapıyor.";

    }
}

