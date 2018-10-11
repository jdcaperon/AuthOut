$(document).ready(function() {	
	$.ajax({
		method: "POST",
		url: "php/views.php",
		success: function(data) {
			var counts = JSON.parse(data);
			
			var ctx = document.getElementById("Weekly").getContext('2d');
			var myChart = new Chart(ctx, {
				type: 'bar',
				data: {
					labels: ["This Week", "Two Weeks Ago", "Three Weeks Ago", "Four Weeks Ago"],
					datasets: [{
						label: 'Views Per Week',
						data: [counts[0], counts[1], counts[2], counts[3]],
						backgroundColor: [
							'#3B3E3A',
							'#DCA726',
							'#03BABD',
							'#BC72AF'
						],
						borderWidth: 1
						}]
					},
				options: {
					scales: {
						yAxes: [{
							ticks: {
								beginAtZero:true
							}
						}]
					}
				}
			});
			
			var ctx = document.getElementById("frequency-chart").getContext('2d');
			var myChart = new Chart(ctx, {
			    type: 'pie',
			    data: {
			        labels: ["All Updates", "Monthly Newsletter", "Product Announcements"],
			        datasets: [{
			            label: 'Clicks Per Week',
			            data: [counts[4], counts[5], counts[6]],
			            backgroundColor: [
			                '#DCA726',
			                '#03BABD',
			                '#BC72AF'
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
			        scales: {
			            yAxes: [{
			                ticks: {
			                    beginAtZero:true
			                }
			            }]
			        }
			    }
			});
			
			var ctx = document.getElementById("early-chart").getContext('2d');
			var myChart = new Chart(ctx, {
			    type: 'pie',
			    data: {
			        labels: ["Early Bird Updates", "No Early Bird Updates"],
			        datasets: [{
			            label: 'Clicks Per Week',
			            data: [counts[7], counts[8]],
			            backgroundColor: [
			                '#DCA726',
			                '#03BABD',
			                '#BC72AF'
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
			        scales: {
			            yAxes: [{
			                ticks: {
			                    beginAtZero:true
			                }
			            }]
			        }
			    }
			});
			
		}
		
	});
	
});