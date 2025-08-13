package com.example.Location.Intelligence.producer;

import com.example.Location.Intelligence.common.JsonValue;
import com.example.Location.Intelligence.common.SensorData;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataGenerator {


    public SensorData getPM25(){
        SensorData pm25 = new SensorData();
        JsonValue jsonValue = new JsonValue();
        int index= ThreadLocalRandom.current().nextInt(0, 20);
        float value = getPM25ByCategory(jsonValue.getSamples().get(index).getCategory());
        pm25.setValue(1);
        pm25.setSensorType("PM25");
        pm25.setUnit("µg/m³");
        pm25.setValue(value);
        pm25.setLocationInfo(jsonValue.getSamples().get(index));

        return pm25;

    }


    public SensorData getCO(){
        SensorData co=new SensorData();
        JsonValue jsonValue = new JsonValue();


        int index=ThreadLocalRandom.current().nextInt(0, 20);
        float value = getCOByCategory(jsonValue.getSamples().get(index).getCategory());
       co.setSensorType("CO");
       co.setValue(value);
        co.setLocationInfo(jsonValue.getSamples().get(index));
        co.setSensorId(2);
        return co;

    }


    public float getPM25ByCategory(String category) {
        switch (category.toLowerCase()) {
            case "hospital": return getRandom(10, 40);
            case "park": return getRandom(15, 50);
            case "school": return getRandom(30, 70);
            case "market": return getRandom(60, 120);
            case "government": return getRandom(40, 90);
            case "residential": return getRandom(40, 100);
            case "industrial area": return getRandom(150, 300);
            default: return getRandom(30, 80); // fallback
        }
    }
    public float getCOByCategory(String category) {
        switch (category.toLowerCase()) {
            case "hospital": return getRandom(0.2f, 1.0f);
            case "park": return getRandom(0.3f, 1.5f);
            case "school": return getRandom(1.0f, 3.0f);
            case "market": return getRandom(2.5f, 6.0f);
            case "government": return getRandom(1.0f, 4.0f);
            case "residential": return getRandom(1.0f, 5.0f);
            case "industrial area": return getRandom(5.0f, 10.0f);
            default: return getRandom(1.0f, 3.0f); // fallback
        }
    }

    private float getRandom(float min, float max) {
        return min + new Random().nextFloat() * (max - min);
    }


    private float getRandom(int min, int max) {
        return min + new Random().nextFloat() * (max - min);
    }


    }








