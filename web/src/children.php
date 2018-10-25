<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Report";
		$stylesheets = array("children_style.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>
		<div class="content">
			<div class="row row-override">
				<div class="col-8">
					<div class="box-outer" style="padding-right: 0">
						<div class="box-inner" id="table-box">
							<h3>All Children</h3>
							<p>Click on a row to edit a child.</p>
							<table id="child-table" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th>First Name</th>
										<th>Last Name</th>
										<th>Date of Birth</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>


				<div class="col-4">
					<div class="box-outer" >
						<div class="box-inner" id="account-form-box">

							<h4 id="add-title">Add A Child</h4>
							<span data-feather="user" id="user"></span>
							<form class="form-group" id="add-child-form">
								<div class="form-group">
									<div id="select-wrapper">
										<label  class="form-check-label" for="parents">Parents</label>
										<select name="parents" id="parent-select-list" multiple>
											<!-- Populated automatically -->
										</select>
									</div>
								</div>
								<div class="form-group">
									<label  class="form-check-label" for="fname">First Name</label>
									<input class="form-control" type="text" name="fname" id="fname" placeholder="Enter first name" required>
								</div>
								<div class="form-group">
									<label  class="form-check-label" for="lname">Last Name</label>
									<input class="form-control" type="text" name="lname" id="lname" placeholder="Enter last name" required>
								</div>
								<div class="form-group">
									<label  class="form-check-label" for="dob">Date of Birth</label>
									<input class="form-control" type="text" name="dob" id="dob-input-add" placeholder="DD/MM/YYYY" required>
								</div>

								<button id="add-submit" name="submit" type="submit" value="Submit" class="rounded-button purple-button">Submit</button>
							</form>
						</div>
					</div>
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
							<label for="parent">Parents:</label>
							<select name="parent" id="modal-parent-select-list" disabled multiple size="2">
								<!-- Populated from database -->
							</select>
							<br>
							<label for="parent">Trusted Parents:</label>
							<select name="parent" id="trusted-parent-select-list" disabled multiple size="2">
								<!-- Populated from database -->
							</select>
							<br><br>
							<label for="fname">First Name:</label>
							<input type="text" name="fname" id="modal-first-name" required></input>
							<br>
							<label for="lname">Last Name:</label>
							<input type="text" name="lname" id="modal-last-name" required></input>
							<br>
							<label for="dob">Date of Birth:</label>
							<input type="text" name="dob" id="dob-input" required></input>
						</form>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default narrow-rounded-button purple-button" id="delete-button">Delete</button>
						<!-- <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
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
			$scripts = array("children_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
