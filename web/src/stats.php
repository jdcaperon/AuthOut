<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Live Monitor";
		$stylesheets = array("stats_style.css");
		$externalSheets = array("lib/jqwidgets/styles/jqx.base.css", "lib/jqwidgets/styles/jqx.custom.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>

		<div class="content">
			<div class="row row-override">
				<div class="col-4">
					<div class="box-outer" style="padding-right: 0; padding-bottom: 0">
						<div class="box-inner">
							<div id="calendar-widget"></div>
						</div>
					</div>
				</div>
				
				<div class="col-8">
					<div class="box-outer" style="padding-bottom: 0">
						<div class="box-inner">
							<canvas id="attendance-graph"></canvas>
						</div>					
					</div>
				</div>
			</div>
			
			<div class="row row-override">
				<div class="col-12">
					<div class="box-outer">
						<div class="box-inner">
							<canvas id="times-graph"></canvas>
						</div>					
					</div>
				</div>
			</div>
		</div>
		
		<!--
		<div id="stats-content" class="content row">
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
		-->
		<?php
			$scripts = array("stats_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
		<script type="text/javascript" src="lib/jqwidgets/jqxcore.js"></script>
		<script type="text/javascript" src="lib/jqwidgets/jqxdatetimeinput.js"></script>
		<script type="text/javascript" src="lib/jqwidgets/jqxcalendar.js"></script>
		<script type="text/javascript" src="lib/jqwidgets/jqxtooltip.js"></script>
		<script type="text/javascript" src="lib/jqwidgets/globalization/globalize.js"></script>
	</body>


</html>
