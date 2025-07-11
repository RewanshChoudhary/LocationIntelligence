package com.example.Location.Intelligence.service;

import com.example.Location.Intelligence.dto.QueryResponseDto;

import com.example.Location.Intelligence.repository.SensorDataEntityRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LiveDataFetchingFromKafkaService {
    private final SensorDataEntityRepository sensorDataEntityRepository;
    private static LocalDateTime lastUpdated;
    private final SimpMessagingTemplate messagingTemplate;

      //Gives a tick every 10 sec to tranfer data to the subscribed endpoint



    public void  getLiveSensorData(String sensor_type, String locationName) {
        if (lastUpdated == null) lastUpdated = LocalDateTime.now().minusSeconds(10);
        List<QueryResponseDto> newData = sensorDataEntityRepository.getLiveSensorData(sensor_type, locationName, lastUpdated);
        lastUpdated = LocalDateTime.now();

        if (!newData.isEmpty()) {
            messagingTemplate.convertAndSend("/topic/sensor-updates", newData);
        }
    }


}







