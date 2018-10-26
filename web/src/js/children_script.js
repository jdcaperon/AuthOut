$(document).ready(function() {
	// Data table
	var table;
	// child ID to be edited
	var id;
	
// -------------------------- Parents List ---------------------------------

	$.ajax({
		method: "Get",
		url: "https://deco3801.wisebaldone.com/api/parent",
		success: function(data) {
			$(data).each(function(kay, value) {
				var name = value['first_name'] + " " + value['last_name'];
				var id = value['id'];
				
				// Add to select list
				$("#parent-select-list").append($('<option>', {
					value: id,
					text: name
				}));
				
				// Change to multiple select plugin
				$('#parent-select-list').change(function() {
					selectedChildren = $(this).val();
				}).multipleSelect({
					width: '100%',
					padding: "10px",
					placeholder: "Please select a parent"
				});
			
			});

		}
	});

// -------------------------- Table and Modal --------------------------------
	
	$.ajax({
		method: "Get",
		url: "https://deco3801.wisebaldone.com/api/child/",
		success: function(data) {
			$('#child-table').DataTable({
				"ordering": false, // false to disable sorting (or any other option),
				"bLengthChange": false,
				"bFilter": true,
				"bAutoHeight": false,
				"scrollY":300,
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
					{"data": "date_of_birth"},
					{"data": "id"},
					{"data": "parents"},
					{"data": "trusted_parents"}
				],
				"columnDefs": [ // Set ID and parents to be invisible and not searchable
				{
					"targets": [ 3 ],
					"visible": false,
					"searchable": false
				},
				{
					"targets": [ 4 ],
					"visible": false,
					"searchable": false
				},
				{
					"targets": [ 5 ],
					"visible": false,
					"searchable": false
				}
				],

			});
			
			table = $('#child-table').DataTable();
			$('.dataTables_length').addClass('bs-select');
			
			// Setup function for clicking a row
			$("#child-table tbody").on( 'click', 'tr', function () {
				data = table.row(this).data();
	
				// set ID
				id = data['id'];
	
				// Edit modal titles
				$("#edit-title").text("Editing data for: " + data['first_name'] + " " + data['last_name']);
				$("#delete-title").text("Are you sure you want to delete " + data['first_name'] + " " + data['last_name'] + "?");
				// Add data to form
				$("input[name=id]").val(id);
				$("#modal-first-name").val(data['first_name']);
				$("#modal-last-name").val(data['last_name']);
				$("#dob-input").val(data['date_of_birth']);

				// Clear select list
				$("#modal-parent-select-list").html("");
				$("#trusted-parent-select-list").html("");
				
				// Loop over all parents
				$(data['parents']).each(function (key, value) {
					// Add to select list
					$("#modal-parent-select-list").append($('<option>', {
						text: value
					}));
					
				});
				
				// Loop over all trusted parents
				$(data['trusted_parents']).each(function (key, value) {
					// Add to select list
					$("#trusted-parent-select-list").append($('<option>', {
						text: value
					}));
				});
				
				$('#form-modal').modal('show');
			});
			
			$("#child-table tbody").css("cursor", "pointer");
			
		}
		
	});
	
	// Updates the table
	function updateTable() {
		$.ajax({
			method: "Get",
			url: "https://deco3801.wisebaldone.com/api/child",
			success: function(data) {
				table.clear();
				table.rows.add(data).draw();
						
				// Hide overlay
				$(".content").LoadingOverlay("hide", true);	
			}
			
		});
		
	}
	
// ---------------------------------- Submit button ----------------------------------------------
	$("#save-button").click(function(event) {
		event.preventDefault();
		
		// clear validity
		document.querySelector("#dob-input").setCustomValidity("");
		
		// Get data from form
		fields = document.getElementById("edit-form").elements;
		
		// Get values
		var fname = fields[3].value;
		var lname = fields[4].value;
		var dob = fields[5].value;
		
		// Validate fields
		validateDOB(dob, "#dob-input");
		
		// Check if all fields are valid
		if($("#edit-form")[0].checkValidity()) {
			// Hide modal
			$('#form-modal').modal('hide');
			// Show overlay
			$(".content").LoadingOverlay("show", {
				image: "",
				text: "Updating..."
			});
			
			var toSend = {
				"first_name": fname,
				"last_name": lname,
				"date_of_birth": dob
			};
			
			$.ajax({
				method: "PUT",
				url: "https://deco3801.wisebaldone.com/api/child/" + id,
				data: JSON.stringify(toSend),
				success: function(data) {
					updateTable();
				}
			});
		} else {
			$("#edit-form")[0].reportValidity();
		}
	});
	
// ---------------------------- Add Submit Button -------------------------------

	$("#add-submit").click(function(event) {
		event.preventDefault();
		
		// clear validity
		document.querySelector("#dob-input-add").setCustomValidity("");
		
		// Get data from form
		fields = document.getElementById("add-child-form").elements;
		
		// Get values
		var fname = fields[fields.length - 4].value;
		var lname = fields[fields.length - 3].value;
		var dob = fields[fields.length - 2].value;
		
		// Validate fields
		validateDOB(dob, "#dob-input-add");
		
		// Get the selected parent IDs
		var IDs = $("#parent-select-list").multipleSelect('getSelects');
		// Convert to integers
		$(IDs).each(function(key, value){
			IDs[key] = parseInt(value);
		});
		
		// Check if parents are selected
		if (IDs.length > 0) {
			// Only submit if valid
			if ($("#add-child-form")[0].checkValidity()) {
				var toSend = {
					"parent_id": IDs,
					"first_name": fname,
					"last_name": lname,
					"date_of_birth": dob,
				};
				
				$.ajax({
				method: "POST",
				url: "https://deco3801.wisebaldone.com/api/child/",
				data: JSON.stringify(toSend),
				success: function(data) {
					// Update the table
					$.ajax({
						method: "Get",
						url: "https://deco3801.wisebaldone.com/api/child",
						success: function(data) {
							table.clear();
							table.rows.add(data).draw();
							
							// Show overlay
							$("#child-form-box").LoadingOverlay("show", {
								image: "",
								text: "Success!"
							});
							
							// Hide overlay after 2 seconds
							setTimeout(function(){
								$("#child-form-box").LoadingOverlay("hide");
							}, 2000);
						}
						
					});
					
				}
			});
				
			} else {
				$("#add-child-form")[0].reportValidity();
			}
		} else {
			$("#select-wrapper").effect('shake');
			$("#add-child-form")[0].reportValidity();
		}
		
	});
	
// --------------------------- Delete Buttons ------------------------------------

	$("#delete-button").click(function() {
		$('#delete-modal').modal('show');
	});
	
	$("#delete-confirm").click(function() {
		$('#delete-modal').modal('hide');
		$('#form-modal').modal('hide');
		
		// Show overlay
		$(".content").LoadingOverlay("show", {
			image: "",
			text: "Deleting..."
		});
		
		// Delete parent
		$.ajax({
			method: "DELETE",
			url: "https://deco3801.wisebaldone.com/api/child/" + id,
			success: function(data) {
				updateTable();
			}
			
		});
		
	});
	
	// Validates a date of birth
	function validateDOB(dob, inputID) {
		// List of days in every month
		var listOfDays = [31,28,31,30,31,30,31,31,30,31,30,31];
		
		var dobInput = document.querySelector(inputID);
		
		// Substrings in the date
		var parts = dob.split("/");
		
		// Check slashes were used
		if (parts.length != 3) {
			dobInput.setCustomValidity("DOB must be in the form DD/MM/YYYY");
		}
		
		// Loop over each substring
		$(parts).each(function(key, value) {
			// Check if it is a number
			if (isNaN(value)) {
				dobInput.setCustomValidity("DOB must be in the form DD/MM/YYYY");
			}
			
			// Check length of substrings
			if (key == 0) {
				// Check length
				if (value.length != 2) {
					dobInput.setCustomValidity("DOB must be in the form DD/MM/YYYY");
				}
				
				// Check day
				if (parseInt(value) < 1) {
					dobInput.setCustomValidity("Invalid Day");
				}
			} else if (key == 1) {
				if (value.length != 2) {
					dobInput.setCustomValidity("DOB must be in the form DD/MM/YYYY");
				}
				
				var month = parseInt(value);
				var day = parseInt(parts[0]);
				
				// Check if the day matches the month
				if (day > listOfDays[month - 1]) {
					dobInput.setCustomValidity("Invalid day for the given month");
				}
				
				// Check the month
				if (month > 12) {
					dobInput.setCustomValidity("Invalid month");
				} else if (month < 1) {
					dobInput.setCustomValidity("Invalid month");
				}
			} else if (key == 2) {
				// Check length
				if (value.length != 4) {
					dobInput.setCustomValidity("DOB must be in the form DD/MM/YYYY");
				}
				
				// Check year
				if (parseInt(value) < 1) {
					dobInput.setCustomValidity("Invalid Year");
				}
			}			
			
		});
		
	}
	
});