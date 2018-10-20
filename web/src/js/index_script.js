$(document).ready(function() {
	$("#login-button").click(function(event){
		event.preventDefault();
		var username = $("#username").val();
		var password = $("#password").val();
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
				window.location.href = "live.php"; 
			},
			fail: function() {
				console.log("Failed");
			}
		});
	});
	
});
