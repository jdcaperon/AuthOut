<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Report";
		$stylesheets = array("parents_style.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>
		<div class="content">
			<div class="box-outer">
				<div class="box-inner">
					<h3>All Parents</h3>
					<p>Click on a row to edit a parent.</p>
					<table id="parent-table" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Email</th>
								<th>Mobile Number</th>
								<th>Date of Birth</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		
		<!-- Modal -->
		<div id="form-modal" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="edit-title"></h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					
					<div class="modal-body">
						<form id="edit-form">
							<label for="id">ID:</label>
							<input type="text" name="id" disabled></input>
							<br>
							<label for="children">Children:</label>
							<select name="children" id="child-select-list"  multiple>
								<!-- Populated from database -->
							</select>
							<br>
							<label class="modal-clear" for="trustedChildren">Trusted Children:</label>
							<select name="trustedChildren" id="trusted-child-select-list" multiple>
								<!-- Populated from database -->
							</select>
							<br><br>
							<label for="fname">First Name:</label>
							<input type="text" name="fname" required></input>
							<br>
							<label for="lname">Last Name:</label>
							<input type="text" name="lname" required></input>
							<br>
							<label for="email">Email:</label>
							<input type="text" name="email" id="email-input" required></input>
							<br>
							<label for="mobile">Mobile Number:</label>
							<input type="text" name="mobile" id="mobile-input" required></input>
							<br>
							<label for="dob">Date of Birth:</label>
							<input type="text" name="dob" id="dob-input" required></input>
						</form>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default narrow-rounded-button purple-button" id="delete-button">Delete</button>
						<!--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
						<button type="button" class="btn btn-primary narrow-rounded-button blue-button" id="save-button">Save changes</button>
					</div>
				</div>
			</div>
		</div>
		
		<div id="delete-modal" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="delete-title"></h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default narrow-rounded-button purple-button" data-dismiss="modal">No</button>
						<button type="button" class="btn btn-primary narrow-rounded-button blue-button" id="delete-confirm">Yes</button>
					</div>
				</div>
			</div>
		</div>
		
		<?php
			$scripts = array("parents_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
