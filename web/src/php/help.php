<!DOCTYPE html>
<html lang="en">
	<?php 
		$pageName = "Help";
		$stylesheets = array("help_style.css");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/head.php';
		include $_SERVER['DOCUMENT_ROOT'].'/html/nav.html';
		
	?>
	
	<body>
		<div id="help-content">
			<h2>How can we help?</h2>
			<button type="button" id="login-button" class="rounded_button purple_button">How to use the site</button>
			<button type="button" id="login-button" class="rounded_button purple_button">Report a bug</button>
			<button type="button" id="login-button" class="rounded_button purple_button">Contact us</button>
		</div>
	</body>
	
	<?php 
		$scripts = array("help_script.js");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/scripts.php';
	?>
</html>