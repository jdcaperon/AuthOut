function loginSuccess() {
	console.log("SuccessLogin, Jordan change me");
	window.location.href = "live.php"
}

function loginFail() {
	console.log("FailedLogin, Jordan change me");
}

$(document).ready(function() {
	$("#login-button").click(function(event){
		event.preventDefault();
		var username = $("#username").val();
		var password = $("#password").val();

		if (username == "") {
			// notify user somehow
			console.log("Need a username");
		} else {
			var request = $.ajax
			({
				type: "POST",
				url: "https://deco3801.wisebaldone.com/app/login.php",
				data: {
					"username": username, 
					"password": password
				},
				success: function(data, textStatus, xhr) {
					if (xhr.status == 200) {
						loginSuccess();
					} else {
						loginFail();
					}
				},
				complete: function(xhr, textStatus) {
					if (xhr.status == 200) {
						loginSuccess();
					} else {
						loginFail();
					}
				}
			});
		}
	});
	
});
