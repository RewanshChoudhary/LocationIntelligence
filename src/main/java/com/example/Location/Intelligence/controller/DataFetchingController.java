package com.example.Location.Intelligence.controller;

import com.example.Location.Intelligence.service.LiveDataFetchingFromKafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/live-data")
public class DataFetchingController {

    private LiveDataFetchingFromKafkaService liveDataFetchingFromKafkaService;

    @GetMapping("/trigger")
    public ResponseEntity<Void> triggerTick(
            @RequestParam String sensor_type,
            @RequestParam String locationName
    ) {
        liveDataFetchingFromKafkaService.getLiveSensorData(sensor_type, locationName);
        return ResponseEntity.ok().build();


    }
}
