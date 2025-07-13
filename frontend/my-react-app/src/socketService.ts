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

export class NativeWebSocketService {
    private socket: WebSocket | null = null


    connect(onMessageReceived: (data: SensorData[]) => void) {
        this.socket = new WebSocket("ws://localhost:9090/ws");

        this.socket.onopen = () => {
            console.log("WebSocket connected");
        };

        this.socket.onmessage = (event) => {
            const data = JSON.parse(event.data) as SensorData[];
            console.log("Received:", data);
            onMessageReceived(data);
        };

        this.socket.onerror = (error) => {
            console.error("WebSocket error", error);
        };

        this.socket.onclose = () => {
            console.log("WebSocket closed");
        };


    }

    disconnect() {
        if (this.socket) {
            this.socket.close()
        }
    }
}