function loginSuccess() {
	window.location.href = "live.php"
}

function loginFail() {
	// TODO: notify user somehow
	console.log("FailedLogin, Jordan change me");
}

$(document).ready(function() {
	$("#login-button").click(function(event){
		event.preventDefault();
		var username = $("#username").val();
		var password = $("#password").val();

		if (username == "") {
			// TODO: notify user somehow
			console.log("Need a username");
		} else {
			var request = $.ajax
			({
				type: "POST",
				//url: "https://deco3801.wisebaldone.com/app/login.php",
				url: "login.php",
				data: {
					"username": username, 
					"password": password
				},
				dataType: "json",
				success: function(data, textStatus, xhr) {
					if (!$.isEmptyObject(data)) {
						loginSuccess();
					} else {
						loginFail();
					}
				}
				
			});
			
		}
		
	});
	
});
