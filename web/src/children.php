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
			
		</div>
		
		<?php
			$scripts = array("children_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
