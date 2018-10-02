$(document).ready(function() {
	$("#nav-line2 > ul li:nth-of-type(4)").addClass("current-tab");
	
	// Create a jqxDateTimeInput
	$("#jqxWidget").jqxDateTimeInput({ animationType: 'fade', width: '150px', height: '25px', dropDownHorizontalAlignment: 'right'});
});