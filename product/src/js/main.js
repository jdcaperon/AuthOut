$(document).ready(function() {
	$("#submit-button").click(function(event){
		// Only submit if valid
		if($("#email-form")[0].checkValidity()) {
			event.preventDefault();

			// Get data from form
			fields = document.getElementById("email-form").elements;
			var email = fields[0].value;
			var name = fields[1].value;
			var frequency = fields[2].value;

			// Process the data
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
			document.getElementById("email-form").reset();

			// Add to database
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
					// Data was added
					if (data.localeCompare("1") == 0) {
						$("#modalDescription").html("<p>Your mailing list details have been saved.</p>");
						$("#validationModal").modal("show");
					// Data was updated
					} else {
						$("#modalDescription").html("<p>You've been added to the mailing list.</p>");
						$("#validationModal").modal("show");
					}

				}
			});

		}

	});

});
