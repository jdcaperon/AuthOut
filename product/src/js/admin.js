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
							'#F2F3F1',
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