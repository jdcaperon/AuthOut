$(document).ready(function() {
	// Data table
	var table;
	// Initialise modal
	$('#form-modal').modal({ show: false});

// -------------------------- Table and Modal --------------------------------
	
	$.ajax({
		method: "Get",
		url: "https://deco3801.wisebaldone.com/api/parent",
		success: function(data) {
			console.log(data);
		
			$('#parent-table').DataTable({
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
					{"data": "first_name"},
					{"data": "last_name"},
					{"data": "email"},
					{"data": "mobile_number"},
					{"data": "date_of_birth"},
					{"data": "id"},
					{"data": "children"}
				],
				"columnDefs": [ // Set ID and children to be invisible and not searchable
				{
					"targets": [ 5 ],
					"visible": false,
					"searchable": false
				},
				{
					"targets": [ 6 ],
					"visible": false,
					"searchable": false
				}
				],

			});
		
			table = $('#parent-table').DataTable();
			$('.dataTables_length').addClass('bs-select');
			
			// Setup function for clicking a row
			$("#parent-table tbody").on( 'click', 'tr', function () {
				data = table.row(this).data();
	
				// Add data to form
				$("h4.modal-title").text("Editing data for: " + data['first_name'] + " " + data['last_name']);
				$("input[name=id]").val(data['id']);
				$("input[name=fname]").val(data['first_name']);
				$("input[name=lname]").val(data['last_name']);
				$("input[name=email]").val(data['email']);
				$("input[name=mobile]").val(data['mobile_number']);
				$("input[name=dob]").val(data['date_of_birth']);

				// Clear select list
				$("#child-select-list").html("");
				
				// Loop over all children
				$(data['children']).each(function (key, value) {
					var name = value['first_name'] + " " + value['last_name'];
					
					// Add to select list
					$("#child-select-list").append($('<option>', {
						text: name
					}));
				});
				
				$('#form-modal').modal('show');
			});
			
		}		
		
	});

// ---------------------------------- Submit button ----------------------------------------------
	$("#save-button").click(function(event) {
		event.preventDefault();
		
		// clear validity
		document.querySelector("#mobile-input").setCustomValidity("");
		
		// Get data from form
		fields = document.getElementById("edit-form").elements;
		
		// Get values
		var fname = fields[2].value;
		var lname = fields[3].value;
		var email = fields[4].value;
		var mobile = fields[5].value;
		var dob = fields[6].value;
		
		// Validate fields
		validateMobile(mobile);
		validateDOB(dob);
		
		// Check if all fields are valid
		if($("#edit-form")[0].checkValidity()) {
			;
		} else {
			$("#edit-form")[0].reportValidity();
		}
	});
	
	// Validates a date of birth
	function validateDOB(dob) {
		var dobInput = document.querySelector("#dob-input");
		
		var parts = dob.split("/");
		
		// Check slashes were used
		if (parts.length != 3) {
			dobInput.setCustomValidity("DOB must be in the form DD/MM/YYYY");
		}
		
		var date = new Date(parts[2], parts[1], parts[0])
		
	}
	
	// Validates a mobile number
	function validateMobile(mobile) {
		var mobileInput = document.querySelector("#mobile-input");

		if (isNaN(mobile) || mobile.length != 10) {
			mobileInput.setCustomValidity("Mobile number is invalid.");
		}
		
	}
	
});