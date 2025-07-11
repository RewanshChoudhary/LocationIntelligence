// src/socketService.ts

import { Client, type Message, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const socketUrl = 'http://localhost:8080/ws'; // Spring Boot WebSocket endpoint
let stompClient: Client;

/**
 * Connect to WebSocket and listen to backend messages

 *
 */

export interface SensorData {
    sensorType:string,
    value :number ,
    unit :string ,
    timeStamp:string ,
    name:string,
    category:string ,
    locationDegoJson:string,
    distance :number

}


export const connectWebsocket = (onMessageReceived: (msg: any) => void) => {
    const socket = new SockJS(socketUrl);
    stompClient = Stomp.over(socket);

    // Disable debug logs
    stompClient.debug = () => {};

    stompClient.onConnect = () => {
        console.log("WebSocket connected");

        stompClient.subscribe("/topic/sensor-data", (message: Message) => {
            const data = JSON.parse(message.body) as SensorData[];
            console.log("WebSocket received:", data);
            onMessageReceived(data);
        });
    };

    stompClient.activate(); // connect
};

/**
 * Disconnect from WebSocket
 */
export const disconnectWebSocket = () => {
    if (stompClient && stompClient.active) {
        stompClient.deactivate().then(() => {
            console.log("WebSocket disconnected");
        });
    }
};

/**
 * Start polling /api/trigger every 4 seconds
 */
export const startTriggerPolling = (sensorType: string, locationName: string): number => {
    return window.setInterval(() => {
        fetch(`http://localhost:8080/api/trigger?sensorType=${sensorType}&locationName=${locationName}`)
            .then(res => {
                if (!res.ok) throw new Error("Trigger failed");
                console.log("Trigger hit");
            })
            .catch(err => console.error("Trigger error:", err));
    }, 4000);
};
