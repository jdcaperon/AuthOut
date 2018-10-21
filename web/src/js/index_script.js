$(document).ready(function() {
	var attempted = false;
	$('[data-toggle="popover"]').popover("enable");

	$("#login-button").click(function(event){

		if($("#login-form")[0].checkValidity() ){
			$('[data-toggle="popover"]').popover("disable");
			event.preventDefault();



			var username = $("#username").val();
			var password = $("#password").val();

			var request = $.ajax
			({
				type: "POST",
				url: "login.php",
				data: {
					"username": username,
					"password": password
				},
				dataType: "json",
				success: function(data, textStatus, xhr) {
					if (!$.isEmptyObject(data)) {
						window.location.href = "live.php"
					}
				}

			});
		}
	});

});
