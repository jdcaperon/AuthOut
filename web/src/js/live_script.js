$(document).ready(function() {
//--------------------------------------------- Data Table ----------------------------------------------------------

	$('#dtBasicExample').DataTable({
		"ordering": false, // false to disable sorting (or any other option),
		"bLengthChange": false,
		"bFilter": true,
    	"bAutoHeight": false,
		"scrollY":200,
		"displayLength":5,
		language: {
        searchPlaceholder: "Search records",
        search: "",
      }
	});
	$('.dataTables_length').addClass('bs-select');
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
			responsive:true,
			maintainAspectRatio: false,
			legend: {
            display: false,

        }

		}
	});
});
