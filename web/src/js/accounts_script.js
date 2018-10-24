$(document).ready(function() {
	
//--------------------------------------------- Data Table ----------------------------------------------------------

	// Accounts table
	var table;

	$.ajax({
		method: "GET",
		url: "https://deco3801.wisebaldone.com/api/admin/",
		success: function(data) {
			
			$('#user-table').DataTable({
				"ordering": false, // false to disable sorting (or any other option),
				"bLengthChange": false,
				"bFilter": true,
				"bAutoHeight": false,
				"scrollY":260,
				"scrollX": false,
				"scrollColapse": false,
				"displayLength":10,
				language: {
				searchPlaceholder: "Search records",
				search: "",
				},
				"order": [1, 'asc'],
				"data": data,
				"columns": [
					{"data": "name"},
					{"data": "email"},
				]
			});
			
			table = $('#user-table').DataTable();
			$('.dataTables_length').addClass('bs-select');
		}
	});
	
//----------------------------------------- New Account ------------------------------------------------------
$("#submit").click(function(event) {
	// Clear validity
	var emailInput = document.querySelector("#email-input");
	emailInput.setCustomValidity("");
	
	// Get data from form
	fields = document.getElementById("add-user-form").elements;
	
	// Get data from the field
	var name = fields[0].value;
	var email = fields[1].value;
	var password = fields[2].value;
	var confirmPassword = fields[3].value;
	
	// Get password input
	var passwordInput = document.querySelector("#confirm-password");
	
	// Validate passwords
	if (password.localeCompare(confirmPassword) != 0) {
		passwordInput.setCustomValidity("Passwords don't match");
	} else {
		passwordInput.setCustomValidity("");
	}
	
	// Create send array
	var toSend = {
		"email": email,
		"password": password,
		"name": name,
	}
	
	// Only submit if valid
	if ($("#add-user-form")[0].checkValidity()) {
		event.preventDefault();
		
		$.ajax({
			method: "POST",
			url: "https://deco3801.wisebaldone.com/api/admin/",
			data: JSON.stringify(toSend),
			success: function(data) {
				// Account was created
				if (data.localeCompare("Created Admin") == 0) {
					// Add to table
					table.row.add(toSend).draw();
					
					// Show overlay
					$("#account-form-box").LoadingOverlay("show", {
						image: "",
						text: "Success!"
					});
					
					// Hide overlay after 2 seconds
					setTimeout(function(){
						$("#account-form-box").LoadingOverlay("hide");
					}, 2000);
				} else if (data.localeCompare("Unable to create account") == 0) {
					// Add invalid tag
					emailInput.setCustomValidity("Account already exists");
					$("#add-user-form")[0].reportValidity();
				}
				
			}
			
		});
		
	}
	
});

});