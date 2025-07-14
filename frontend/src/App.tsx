import { useEffect, useState } from "react";
import { NativeWebSocketService, type SensorData } from "./socketService";

function App() {
    const socketService = new NativeWebSocketService();
    const [sensorDataList, setSensorDataList] = useState<SensorData[]>([]);

    const triggerBackend = async () => {
        try {
            const sensorType = "PM2.5";
            const locationName = "Delhi";

            const trigger = await fetch(
                `http://localhost:9090/api/live-data/trigger?sensorType=${sensorType}&locationName=${locationName}`,

                { method: "GET" }
            );

            if (!trigger.ok) {
                throw new Error("Triggering failed");
            }
            console.log("Trigger successful");
        } catch (error) {
            console.error("Trigger error:", error);
        }
    };

    useEffect(() => {
        const initializeSocket = async () => {
            try {
                await socketService.connect((data) => {
                    setSensorDataList((prev) => [...data, ...prev]);
                });
            } catch (error) {
                console.error("WebSocket connect failed:", error);
            }
        };

        initializeSocket();
        const startPolling = setInterval(triggerBackend, 5000);

        return () => {
            socketService.disconnect();
            clearInterval(startPolling);
        };
    }, []);

    return (
        <div className="App">
            <h1>Live Sensor Data</h1>
            {sensorDataList.length === 0 ? (
                <p>No data received yet...</p>
            ) : (
                <table border={1} style={{ margin: 'auto', marginTop: '20px' }}>
                    <thead>
                    <tr>
                        <th>Type</th>
                        <th>Value</th>
                        <th>Unit</th>
                        <th>Time</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Distance</th>
                    </tr>
                    </thead>
                    <tbody>
                    {sensorDataList.map((data, index) => (
                        <tr key={index}>
                            <td>{data.sensorType}</td>
                            <td>{data.value}</td>
                            <td>{data.unit}</td>
                            <td>{data.timeStamp}</td>
                            <td>{data.name}</td>
                            <td>{data.category}</td>
                            <td>{data.distance.toFixed(2)}m</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default App;
