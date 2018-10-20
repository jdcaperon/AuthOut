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

					<button type="button" id="login-button" class="rounded-button blue-button">LOG IN</button>
					<!-- TODO: add functionality  -->
					<button type="button" class="rounded-button purple-button">DON'T HAVE AN ACCOUNT?</button>
				</div>
			</div>
		</body

        <?php
		$scripts = array("index_script.js");
		include 'php_inclusions/scripts.php'
		?>

    </body>

</html>
