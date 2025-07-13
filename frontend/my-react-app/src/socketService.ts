import {Client, type Message} from "@stomp/stompjs";
import * as SockJS from "sockjs-client";

const socketUrl = 'http://localhost:8080/ws';

export interface SensorData {
    sensorType: string;
    value: number;
    unit: string;
    timeStamp: string;
    name: string;
    category: string;
    locationDegoJson: string;
    distance: number;
}

export class SocketService{
    private stompClient:Client| null=null

    connect(onMessageReceived:(data :SensorData[])=> void)
    {
        const socket =new SockJS(socketUrl)
      this.stompClient=new Client({
          webSocketFactory :()=>socket,

          debug:()=>{},
          reconnectDelay: 5000,
          onConnect:()=>{
              console.log("Connected to the websocket connection")
              this.stompClient?.subscribe("/topic/sensor-data",(message:Message)=>{
                  const data=JSON.parse(message.body) as SensorData[]
                  console.log("Got the data the ",data )

                  onMessageReceived(data)






              })

          },
          onStompError:()=>{
              console.error("Websocket connection error ")


          }




      })
        this.stompClient.activate()
    }
    disconnect(){
        this.stompClient?.deactivate()

        console.log("Connection was disconnected")

    }
}