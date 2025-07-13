import {type SensorData, SocketService} from "./socketService.ts";
import {useEffect, useState} from "react";

const socketService = new SocketService()


function App() {

    const [sensorDataList, setSensorDataList] = useState<SensorData[]>([]);

    const triggerBackend = async () => {
        try {
            const trigger = await fetch("http://localhost:8080/api/live-data/trigger",
                {method : "GET"})

            if (!trigger.ok) {
                throw new Error("The triggering didnt happen")
            }
        } catch (error) {
            console.error("An error occured", error)

        }
    }

    useEffect(() => {
        socketService.connect((data) => {
            setSensorDataList((prev) => [...data, ...prev]);
        });

        const startPolling=setInterval(triggerBackend,5000)


        return ()=>{
            socketService.disconnect();
            clearInterval(startPolling)

        }


    })



}