$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(2)").addClass("current-tab");

	// Calendar widget
	$(".calendar").jqxDateTimeInput({ animationType: 'slide', width: '80%', height: "40px", dropDownHorizontalAlignment: 'right', formatString: 'D' });

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
});
