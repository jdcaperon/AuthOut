$(document).ready(function() {
	$("#login-button").click(function(event){
		// Clear validity
		var emailInput = document.querySelector("#username");
		emailInput.setCustomValidity("");

		if($("#login-form")[0].checkValidity() ){
			// Show overlay
			$.LoadingOverlay("show", {
				image: "",
				text: "Signing you in..."
			});
			
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
					} else {
						// Remove overlay
						$.LoadingOverlay("hide", true);
					
						// Add invalid tag
						emailInput.setCustomValidity("Username/password combination is incorrect");
						$("#login-form")[0].reportValidity();
					}
					
				}

			});
			
		}
		
	});

});
