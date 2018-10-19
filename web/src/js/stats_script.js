$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(2)").addClass("current-tab");
	
//--------------------------------------------- Calendar ----------------------------------------------------------
	
	$("#calendar-widget").jqxCalendar({width: "100%", height: "100%", selectionMode: 'range', theme: 'custom', max: new Date()});
			
	var count = 0;
			
	// Get date range on change
	$("#calendar-widget").on('change', function (event) {
        var selection = event.args.range;
		var fromDate = selection.from.toLocaleDateString();
		var toDate = selection.to.toLocaleDateString();
				
		// Ignore the first two changes that happen when the calendar is made
		if (count > 1) {
			console.log(fromDate);
			console.log(toDate);
		}
				
		count++;
	});
            
	// Set starting range to be the last week
	var today = new Date();
	var past = new Date();
	past.setDate(past.getDate() - 6);
    $("#calendar-widget").jqxCalendar('setRange', past, today);
	
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
