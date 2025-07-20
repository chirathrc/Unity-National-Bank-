function initializeCharts() {
    // New Accounts Chart
    const accountsCtx = document.getElementById('accountsChart').getContext('2d');
    const accountsChart = new Chart(accountsCtx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
            datasets: [{
                label: 'Savings Accounts',
                data: [65, 59, 80, 81, 56, 55, 40],
                backgroundColor: 'rgba(67, 97, 238, 0.1)',
                borderColor: 'rgba(67, 97, 238, 1)',
                borderWidth: 2,
                tension: 0.3,
                fill: true
            }, {
                label: 'Current Accounts',
                data: [28, 48, 40, 19, 86, 27, 90],
                backgroundColor: 'rgba(16, 185, 129, 0.1)',
                borderColor: 'rgba(16, 185, 129, 1)',
                borderWidth: 2,
                tension: 0.3,
                fill: true
            }]
        },
        options: getChartOptions('New Accounts Opened')
    });

    // FD Account Values Chart
    const fdCtx = document.getElementById('fdChart').getContext('2d');
    const fdChart = new Chart(fdCtx, {
        type: 'bar',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
            datasets: [{
                label: 'FD Amount ($)',
                data: [125000, 190000, 150000, 210000, 175000, 230000, 195000],
                backgroundColor: 'rgba(245, 158, 11, 0.7)',
                borderColor: 'rgba(245, 158, 11, 1)',
                borderWidth: 1
            }]
        },
        options: getChartOptions('FD Account Values', false)
    });

    // Update charts when range changes
    document.getElementById('accountChartRange').addEventListener('change', function() {
        updateChartData(accountsChart, this.value);
    });

    document.getElementById('fdChartRange').addEventListener('change', function() {
        updateChartData(fdChart, this.value);
    });
}

function getChartOptions(title, showLegend = true) {
    return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: showLegend,
                position: 'top',
                labels: {
                    boxWidth: 12,
                    padding: 20,
                    usePointStyle: true,
                    pointStyle: 'circle'
                }
            },
            title: {
                display: false,
                text: title
            },
            tooltip: {
                backgroundColor: 'rgba(0, 0, 0, 0.8)',
                titleFont: {
                    size: 14,
                    weight: 'bold'
                },
                bodyFont: {
                    size: 12
                },
                padding: 12,
                usePointStyle: true,
                callbacks: {
                    label: function(context) {
                        let label = context.dataset.label || '';
                        if (label) {
                            label += ': ';
                        }
                        if (context.parsed.y !== null) {
                            label += new Intl.NumberFormat('en-US', {
                                style: 'currency',
                                currency: 'USD'
                            }).format(context.parsed.y);
                        }
                        return label;
                    }
                }
            }
        },
        scales: {
            y: {
                beginAtZero: true,
                grid: {
                    drawBorder: false,
                    color: 'rgba(0, 0, 0, 0.05)'
                },
                ticks: {
                    callback: function(value) {
                        if (value >= 1000) {
                            return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                        } else {
                            return '$' + value;
                        }
                    }
                }
            },
            x: {
                grid: {
                    display: false,
                    drawBorder: false
                }
            }
        },
        interaction: {
            intersect: false,
            mode: 'index'
        },
        animation: {
            duration: 1000
        }
    };
}

function updateChartData(chart, range) {
    // In a real app, you would fetch new data based on the range
    // This is just a simulation
    const months = {
        '7': ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5', 'Week 6', 'Week 7'],
        '30': ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
        '90': ['Q1', 'Q2', 'Q3', 'Q4', 'Q5', 'Q6', 'Q7']
    };

    const newData = {
        '7': [[12, 19, 15, 22, 18, 25, 20], [8, 15, 12, 18, 14, 20, 16]],
        '30': [[65, 59, 80, 81, 56, 55, 40], [28, 48, 40, 19, 86, 27, 90]],
        '90': [[320, 450, 380, 490, 420, 510, 460], [180, 290, 240, 310, 270, 350, 300]]
    };

    const fdData = {
        '7': [45000, 62000, 55000, 70000, 60000, 75000, 65000],
        '30': [125000, 190000, 150000, 210000, 175000, 230000, 195000],
        '90': [450000, 620000, 550000, 700000, 600000, 750000, 650000]
    };

    chart.data.labels = months[range];

    if (chart.config.type === 'line') {
        chart.data.datasets[0].data = newData[range][0];
        chart.data.datasets[1].data = newData[range][1];
    } else {
        chart.data.datasets[0].data = fdData[range];
    }

    chart.update();
}