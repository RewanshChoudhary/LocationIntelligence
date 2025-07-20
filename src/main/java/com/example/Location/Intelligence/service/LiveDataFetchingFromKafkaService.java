package com.example.Location.Intelligence.service;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.time.LocalDateTime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
//topic
public class LiveDataFetchingFromKafkaService{

    // stores current items to request
    private final Map<String , LocalDateTime> lastUpdated=new ConcurrentHashMap<>();
    @EventListener

    public void handleWebsocketSubscribeEvent(SessionSubscribeEvent session){
         // Provides destiantion endpoint for websocket connections to extreact the sensor_type and name

       String destination=session.getMessage().getHeaders().get("simpDestination").toString();

       if (destination!=null && destination.startsWith("/topic/sensor-data/")){
           String[] params=destination.split("/");
           String sensorType=params[3];
           String locationName=params[4];
           String key=sensorType+":"+locationName;

           lastUpdated.putIfAbsent(key,LocalDateTime.now().minusSeconds(10));





        }


    }
}