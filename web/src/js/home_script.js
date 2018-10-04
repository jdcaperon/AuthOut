$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(1)").addClass("current-tab");
	
	var ctx = document.getElementById("Weekly").getContext('2d');
	var myChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: ["White", "Orange", "Blue", "Pink"],
			datasets: [{
				label: 'Clicks Per Week',
			    data: [12, 19, 3, 5],
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
			responsive:true,
			maintainAspectRatio: false,
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero:true
					}
				}]
			}
		}
	});
			
	var ctx2 = document.getElementById("Daily").getContext('2d');
	var myChart2 = new Chart(ctx2, {
		type: 'bar',
		data: {
			labels: ["White", "Orange", "Blue", "Pink"],
			datasets: [{
				label: 'Clicks Per Week',
			    data: [12, 19, 3, 5],
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
			responsive:true,
			maintainAspectRatio: false,
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero:true
					}
				}]
			}
		}
	});
});