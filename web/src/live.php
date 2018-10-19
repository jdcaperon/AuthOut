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
		<div class="content">
			<div class="row row-override">
				<div class="col-4">
					<div class="box-outer" style="padding-right: 0">
						<div class="box-inner">
						
						</div>
					</div>
				</div>
				
				<div class="col">
					<div class="box-outer">
						<div class="box-inner">
						
						</div>
					</div>
				</div>
			</div>
		</div>
		<?php
			$scripts = array("live_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>

	<!--
	TODO: delete
	
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
	-->
</html>
