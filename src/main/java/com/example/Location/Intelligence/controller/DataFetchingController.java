package com.example.Location.Intelligence.controller;

import com.example.Location.Intelligence.service.LiveDataFetchingFromKafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/live-data")
public class DataFetchingController {
    @Autowired
    private final LiveDataFetchingFromKafkaService liveDataFetchingFromKafkaService;

    @GetMapping("/trigger")
    public ResponseEntity<Void> triggerTick(
            @RequestParam String sensorType,
            @RequestParam String locationName
    ) {

        System.out.println("Received sensorType: " + sensorType);
        System.out.println("Received locationName: " + locationName);
        liveDataFetchingFromKafkaService.getLiveSensorData(sensorType, locationName);
        return ResponseEntity.ok().build();


    }
}
