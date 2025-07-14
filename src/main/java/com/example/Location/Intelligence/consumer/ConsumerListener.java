package com.example.Location.Intelligence.consumer;


import com.example.Location.Intelligence.common.SensorData;
import com.example.Location.Intelligence.model.SensorDataEntity;
import com.example.Location.Intelligence.repository.SensorDataEntityRepository;


import lombok.RequiredArgsConstructor;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ConsumerListener {
    private final SensorDataEntityRepository sensorDataEntityRepository;



    @KafkaListener(topics = "${spring.kafka.topics.pm25}", groupId = "${spring.kafka.consumers.groupid}")
    public void  getData(SensorData sensorData) {
        SensorDataEntity sensorDataEntity =new SensorDataEntity(sensorData);
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point =geometryFactory.createPoint(new Coordinate(sensorData.getLocationInfo().getLongitude(),sensorData.getLocationInfo().getLatitude()));
        sensorDataEntity.setLocation(point);
        sensorDataEntityRepository.save(sensorDataEntity);












    }
}