$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(3)").addClass("current-tab");

//-------------------------------------------- Table ----------------------------------------------------------------

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
				"ordering": false,
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
			reversedData.unshift(data[key]);
		});
		
		return reversedData;
    }

//--------------------------------------------- Attendance Chart ----------------------------------------------------------

	var ctx = document.getElementById("attendance-chart").getContext('2d');
	var myChart = new Chart(ctx, {
		type: 'pie',
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
