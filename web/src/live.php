<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Live Monitor";
		$stylesheets = array("live_style.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>
		<div class="content container">
			<div class="row">
				<div class="col">
					<p> Signed In:</p>
					<p> Signed Out: </p>
				</div>

				<div class="col">
					<h3>Today's Activity </h3>
					<input class="form-control" type="text" placeholder="Search" aria-label="Search">
					<table></table>
				</div>
			</div>
		</div>
		<?php
			$scripts = array("live_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
