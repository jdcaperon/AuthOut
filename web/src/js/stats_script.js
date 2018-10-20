$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(2)").addClass("current-tab");


//--------------------------------------------- Calendar ----------------------------------------------------------

	$('#calendar').datepicker({
		language: 'en',
		maxDate: new Date(), // Now can select only dates, which goes after today
		dateFormat: "dd/mm/yyyy",
		onSelect: function(formattedDate, date, inst) {
			var dates = formattedDate.split(',');
			if (dates.length > 1) {
				// send requests
			}
		}
	});

	var datePicker = $('#calendar').datepicker().data('datepicker');
	
	var today = new Date();
 	var past = new Date();
	past.setDate(past.getDate() - 6);
	
	datePicker.selectDate([past, today]);

//--------------------------------------------- Attendance Graph ----------------------------------------------------------

	var ctx = document.getElementById("attendance-graph").getContext('2d');
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

//--------------------------------------------- Times Graph ----------------------------------------------------------

	var ctx = document.getElementById("times-graph").getContext('2d');
	var myChart = new Chart(ctx, {
		type: 'line',
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
