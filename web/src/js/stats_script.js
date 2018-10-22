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
						
						// Update graphs
						updateAttendanceGraph(statistics['attendance']);
						updateTimesChart(statistics['sign-ins'], statistics['sign-outs']);
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
	var attendanceChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
			datasets: [{
			    data: [0, 0, 0, 0, 0, 0, 0],
			    backgroundColor: [
			        '#DCA726',
			        '#03BABD',
			        '#BC72AF',
					'#3B3E3A',
					'#BC72AF',
					'#03BABD',
					'#DCA726'
				],
			    borderWidth: 1
			}]
		},
		options: {
			legend: {
				display: false
			},
			title: {
				display: true,
				text: 'Average Attendance'
			},
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
	var timesChart = new Chart(ctx, {
		type: 'line',
		data: {
			labels: ["<6AM", "6AM", "7AM", "8AM", "9AM", "10AM", "11AM", "12PM", "1PM", "2PM", "3PM", "4PM", "5PM", "6PM", "6PM+"],
			datasets: [{
			    data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
			    backgroundColor: '#03BABD',
				borderColor: '#03BABD',
			    borderWidth: 1,
				fill: false
			},
			{
			    data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
			    backgroundColor: '#BC72AF',
				borderColor: '#BC72AF',
			    borderWidth: 1,
				fill: false
			}]
		},
		options: {
			legend: {
				display: false
			},
			title: {
				display: true,
				text: 'Average Actions During the Day'
			},
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

	// Updates the times chart with the given times
	function updateTimesChart(signIns, signOuts) {
		// Remove old data
		timesChart.data.datasets.forEach((dataset) => {
			$(signIns).each(function(index, data) {
				dataset.data.pop();
			});
			
		});
		
		var index = 0;
		
		// Add new data
		timesChart.data.datasets.forEach((dataset) => {
			if (index > 0) {
				$(signOuts).each(function(index, data) {
					dataset.data.push(data);
				});
			} else {
				$(signIns).each(function(index, data) {
					dataset.data.push(data);
				});
			}
			
			index++;
		});
		
		// Update chart
		timesChart.update();
	}

	// Adds the given data to the attendance graph
	function updateAttendanceGraph(data) {
		// Remove old data
		attendanceChart.data.datasets.forEach((dataset) => {
			$(data).each(function() {
				dataset.data.pop();
			});
			
		});
		
		var index = 0;
		
		// Add new data
		attendanceChart.data.datasets.forEach((dataset) => {
			$(data).each(function() {
				dataset.data.push(data[index]);
				index++;
			});
			
		});
		
		// Update chart
		attendanceChart.update();
	}
	
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
