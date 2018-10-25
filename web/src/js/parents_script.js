$(document).ready(function() {
	// Data table
	var table;
	// Initialise modal
	$('#form-modal').modal({ show: false});
	// parent ID to be edited
	var id;
	// selected children
	var selectedChildren;
	var selectedTrustedChildren;
	// array of all children
	var allChildren;
	// children selected before opening the form
	var childrenBeforeChange;
	var trustedChildrenBeforeChange;
	// Tracks if the table is updating
	var tableNeedsUpdate = true;
	
// -------------------------- Children List ---------------------------------

	$.ajax({
		method: "Get",
		url: "https://deco3801.wisebaldone.com/api/child",
		success: function(data) {
			allChildren = data;
			
			$(data).each(function(kay, value) {
				var name = value['first_name'] + " " + value['last_name'];
				var id = value['id'];
				
				// Add to child select list
				$("#child-select-list").append($('<option>', {
					value: id,
					text: name
				}));
				
				// Add to trusted child select list
				$("#trusted-child-select-list").append($('<option>', {
					value: id,
					text: name
				}));
				
				// Change to multiple select plugin
				$('#child-select-list').change(function() {
					selectedChildren = $(this).val();
				}).multipleSelect({
					width: '100%',
					placeholder: "Please select children",
					selectAll: false
				});
				
				// Change to multiple select plugin
				$('#trusted-child-select-list').change(function() {
					selectedTrustedChildren = $(this).val();
				}).multipleSelect({
					width: '100%',
					placeholder: "Please select trusted children",
					selectAll: false
				});
			
			});

		}
	});

// -------------------------- Table and Modal --------------------------------
	
	$.ajax({
		method: "Get",
		url: "https://deco3801.wisebaldone.com/api/parent",
		success: function(data) {
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
			// Set update flag to false
			var tableNeedsUpdate = false;
			
			// Setup function for clicking a row
			$("#parent-table tbody").on( 'click', 'tr', function () {
				data = table.row(this).data();
	
				// set ID
				id = data['id'];
	
				// Edit modal titles
				$("#edit-title").text("Editing data for: " + data['first_name'] + " " + data['last_name']);
				$("#delete-title").text("Are you sure you want to delete " + data['first_name'] + " " + data['last_name'] + "?");
				// Add data to form
				$("input[name=id]").val(id);
				$("input[name=fname]").val(data['first_name']);
				$("input[name=lname]").val(data['last_name']);
				$("input[name=email]").val(data['email']);
				$("input[name=mobile]").val(data['mobile_number']);
				$("input[name=dob]").val(data['date_of_birth']);
				
				// Tracks index of children to select in the list
				var toSelectChildren = new Array();
				var toSelectTrusted = new Array();
				childrenBeforeChange = new Array();
				trustedChildrenBeforeChange = new Array();
				
				// Loop over all children for this parent
				$(data['children']).each(function (key, value) {
					// Loop over total children
					$(allChildren).each(function (key2, value2) {
						if (value['id'] == value2['id']) {
							toSelectChildren.push(value['id']);
							childrenBeforeChange.push(value['id']);
						}
						
					});
					
				});
				
				// Loop over all trusted children for this parent
				$(data['trusted_children']).each(function (key, value) {
					// Loop over total children
					$(allChildren).each(function (key2, value2) {
						if (value['id'] == value2['id']) {
							toSelectTrusted.push(value['id']);
							trustedChildrenBeforeChange.push(value['id']);
						}
						
					});
					
				});
				
				$('#form-modal').modal('show');
				
				// Wait for selects to be visible before updating
				$('#form-modal').on('shown.bs.modal', function() {
					$('#child-select-list').multipleSelect("setSelects", toSelectChildren);
					$('#trusted-child-select-list').multipleSelect("setSelects", toSelectTrusted);
				});
				
				
			});
			
		}		
		
	});
	
	// Updates the table
	function updateTable() {
		$.ajax({
			method: "Get",
			url: "https://deco3801.wisebaldone.com/api/parent",
			success: function(data) {
				table.clear();
				table.rows.add(data).draw();
						
				// Hide overlay
				$(".content").LoadingOverlay("hide", true);	
				
				// Set update flage to false
				tableNeedsUpdate = false;
			}
			
		});
		
	}

// ---------------------------------- Submit button ----------------------------------------------
	$("#save-button").click(function(event) {
		event.preventDefault();
		
		// clear validity
		document.querySelector("#mobile-input").setCustomValidity("");
		document.querySelector("#dob-input").setCustomValidity("");
		document.querySelector("#email-input").setCustomValidity("");
		
		// Get data from form
		fields = document.getElementById("edit-form").elements;
		
		// Get values
		var fname = fields[fields.length - 5].value;
		var lname = fields[fields.length - 4].value;
		var email = fields[fields.length - 3].value;
		var mobile = fields[fields.length - 2].value;
		var dob = fields[fields.length - 1].value;
		
		// Validate fields
		validateMobile(mobile);
		validateDOB(dob);
		if (!validateEmail($("#email-input").val())) {
			document.querySelector("#email-input").setCustomValidity("Invalid email address");
		}
		
		// Check if all fields are valid
		if($("#edit-form")[0].checkValidity()) {
			// Hide modal
			$('#form-modal').modal('hide');
			// Show overlay
			$(".content").LoadingOverlay("show", {
				image: "",
				text: "Updating..."
			});
			
			// Set update flag to true
			tableNeedsUpdate = true;
			
			var toSend = {
				"first_name": fname,
				"last_name": lname,
				"date_of_birth": dob,
				"email": email,
				"mobile_number": mobile
			};
			
			$.ajax({
				method: "PUT",
				url: "https://deco3801.wisebaldone.com/api/parent/" + id,
				data: JSON.stringify(toSend),
				success: function(data) {
					// Get selected items
					var children = toInt($('#child-select-list').multipleSelect("getSelects"));
					var trusted = toInt($('#trusted-child-select-list').multipleSelect("getSelects"));
					
					// IDs to add/delete
					var childrenToAdd = new Array();
					var childrenToDelete = new Array();
					var trustedToAdd = new Array();
					var trustedToDelete = new Array();
					
					// Get Children to add
					$(children).each(function(key, value) {
						if ($.inArray(value, childrenBeforeChange) == -1) {
							childrenToAdd.push(value);
						}
						
					});
					
					// Get Children to delete
					$(childrenBeforeChange).each(function(key, value) {
						if ($.inArray(value, children) == -1) {
							childrenToDelete.push(value);
						}
						
					});
					
					// Get trusted to add
					$(trusted).each(function(key, value) {
						if ($.inArray(value, trustedChildrenBeforeChange) == -1) {
							trustedToAdd.push(value);
						}
						
					});
					
					// Get trusted to delete
					$(trustedChildrenBeforeChange).each(function(key, value) {
						if ($.inArray(value, trusted) == -1) {
							trustedToDelete.push(value);
						}
						
					});
					
					if (childrenToAdd.length > 0) {
						addChildren(childrenToAdd);
					}
					
					if (childrenToDelete.length > 0) {
						deleteChildren(childrenToDelete);
					}
					
					if (trustedToAdd.length > 0) {
						addTrusted(trustedToAdd);
					}
					
					if (trustedToDelete.length > 0) {
						deleteTrusted(trustedToDelete);
					}
					
					// Wait for all ajax requests to finish
					$(document).ajaxStop(function () {
						if (tableNeedsUpdate == true) {
							updateTable();
						}
									
					});
				}
			});
		} else {
			$("#edit-form")[0].reportValidity();
		}
		
	});
	
	function deleteTrusted(children) {
		toSend = {
			"children": children			
		};
					
		$.ajax({
			method: "DELETE",
			url: "https://deco3801.wisebaldone.com/api/parent/" + id + "/children/trusted",
			data: JSON.stringify(toSend),
			success: function(data) {
				;
			}
			
		});
		
	}
	
	function addTrusted(children) {
		toSend = {
			"children": children			
		};
					
		$.ajax({
			method: "POST",
			url: "https://deco3801.wisebaldone.com/api/parent/" + id + "/children/trusted",
			data: JSON.stringify(toSend),
			success: function(data) {
				;
			}
			
		});
	}
		
	
	function deleteChildren(children) {
		toSend = {
			"children": children			
		};
					
		$.ajax({
			method: "DELETE",
			url: "https://deco3801.wisebaldone.com/api/parent/" + id + "/children",
			data: JSON.stringify(toSend),
			success: function(data) {
				;
			}
			
		});
		
	}
	
	function addChildren(children) {
		toSend = {
			"children": children			
		};
					
		$.ajax({
			method: "POST",
			url: "https://deco3801.wisebaldone.com/api/parent/" + id + "/children",
			data: JSON.stringify(toSend),
			success: function(data) {
				;
			}
			
		});
		
	}
	
	function toInt(array) {
		$(array).each(function(key, value) {
			array[key] = parseInt(value);
		});
		
		return array;
	}
	
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
			url: "https://deco3801.wisebaldone.com/api/parent/" + id,
			success: function(data) {
				updateTable();
			}
			
		});
		
	});
	
	// Validates a date of birth
	function validateDOB(dob) {
		// List of days in every month
		var listOfDays = [31,28,31,30,31,30,31,31,30,31,30,31];
		
		var dobInput = document.querySelector("#dob-input");
		
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
	
	// Validates a mobile number
	function validateMobile(mobile) {
		var mobileInput = document.querySelector("#mobile-input");

		if (isNaN(mobile) || mobile.length != 10) {
			mobileInput.setCustomValidity("Mobile number is invalid.");
		}
		
	}
	
	// Validates an email address
	function validateEmail(email) {
		// https://www.w3resource.com/javascript/form/email-validation.php
		return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email);
	}
	
});