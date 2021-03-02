
var color = Chart.helpers.color;

// Start of the Doughnut's chart development

function createCharts() {
    doughnutDisplay();
    barChartDisplay();
    radarDisplay();

}

function doughnutDisplay() {
    new Chart(document.getElementById("Doughnut-chart").getContext('2d'), {
        data: {
            labels: ["Mental Health", "Physical health", "Savings", "Good night", "Home", "Focus and effort"],
            datasets: [{
                backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
                data: [10, 37, 20, 78, 43, 30],
                bezierCurve: false
            }]
        },

        type: 'doughnut',

        options: {
            bezierCurve: false,
            responsive: true,
            title: {
                display: true,
                text: 'Number of likes given by the user for categories'
            }
        }

    });
}

// End of the Doughnut's chart development


// Start of the Bar's chart development

function barChartDisplay(){
    new Chart(document.getElementById("bar-chart"), {
        type: 'bar',
        data: {
			labels: ["Mental Health", "Physical health", "Savings", "Good night", "Home", "Focus and effort"],
            datasets: [
                {
                    backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
                    data: [2478,5267,734,784,433, 500]
                }
            ]
        },
        options: {
            legend: { display: false },
            title: {
                display: true,
                text: "Tree height for each category"
            },
            responsive: true
        }
    });
}

// Ending of the Bar's chart development



// Start of the Radar's chart development

function radarDisplay(){
    new Chart(document.getElementById('radarChart'), {
        type: 'radar',
        data: {
            labels: ["Mental Health", "Physical health", "Savings", "Good night", "Home", "Focus and effort"],
            datasets: [{
                backgroundColor: color('rgba(18, 162, 148, 0.2)').rgbString(),
                borderColor: "rgba(18, 162, 148, 0.8)",
                pointBackgroundColor: "rgba(18, 162, 148, 1)",
                bezierCurve : false,
                data: [
                    10,20,30,40,50, 60
                ],
            }]
        },
        options: {
            bezierCurve: false,
            responsive: true,
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Number of tasks done by category'
            },
            scale: {
                ticks: {
                    beginAtZero: true
                }
            }
        }
    });
}

// Ending of the Radar's chart development

