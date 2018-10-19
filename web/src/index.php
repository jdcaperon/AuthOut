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


					<form id="login-form" class="container clearfix">
						<div class="clearfix"><label for="username">Username:</label><input type="text" name="username" id="username"></div>
						<div class="clearfix"><label for="password">Password:</label><input type="text" name="password" id="password"></div>
					</form>

					<button type="button" id="login-button" class="rounded_button blue_button">LOGIN</button>
					<button type="button" class="rounded_button purple_button">DON'T HAVE AN ACCOUNT?</button>
				</div>
			</div>
		</body

        <?php
		$scripts = array("index_script.js");
		include 'php_inclusions/scripts.php'
		?>

    </body>

</html>
