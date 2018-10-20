$(document).ready(function() {
//--------------------------------------------- Data Table ----------------------------------------------------------

	var entries;

	$.ajax({
		method: "GET",
		url: "https://deco3801.wisebaldone.com/api/entry/",
		success: function(data) {
			console.log(data);
			entries = data['entries'];
			console.log(entries);
			entries = reverse(entries);
			console.log(entries);
			
			$('#dtBasicExample').DataTable({
				"ordering": false, // false to disable sorting (or any other option),
				"bLengthChange": false,
				"bFilter": true,
				"bAutoHeight": false,
				"scrollY":260,
				"displayLength":10,
				language: {
				searchPlaceholder: "Search records",
				search: "",
				},
				"data": entries,
				"columns": [
					{"data": "time"},
					{"data": "parent_name"},
					{"data": "child_name"},
					{"data": "status"}
				]
			});
			
			var table = $('#dtBasicExample').DataTable();
	
			table.order([1, 'desc']).draw();
	
			$('.dataTables_length').addClass('bs-select');
		}
	});
	
	function reverse(data) {
		var reversedData = new Array();
		
		$(data).each(function (key) {
			var date = new Date(0);
			date.setUTCSeconds(data[key]['time']);
			var time = date.getHours() + ":" + date.getMinutes() + "." + date.getSeconds();
			data[key]['time'] = time;
			if (data[key]['status'] == true) {
				data[key]['status'] = "Signed in";
			} else {
				data[key]['status'] = "Signed out";
			}
			
			reversedData.unshift(data[key]);
		});
		
		return reversedData;
    }

//--------------------------------------------- Attendance Chart ----------------------------------------------------------

	var ctx = document.getElementById("attendance-chart").getContext('2d');
	var myChart = new Chart(ctx, {
		type: 'pie',
		data: {
			labels: ["Signed In", "Signed Out"],
			datasets: [{
			    data: [12, 19],
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
});
