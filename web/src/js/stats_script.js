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
				var startDate = dates[0];
				var endDate = dates[1];
				
				var toSend = {
					"lower": startDate,
					"upper": endDate
				}
				
				$.ajax({
					method: "POST",
					url: "https://deco3801.wisebaldone.com/api/entry/stats",
					data: JSON.stringify(toSend),
					success: function(data) {						
						// Object to store the data
						var statistics = formatData(data);
						
						console.log(statistics);
						
						// TODO: make graphs
					}
				
				});
				
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

// ------------------------------------ Functions ------------------------------------------------------

	// Gets the index in the time arrays from the given time
	function getTimeIndex(time) {
		// Create date objet
		var entryDate = new Date(0);
		entryDate.setUTCSeconds(time);
		
		var hours = entryDate.getHours();
			
		// Calculate index
		if (hours < 6) {
			return 0;
		} else if (hours > 18) {
			return 14;
		} else {
			return hours - 5;			
		}
		
	}
	
	// Averages the data for unique day in a range
	function averageData(data) {
		var days = data['day-count'];
		var totalDays = 0;
		
		// Average the attendance
		$(days).each(function (dayKey) {
			data['attendance'][dayKey] /= days[dayKey];
			totalDays += days[dayKey]; // count total days in the range
		});
		
		// Average the times
		$(data['sign-ins']).each(function (key) {
			data['sign-ins'][key] /= totalDays;
			data['sign-outs'][key] /= totalDays;
		});
		
		return data;
	}

	// Formats returned data to a usable form
	function formatData(data) {
		// Object to return
		var returnObject = {
			"day-count": [0, 0, 0, 0, 0, 0, 0], // 7 days of the week, Sunday being the first number
			"sign-ins": [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], // Array for times during the day: <6AM, 6AM, 7AM, ..., 5PM, 6PM, >6PM
			"sign-outs": [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], // Array for times during the day: <6AM, 6AM, 7AM, ..., 5PM, 6PM, >6PM
			"attendance": [0, 0, 0, 0, 0, 0, 0] // 7 days of the week, Sunday being the first number
		};
		// Array of days with data from the range
		var days = data['days'];
		
		// Loop over every day
		$(days).each(function (dayKey) {
			var entries = days[dayKey]['entries'];
			
			// Create date object
			var dateComponents = days[dayKey]['date'].split("/");			
			var date = new Date(dateComponents[2], parseInt(dateComponents[1]) - 1, dateComponents[0]);
			
			// Sum attendance for each day
			returnObject['attendance'][date.getDay()] += days[dayKey]['signins'];
			
			// Add sign in/out times to arrays
			$(entries).each(function (entryKey) {
				// Get index for time array
				var index = getTimeIndex(entries[entryKey]['time']);
					
				// Check if it was a sign in or sign out
				if (entries[entryKey]['status'] == true) {
					returnObject['sign-ins'][index]++;
				} else {
					returnObject['sign-outs'][index]++;
				}
				
			});
			
			// Increase count of each day
			returnObject['day-count'][date.getDay()]++;
			
		});
		
		return averageData(returnObject);
	}
	
});
