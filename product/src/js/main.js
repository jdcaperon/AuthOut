$(document).ready(function() {
	$("#submit-button").click(function(event){
		event.preventDefault();
		
		fields = document.getElementById("email-form").elements;

		var email = fields[0].value;
		var name = fields[1].value;
		var frequency = fields[2].value;

		switch(frequency) {
			case "All updates":
				frequency = "all";
				break;

			case "Monthly newsletter":
				frequency = "monthly";
				break;

			case "Product announcements":
				frequency = "announcements";
				break;
		}

		var earlyBird = fields[3].checked == 1 ? 1 : 0;

		$.ajax({
			method: "POST",
			url: "php/interest_form.php",
			data: {
				email: email,
				name: name,
				frequency: frequency,
				earlyBird: earlyBird,
			},
			success: function(data) {
				console.log(data);
			}
		});

	});

});
