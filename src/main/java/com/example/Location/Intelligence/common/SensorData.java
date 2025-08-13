package com.example.Location.Intelligence.common;

import lombok.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    private int sensorId;

    private String sensorType;
    private float value;
    private String unit;
    private String timeStamp=ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME).toString();
    private LocationInfo locationInfo;
}






