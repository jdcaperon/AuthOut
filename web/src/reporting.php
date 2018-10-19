<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Report";
		$stylesheets = array("report_style.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>

		<div id="report-content" class="row">
			<div id="graph" class="col">
				<!--Graph here-->
				<img src="img/placeholder.png" style="width:100%; height:100%">
			</div>

			<div id="options" class="col-4 text-center">
				<form id="report-form" class="vertical-center jumbotron">
					<label>Child:</label><br>
					<input type="text" name="childName"><br>
					<label>Start Date:</label><div id='start-date-widget' class="calendar"></div>
					<label>End Date:</label><div id='end-date-widget' class="calendar"></div>
					<button type="button" id="generate-report-button" class="rounded_button purple_button">Generate Report</button>
					<button type="button" id="save-report-button" class="rounded_button blue_button">Save Report</button>
				</form>
			</div>
		</div>
		<?php
			$scripts = array("report_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
