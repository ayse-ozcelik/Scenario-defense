package main.UAV;
import main.munition.Munition;
import main.sensor.Sensor;

import java.util.*;

public abstract class UAV {

    protected String id;
    protected String name;
    protected double maxWeightCapacity; //azami taşıma kapasitesi
    protected double maxSpeed;
    protected List<Sensor> sensors;
    //sensors adlı liste değişkeninde Sensors türünde nesneler bulunsun
    //<Sensors> bir generictir. Liste içine başka veri tipi(metin ya da sayı) girmesini engeller
    protected List<Munition> munitions; //list tanımlama

    public UAV(String id, String name, double maxWeightCapacity, double maxSpeed ){
        this.id=id;
        this.name=name;
        this.maxWeightCapacity=maxWeightCapacity;
        this.maxSpeed=maxSpeed;

        // Listeleri constructor içinde boş olarak başlatıyoruz (NullPointerException almamak için)
        this.sensors=new ArrayList<>();
        this.munitions=new ArrayList<>();
        //Javada List bir arayüzdür(interface). O yüzden onun somut uygulaması olan ArrayList nesnesini new ile oluşturuyoruz
        //değişken kısmında tanımlama yaptık. Burada boş liste oluşturduk.
        //Çünkü uav nesnesi oluşturulduğunda constructor çalışacak ve new arraylist çalışıp boş liste oluşmuş olacak.

    }


    //elemanları ekleme görevi görecek yardımcı method
    public void addSensor(Sensor sensor){
        //dışarıdan istediği parametreler parantez içindedir
        //Sensor tipinde olmalı ve geçici değişken adı da sensor olmalı demek
        this.sensors.add(sensor);
    }

    public void addMunition(Munition munition){
        //dışarıdan istediği parametreler parantez içindedir
        //Sensor tipinde olmalı ve geçici değişken adı da sensor olmalı demek
        this.munitions.add(munition);
    }
    public List<Sensor> getSensors(){
        return sensors;
    //bu method dışarıya sensors isimli bir liste döneceği için gövdeye List yazdık.
    //tipi de Sensor olacak (class adı) (bu liste elemanlarının tipi Sensor sınıfıdır demek.)

    }
    public abstract void fly();
    //abstract method, abstract class bunu yazmak (overrride etmek) zorunda.

    public void printInfo(){
        System.out.println("Sensor id: "+id+ "\nName: "+name+"\nMax Weight Capacity: "+maxWeightCapacity+"\nMax Speed: "+maxSpeed);
    }


//Get set methodlarını yaz

}

//Sensor (dönüş tipi), sensor(liste), sensor(elimdeki 1 örnek)
