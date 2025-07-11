import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

const ctx = document.getElementById('myChart') as HTMLCanvasElement;

const myChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: ['January', 'February', 'March'],
    datasets: [{
      label: 'Sensor Values',
      data: [12, 19, 3],
      borderColor: 'rgb(75, 173, 192)',
      borderWidth: 2,
      fill: false
    }]
  },
  options: {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true
      }
    }
  }
});
