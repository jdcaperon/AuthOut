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
				type: "GET",
				url: "https://deco3801.wisebaldone.com/api/login",
				dataType: 'json',
				username: username,
				password: password
			});

			request.success(function() {
				loginSuccess();
			});

			request.error(function(httpObj, textStatus) {       
					if(httpObj.status==200) {
						loginSuccess();
					}
					else {
						loginFail();
					}
			});
		}
	});
	
});
