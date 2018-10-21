<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Accounts";
		$stylesheets = array("accounts_style.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>

		<?php include ('php_inclusions/nav.php'); ?>
		<div class="content">
			<div class="row row-override">
				<div class="col-4">
					<div class="box-outer" style="padding-right: 0">
						<div class="box-inner">
							<h3>Add A User</h3>
							<form id="add-user-form">
								<label for="name">Name</label>
								<input type="text" name="name" required>
								<br>
								<label for="email">Email</label>
								<input type="email" name="email" id="email-input" required>
								<br>
								<label for="password">Password</label>
								<input type="password" name="password" required>
								<br>
								<label for="onfirmPassword">Confirm Password</label>
								<input type="password" name="confirmPassword" id="confirm-password" required>
								
								<input id="submit" name="submit" type="submit" value="Submit" class="rounded-button purple-button">
							</form>
						</div>
					</div>
				</div>

				<div class="col-8">
					<div class="box-outer">
						<div class="box-inner" id="table-box">
						<h3>All Users</h3>
						<table id="user-table" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
							  <thead>
							    <tr>
							      <th class="th-sm">Name
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							      <th class="th-sm">Email
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							    </tr>
							  </thead>
							</table>
						</div>
					</div>
				</div>
			</div>
			
		</div>


		<?php
			$scripts = array("accounts_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
