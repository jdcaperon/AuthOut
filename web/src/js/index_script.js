$(document).ready(function() {
	$("#login-button").click(function(event){
		event.preventDefault();
		
		window.location.href = "php/home.php";
	});
});