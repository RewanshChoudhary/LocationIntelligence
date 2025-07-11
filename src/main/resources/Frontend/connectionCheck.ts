import { Client, Message, ReconnectionTimeMode, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client'; // Make sure this is imported

const socketUrl = 'http://localhost:8080/ws';

let stompClient:Client;


export const connectWebsocket=(onMessageReceived:(msg:any)=> void){
    const socket=new SockJS(socketUrl)
    stompClient=Stomp.over(socket)

    stompClient.onConnect=(()=>{
        stompClient.subscribe("/topic/sensor-data",(message:Message)=>{

            const data=JSON.parse(message.body);
            onMessageReceived(data)
            


        })
    })
}