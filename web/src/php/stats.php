<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Statistics";
		$stylesheets = array("stats_style.css");
		include_once ('head.php');

	?>

	<body>
		<?php include ('../html/nav.html'); ?>

		<div id="stats-content" class="row">
			<div id="graph" class="col">
				<h2 class="graph_title">Data Displayed - Timeframe</h2>
				<div class = "jumbotron" style="height:100%;">
					<canvas id="Weekly"></canvas>
				</div>
			</div>

			<div id="options" class="col-4 text-center">
				<form id="stats-form" class="vertical-center jumbotron">
					<label>Graph type:</label><br>
					<select name="graphType"><br>
						<option value="bar">Bar</option>
						<option value="line">Line</option>
						<option value="scatter">Scatter</option>
						<option value="pieChart">Pie Chart</option>
					</select><br>

					<label>Data to Display:</label><br>
					<select name="displayData"><br>
						<option value="attendance">Daily Attendance</option>
						<option value="dropOffTime">Drop-off Time</option>
						<option value="pickUpTime">Pick-up Time</option>
						<option value="childrenPerParent">Children Per Parent</option>
					</select><br>

					<label>Start Date:</label><div id='start-date-widget' class="calendar"></div>
					<label>End Date:</label><div id='end-date-widget' class="calendar"></div>
					<button type="button" id="display-data-button" class="rounded_button purple_button">Dispaly Data</button>
				</form>
			</div>
		</div>
	</body>

	<?php
		$scripts = array("stats_script.js");
		include_once ('scripts.php');
	?>
	
</html>
