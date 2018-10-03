$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(4)").addClass("current-tab");
	
	// Calendar widget
	$(".calendar").jqxDateTimeInput({ animationType: 'slide', width: '80%', height: "40px", dropDownHorizontalAlignment: 'right', formatString: 'D' });
});