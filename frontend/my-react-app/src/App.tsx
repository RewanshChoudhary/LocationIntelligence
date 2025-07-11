import {useEffect, useState} from "react";
import {connectWebsocket, disconnectWebSocket, type SensorData, startTriggerPolling} from "./socketService.ts";

function App()
{
    const [sensorData,setSensorData]=useState<SensorData[]>([])

    useEffect(() => {
        connectWebsocket((newData: SensorData[])=> {
            setSensorData(prevState => [...prevState, ...newData])
        });


   const startPolling =startTriggerPolling("PM25","Delhi")

        return ()=>{
       disconnectWebSocket()
            clearInterval(startPolling)

        }








    }, []);

    return  (
        <div style={{fontFamily:"monospace",fontSize:"1rem"}}>
            <h1> Live Sensor Data </h1>
            {
                sensorData.length==0?(
                    <p> Data not available yet </p>)
                    :(
                        <table border={1} cellPadding={8}>
                            <thead>
                            <tr>
                                <th>Sensor</th>
                                <th>Value</th>
                                <th>Unit</th>
                                <th>Time</th>
                                <th>Name</th>
                                <th>Category</th>
                                <th>Distance</th>
                                <th>Location (GeoJSON)</th>


                            </tr>
                            </thead>
                            <tbody>
                            {sensorData.map((data, i) => (
                                <tr key={i}>
                                    <td>{data.sensorType}</td>
                                    <td>{data.value}</td>
                                    <td>{data.unit}</td>
                                    <td>{data.timeStamp}</td>
                                    <td>{data.name}</td>
                                    <td>{data.category}</td>
                                    <td>{data.distance.toFixed(2)} m</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                )
            }


        </div>
    )

}

export default App;
