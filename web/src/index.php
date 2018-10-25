<?php
    session_start();
	// Include head and desired style sheets
	$stylesheets = array("login.css");
	include("php_inclusions/head.php");

?>
<!DOCTYPE html>
<html lang="en">
	<body>
		<body id="login">
			<div id="login-window">
				<div id="login-content">
					<div id="login-logo">
						<img src="img/authoutlogo.png" alt="logo">
						<h1>AuthOut Admin Portal</h1>
					</div>

					<h3> Please log in </h3>


					<form id="login-form" class="container clearfix">
						<div class=" form-group clearfix">

							<input required type="text" class="form-control" name="username" id="username" placeholder="Username">
						</div>
						<div class="form-group clearfix">
							<input  required title="password" class='form-control' type="password" name="password" id="password" placeholder="Password">
						</div>

						<button type="submit" id="login-button" class="rounded-button blue-button">LOG IN</button>

						<button type="button" class="rounded-button purple-button" data-toggle="modal" data-target="#no-account">DON'T HAVE AN ACCOUNT?</button>


					</form>


				</div>

			</div>


			<!-- Modal -->
			<div class="modal fade" id="no-account" tabindex="-1" role="dialog" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h2 class="modal-title" id="exampleModalLabel">No account?</h2>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
			        <p>Please contact your manager for your account information.</p>
					<p>If you are the manager, check your AuthOut start-up pack!</p>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			      </div>
			    </div>
			  </div>
			</div>

        <?php
		$scripts = array("index_script.js");
		include 'php_inclusions/scripts.php'
		?>

    </body>

</html>
