$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(4)").addClass("current-tab");
	
// ----------------------------- Calendar --------------------------------------
	
	// Calendar widget
	$('#calendar').datepicker({
		language: 'en',
		maxDate: new Date(), // Now can select only dates, which goes after today
		dateFormat: "dd/mm/yyyy"
	});

// --------------------------- Dropdown list -------------------------------------
	
	$.ajax({
		method: "GET",
		url: "https://deco3801.wisebaldone.com/api/child/",
		success: function(data) {
			// Order data by last name, then first name
			data.sort(predicateBy("first_name"));
			data.sort(predicateBy("last_name"));
			
			$(data).each(function (key) {
				var name = data[key]['first_name'] + " " + data[key]['last_name'];
				var id = data[key]['id'];
				
				$("#child-select-list").append($('<option>', {
					value: id,
					text: name
				}));
				
			});
			
		}
		
	});
	
	// https://stackoverflow.com/questions/11099610/generic-way-of-sorting-json-array-by-attribute
	function predicateBy(prop){
		return function(a,b){
			if( a[prop] > b[prop]){
				return 1;
			} else if( a[prop] < b[prop] ){
				return -1;
			}
			
			return 0;
			
		}
		
	}
	
// --------------------------------- Data Table -----------------------------------------
	
	// Create table
	$('#user-table').DataTable({
		"ordering": false, // false to disable sorting (or any other option),
		"bLengthChange": false,
		"bFilter": true,
		"bAutoHeight": false,
		"scrollY":260,
		"scrollX": false,
		"scrollColapse": false,
		"displayLength":-1,
		language: {
			searchPlaceholder: "Search records",
			search: "",
		},
		"order": [1, 'asc'],
		"data": {},
		"columns": [
			{"data": "time"},
			{"data": "status"},
		]
	});
					
	var table = $('#user-table').DataTable();
	$('.dataTables_length').addClass('bs-select');

// --------------------------------- Buttons --------------------------------------
// TODO: fix bug with calendar selection after generating a report	
	
	$("#generate-report-button").click(function(){
		var datePicker = $('#calendar').datepicker().data('datepicker');
		var selectedDates = datePicker.selectedDates;
		
		// Check if both dates have been selected
		if (selectedDates.length > 1) {
			// Get variables
			var childID = parseInt($("#child-select-list").val(), 10);
			var startDate = formatDate(datePicker.selectedDates[0]);
			var endDate = formatDate(datePicker.selectedDates[1]);
			
			var toSend = {
				"lower": startDate,
				"upper": endDate,
				"id": childID,
			}
			
			$.ajax({
				method: "POST",
				url: "https://deco3801.wisebaldone.com/api/entry/query",
				data: JSON.stringify(toSend),
				success: function(data) {
					var dateArray = getDateArray(datePicker.selectedDates[0], datePicker.selectedDates[1]);
					entries = formatEntries(data['entries'], dateArray);
					
					table.clear();
					table.rows.add(entries).draw();
				}
			
			});
		} else {
			$("#calendar").effect('shake');
		}
		
	});
	
	// Compares 2 dates and returns true if they are the same day, false otherwise
	function dateCompare(date1, date2) {
		if (date1.getDate() === date2.getDate() && date1.getMonth() === date2.getMonth() && date1.getFullYear() === date2.getFullYear()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	// Formats database data into a usable form
	function formatEntries(entries, dates) {
		var returnArray = new Array();
		
		// Loop over each date in the selected range
		$(dates).each(function (dateKey) {
			// Loop over every entry
			$(entries).each(function (entryKey) {
				// Create date variable based on the time in the entry
				var entryDate = new Date(0);
				entryDate.setUTCSeconds(entries[entryKey]['time']);
				
				// Add a new entry if the dates match and there isn't already an entry for this day
				if (dateCompare(dates[dateKey], entryDate) && returnArray.length == dateKey) {
					returnArray.push({
						"time": formatDate(dates[dateKey]),
						"status": "Yes",
					});
				}
				
			});
			
			// Add an entry for no attendance if no entry for attendace was added
			if (returnArray.length == dateKey) {
				returnArray.push({
					"time": formatDate(dates[dateKey]),
					"status": "No",
				});
			}
			
		});
		
		return returnArray;
		
	}
	
	// Generates an array of evry dates between start and end inclusive
	function getDateArray(start, end) {
		var arr = new Array();

		while (start <= end) {
			arr.push(new Date(start));
			start.setDate(start.getDate() + 1);
		}

		return arr;
	}
	
	// Formate date to a usable format
	function formatDate(date) {
		var day = date.getDate();
		var month = date.getMonth() + 1; // months start at 0
		var year = date.getFullYear();
		
		return day + "/" + month + "/" + year;
	}
	
});