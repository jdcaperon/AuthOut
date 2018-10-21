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

	// --------------------------------- Buttons --------------------------------------
	$("#generate-report-button").click(function(){
		var datePicker = $('#calendar').datepicker().data('datepicker');
		var selectedDates = datePicker.selectedDates;
		
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
				method: "GET",
				url: "https://deco3801.wisebaldone.com/api/entry/query",
				data: JSON.stringify(toSend),
				success: function(data) {
					console.log(data);					
				}
			
			});
		} else {
			// TODO: prompt to select more dates
		}
		
	});
	
	// Formate date to a usable format
	function formatDate(date) {
		var day = date.getDate();
		var month = date.getMonth() + 1; // months start at 0
		var year = date.getFullYear();
		
		return day + "/" + month + "/" + year;
	}
	
});