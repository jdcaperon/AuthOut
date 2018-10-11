<!DOCTYPE html>
<html lang="en">
	<?php 
		$pageName = "Help";
		$stylesheets = array("help_style.css");
		include_once ('head.php');
		
	?>
	
	<body>
		<?php include ('../html/nav.html'); ?>
		
		<div id="help-content">
			<h2>How can we help?</h2>
			<button type="button" id="login-button" class="rounded_button purple_button">How to use the site</button>
			<button type="button" id="login-button" class="rounded_button purple_button">Report a bug</button>
			<button type="button" id="login-button" class="rounded_button purple_button">Contact us</button>
		</div>
	</body>
	
	<?php 
		$scripts = array("help_script.js");
		include_once ('scripts.php');
	?>
</html>