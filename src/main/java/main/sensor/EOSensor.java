package main.sensor;

public class EOSensor extends Sensor{

    private double opticalZoom;

    public EOSensor(String id, String name, double range, double powerConsumption, double opticalZoom){
        super(id,name,range,powerConsumption);
        this.opticalZoom=opticalZoom;
    }

    @Override
    public void scan(){
        System.out.println(name+ " optik kamera ile "+opticalZoom+" x zoom yaparak tarıyor.");
    }
    @Override
    public String toString(){
        return name+ " optik kamera ile "+opticalZoom+" x zoom yaparak tarıyor.";
    }

}
