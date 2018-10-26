$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(4)").addClass("current-tab");
	
	// Array of selected date range
	var dates = getDateArray(new Date(), new Date());
	// Link child names with IDs
	var childNames = new Object();
	
// ----------------------------- Calendar --------------------------------------

	$('input[name="daterange"]').daterangepicker({
		opens: 'left',
		maxDate: new Date(),
		locale: {
			format: 'DD/MM/YYYY'
		}
	// Save the dates when they are updated
	}, function(start, end, label) {
		dates = getDateArray(new Date(start['_d']), new Date(end['_d']));
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
				var name = " " + data[key]['first_name'] + " " + data[key]['last_name'];
				var id = data[key]['id'];
				
				// Add to dictionary
				childNames[id] = name;
				
				// Add to select list
				$("#child-select-list").append($('<option>', {
					value: id,
					text: name
				}));
				
			});
			
			// Change to multiple select plugin
			$('#child-select-list').change(function() {
				selectedChildren = $(this).val();
			}).multipleSelect({
				width: '100%',
				placeholder: "Please select a child",
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
	// Set date ordering
	$.fn.dataTable.moment('DD/MM/YYYY');
	
	// Create table
	$('#user-table').DataTable({
		"ordering": true, // false to disable sorting (or any other option),
		"bLengthChange": false,
		"bFilter": true,
		"bAutoHeight": false,
		"scrollY":260,
		"scrollX": false,
		"scrollColapse": false,
		"displayLength": 10,
		dom: 'Bfrtip',
		buttons: [
            'excelHtml5',
            'csvHtml5',
            {
                extend: 'pdfHtml5',
                messageTop: 'Attendance report generated with AuthOut.'
            }
		],
		language: {
			searchPlaceholder: "Search records",
			search: "",
		},
		"order": [1, 'asc'],
		"data": {},
		"columns": [
			{"data": "name"},
			{"data": "time"},
			{"data": "status"},
		]
	});
					
	var table = $('#user-table').DataTable();
	$('.dataTables_length').addClass('bs-select');

// --------------------------------- Buttons --------------------------------------
	
	$("#user-table_wrapper .dt-buttons").css({
		"font-size" : "0"
	});
	
	$(".buttons-excel, .buttons-csv, .buttons-pdf").addClass("rounded-button blue-button");
	$(".buttons-excel, .buttons-csv, .buttons-pdf").css({
		"max-width" : "80px",
		"display" : "inline-block",
		"font-size" : "14px"
	});
	
	$(".buttons-excel").css({
		"border-radius" : "200px 0 0 200px",
	});
	
	$(".buttons-csv").css({
		"border-radius" : "0px",
		"border-left" : "1px solid white",
		"border-right" : "1px solid white",
	});

	$(".buttons-pdf").css({
		"border-radius" : " 0 200px 200px 0",
	});
	
	$("#generate-report-button").click(function(){		
		// Get the selected dates
		var IDs = $("#child-select-list").multipleSelect('getSelects');
		// Entries for the table
		var entries = [];
		
		// Check children have been selected
		if ($("#child-form")[0].checkValidity()) {
			
			// Show overlay
			$(".content").LoadingOverlay("show", {
				image: "",
				text: "Loading..."
			});
			
			// Get variables
			$(IDs).each(function(key, ID) {
				var childID = parseInt(ID, 10);
				var startDate = formatDate(dates[0]);
				var endDate = formatDate(dates[dates.length - 1]);
				
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
						var returned = formatEntries(data['entries'], dates, ID);
						
						$(returned).each(function(key, value) {
							entries.push(value);
						});
						
					}
				
				});
				
			});
			
			// Wait for all ajax requests to finish
			$(document).ajaxStop(function () {				
				table.clear();
				table.rows.add(entries).draw();
						
				// Hide overlay
				$(".content").LoadingOverlay("hide", true);				
			});
			
		} else {
			$("#child-form").effect("shake");
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
	function formatEntries(entries, dates, ID) {
		var returnArray = new Array();
		// Get the name of the child
		var name = childNames[ID];
		
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
						"name": name
					});
				}
				
			});
			
			// Add an entry for no attendance if no entry for attendace was added
			if (returnArray.length == dateKey) {
				returnArray.push({
					"time": formatDate(dates[dateKey]),
					"status": "No",
					"name": name
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
		
		// Add '0' if needed
		if (parseInt(day / 10) == 0) {
			day = "0" + day;
		}
		
		var month = date.getMonth() + 1; // months start at 0
		
		// Add '0' if needed
		if (parseInt(month / 10) == 0) {
			month = "0" + month;
		}
		
		var year = date.getFullYear();
		
		return day + "/" + month + "/" + year;
	}
	
});