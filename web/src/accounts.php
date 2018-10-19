<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Accounts";
		$stylesheets = array();
		include_once ('php_inclusions/head.php');

	?>

	<body>
		
		<?php include ('php_inclusions/nav.php'); ?>
		<div class="content">
		</div>


		<?php
			$scripts = array();
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
