var color = Chart.helpers.color;

// Start of the Bar chart development

(function () {
    $.getJSON('/generateBarChart/')
        .done(function (ans) {
            console.log(ans)
            let labels = []
            let color = []
            let data = []
            ans.forEach(el => {labels.push(el.name); color.push(el.color); data.push(el.data)})
            new Chart(document.getElementById("bar-chart"), {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [
                        {
                            backgroundColor: color,
                            data: data
                        }
                    ]
                },
                options: {
                    legend: {display: false},
                    title: {
                        display: true,
                        text: "Tree height for each category"
                    },
                    responsive: true
                }
            })
        })
})();

(function () {
    $.getJSON('/generateDoughnutChart/')
        .done(function (ans) {
            console.log(ans)
            let labels = []
            let color = []
            let data = []
            ans.forEach(el => {labels.push(el.name); color.push(el.color); data.push(el.data)})
            new Chart(document.getElementById("Doughnut-chart").getContext('2d'), {
                data: {
                    labels: labels,
                    datasets: [{
                        backgroundColor: color,
                        data: data,
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
        })
})();

// Start of the Radar's chart development

(function () {
    $.getJSON('/generateRadarChart/')
        .done(function (ans) {
            console.log(ans)
            let labels = []
            let data = []
            ans.forEach(el => {labels.push(el.name); data.push(el.data)})
            new Chart(document.getElementById('radarChart'), {
                type: 'radar',
                data: {
                    labels: labels,
                    datasets: [{
                        backgroundColor: color('rgba(18, 162, 148, 0.2)').rgbString(),
                        borderColor: "rgba(18, 162, 148, 0.8)",
                        pointBackgroundColor: "rgba(18, 162, 148, 1)",
                        bezierCurve: false,
                        data: data,
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
        })
})();

// Ending of the Radar's chart development