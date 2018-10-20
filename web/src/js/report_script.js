$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(4)").addClass("current-tab");
	
	// Calendar widget
	$('#calendar').datepicker({
		language: 'en',
		maxDate: new Date() // Now can select only dates, which goes after today
	});
	
});