$(document).ready(function() {
	$("#login-button").click(function(event){
		event.preventDefault();
		var username = $("#username").val();
		var password = $("#password").val();

		if (username == "") {
			// notify user somehow
			console.log("Need a username");
		} else {
			$.ajax
			({
				type: "GET",
				url: "https://deco3801.wisebaldone.com/api/login",
				dataType: 'json',
				async: false,
				username: username,
				password: password,
				data: '{ "comment" }',
				success: function (){
					console.log("Success");
					window.location.href = "live.php"; 
				},
				error: function() {
					console.log("Failed");
				}
			});
		}
	});
	
});
