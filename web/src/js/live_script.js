$(document).ready(function() {
	
//--------------------------------------------- Attendance Chart ----------------------------------------------------------

	var ctx = document.getElementById("attendance-chart").getContext('2d');
	var myChart = new Chart(ctx, {
		type: 'pie',
		data: {
			labels: ["Signed In", "Signed Out"],
			datasets: [{
			    data: [1, 1],
			    backgroundColor: [
			        '#03BABD',
			        '#BC72AF'
				],
			    borderWidth: 4
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			legend: {
            display: false,

        }

		}
	});
	
//--------------------------------------------- Data Table ----------------------------------------------------------
	
	// List of actions for the day
	var entries;
	// Count of current actions;
	var currentActions = 0;
	// Live monitor table
	var table;

	$.ajax({
		method: "GET",
		url: "https://deco3801.wisebaldone.com/api/entry/",
		success: function(data) {
			// Update the attendance numbers
			updateAttendance(data["signed_in"], data["signed_out"]);
			entries = data['entries'];
			entries = reverse(entries);
			
			currentActions = entries.length;
			
			$('#dtBasicExample').DataTable({
				"ordering": false, // false to disable sorting (or any other option),
				"bLengthChange": false,
				"bFilter": true,
				"bAutoHeight": false,
				"scrollY":300,
				"displayLength":10,
				language: {
				searchPlaceholder: "Search records",
				search: "",
				},
				"order": [1, 'asc'],
				"data": entries,
				"columns": [
					{"data": "time"},
					{"data": "parent_name"},
					{"data": "child_name"},
					{"data": "status"}
				]
			});
			
			table = $('#dtBasicExample').DataTable();
			//table.order([1, 'desc']).draw(); // Order by time
			$('.dataTables_length').addClass('bs-select');
			
			// Set up periodic check
			window.setInterval(periodicUpdate, 2000); // TODO: change to use web sockets
		}
	});
	
	function periodicUpdate() {
		$.ajax({
			method: "GET",
			url: "https://deco3801.wisebaldone.com/api/entry/",
			success: function(data) {
				entries = data['entries'];
				
				// Check if there are new entries
				if (currentActions < entries.length) { // TODO: check if a new child has been registered
					// Update the attendance numbers
					updateAttendance(data["signed_in"], data["signed_out"]);
					
					// Format the entries
					entries = reverse(entries);
					
					// Add new entries to the table
					table.clear(); // TODO: change so that the table doesn't have to be cleared
					table.rows.add(entries).draw(); 
					
					currentActions = entries.length;
				}
				
				// Update values if a new child has been added
				if (parseInt($("#signed-out-number").text()) != data["signed_out"]) {
					updateAttendance(data["signed_in"], data["signed_out"]);
				}
				
			}
			
		});
	}
	
	function updateAttendance(signedIn, signedOut) {
		$('#signed-in-number').text(signedIn);
		$('#signed-out-number').text(signedOut);
		
		// Remove old data
		myChart.data.labels.pop();
		myChart.data.datasets.forEach((dataset) => {
			dataset.data.pop();
			dataset.data.pop();
		});
		
		// Add new data
		myChart.data.labels.push("Signed Out");
		myChart.data.datasets.forEach((dataset) => {
			dataset.data.push(signedIn);
			dataset.data.push(signedOut);
		});
		
		// Update chart
		myChart.update();
	}
	
	// Reverse and format the JSON array
	function reverse(data) {
		var reversedData = new Array();
		
		$(data).each(function (key) {
			// Format the time
			var date = new Date(0);
			date.setUTCSeconds(data[key]['time']);
			
			var hours = formatTime(date.getHours().toString());			
			var minutes = formatTime(date.getMinutes().toString());
			var seconds = formatTime(date.getSeconds().toString());
			
			var time = hours + ":" + minutes + ":" + seconds;
			data[key]['time'] = time;
			
			// Format the status
			if (data[key]['status'] == true) {
				data[key]['status'] = "Signed in";
			} else {
				data[key]['status'] = "Signed out";
			}
			
			reversedData.unshift(data[key]);
		});
		
		return reversedData;
    }
	
	// Adds a 0 to the time if needed
	function formatTime(time) {
		if (time.length == 1) {
			return "0" + time;
		} else {
			return time;
		}
	}


});
