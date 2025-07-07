package com.example.Location.Intelligence.dto;

import com.example.Location.Intelligence.common.GeoJsonUtil;
import com.example.Location.Intelligence.model.SensorDataEntity;
import lombok.*;
import org.apache.kafka.common.metrics.Sensor;


@Getter
@Setter
@ToString

@NoArgsConstructor


public class QueryResponseDto {

    private String sensorType;
    private double value;
    private String unit;
    private String timeStamp;
    private String name;
    private String category;
    private String locationGeoJson;
    private double distance;
    public QueryResponseDto(SensorDataEntity sensorDataEntity) {
        this.sensorType = sensorDataEntity.getSensorType();
        this.value = sensorDataEntity.getValue();
        this.unit = sensorDataEntity.getUnit();
        this.timeStamp = sensorDataEntity.getTimeStamp();
        this.category=sensorDataEntity.getLocationInfo().getCategory();
        this.name=sensorDataEntity.getLocationInfo().getName();
        this.locationGeoJson= GeoJsonUtil.toGeoJson(sensorDataEntity.getLocation());


    }

    public QueryResponseDto(String sensorType, double value, String unit, String timeStamp, String name, String category, String locationGeoJson, double distance) {
        this.sensorType = sensorType;
        this.value = value;
        this.unit = unit;
        this.timeStamp = timeStamp;
        this.name = name;
        this.category = category;
        this.locationGeoJson = locationGeoJson;
        this.distance = distance;
    }


}
