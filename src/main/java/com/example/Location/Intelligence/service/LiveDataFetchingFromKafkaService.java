package com.example.Location.Intelligence.service;


import com.example.Location.Intelligence.dto.QueryResponseDto;
import com.example.Location.Intelligence.repository.SensorDataEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.time.LocalDateTime;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
//topic
@RequiredArgsConstructor
public class LiveDataFetchingFromKafkaService{
    private final Map<String, Set<String>> topicSubscribers=new ConcurrentHashMap<>();

    private final SensorDataEntityRepository sensorDataEntityRepository;
    private SimpMessagingTemplate simpMessagingTemplate;


    // stores current items to request
    private final Map<String , LocalDateTime> lastUpdated=new ConcurrentHashMap<>();
    @EventListener

    public void handleWebsocketSubscribeEvent(SessionSubscribeEvent session){
        String sessionId=session.getMessage().getHeaders().get("simpSessionId").toString();

         // Provides destiantion endpoint for websocket connections to extreact the sensor_type and name
         String destination=session.getMessage().getHeaders().get("simpDestination").toString();

       if (destination!=null && destination.startsWith("/topic/sensor-data/")){
           topicSubscribers.computeIfAbsent(destination, k -> ConcurrentHashMap.newKeySet()).add(sessionId);


           String[] params=destination.split("/");
           String sensorType=params[3];
           String locationName=params[4];
           String key=sensorType+":"+locationName;
           // checks if it doesnt already exists

           lastUpdated.putIfAbsent(key,LocalDateTime.now().minusSeconds(10));





        }



    }
    @EventListener
    public void handleWebsocketUnsubscribeEvent(SessionUnsubscribeEvent session){
        String sessionId=session.getMessage().getHeaders().get("simpSessionId").toString();

        topicSubscribers.forEach((destination,sessions)->{sessions.remove(sessionId);});



    }
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event){
        String sessionId=event.getMessage().getHeaders().get("simpSessionId").toString();

        topicSubscribers.forEach((destination,sessions)->{
            sessions.remove(sessionId);

        });
        clearEmptyTopics();




    }
    @Scheduled(fixedRate=10000)
    public void pollLiveData(){
        // Search for the client requested sensor and sends out the data
        for (Map.Entry<String,LocalDateTime> entry:lastUpdated.entrySet()){
            String key=entry.getKey();
            LocalDateTime lastUpdatedTime=entry.getValue();
            String [] params=key.split(":");
            String sensorType=params[0];
            String locationName=params[1];

            List<QueryResponseDto> response=sensorDataEntityRepository.getLiveSensorData(sensorType,locationName,lastUpdatedTime);
            // We need to assign new time as the query refereshes the data according to the new time



            if (response!=null){
                simpMessagingTemplate.convertAndSend("/topic/sensor-data",response.toString());
                lastUpdated.put(key,LocalDateTime.now());


            }

        }

    }

    public void clearEmptyTopics(){
        topicSubscribers.entrySet().removeIf(entry-> {
                boolean empty=entry.getValue().isEmpty();
                if (empty){
                   String [] parts=entry.getKey().split("/");
                   if(parts.length>=5){
                       String key=parts[3]+":"+parts[4];
                       lastUpdated.remove(key);


                   }


                }
                return empty;


        });


    }
}