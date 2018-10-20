$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(4)").addClass("current-tab");
	
// ----------------------------- Calendar --------------------------------------
	
	// Calendar widget
	$('#calendar').datepicker({
		language: 'en',
		maxDate: new Date(), // Now can select only dates, which goes after today
		dateFormat: "dd/mm/yyyy",
		onSelect: function(formattedDate, date, inst) {
			var dates = formattedDate.split(',');
			if (dates.length > 1) {
				// send requests
			}
		}
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
	
});