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
						<div class="clearfix"><input class='form-control' type="text" name="username" id="username" placeholder="Username"></div>
						<div class="clearfix"><input  class='form-control' type="password" name="password" id="password" placeholder="Password"></div>
					</form>

					<button type="button" id="login-button" class="rounded_button blue_button">LOG IN</button>
					<!-- TODO: add functionality  -->
					<button type="button" class="rounded_button purple_button" data-toggle="modal" data-target="#noAccountModal">DON'T HAVE AN ACCOUNT?</button>
				</div>
			</div>
		</body

        <?php
		$scripts = array("index_script.js");
		include 'php_inclusions/scripts.php'
		?>

    </body>

</html>

<!-- Modal -->
<div class="modal fade" id="noAccountModal" tabindex="-1" role="dialog" >
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">No Account?</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
		  <br>
        <p> Please talk to your manager - they'll be able to help you out. If you are the manager, check your startup guide for account information!</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn rounded_button blue_button" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
