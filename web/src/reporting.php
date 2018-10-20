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
		<div class="content">
			<div class="row row-override">
				<div class="col-8">
					<div class="box-outer" style="padding-right: 0;">
						<div class="box-inner">
						
						</div>
					</div>
				</div>
			
				<div class="col-4">
					<div id="child-select-box" class="row">
						<div class="box-outer" style="padding-bottom: 0">
							<div class="box-inner">
								<div class="center-group">
									<label>Child:</label><br>
									<select name="children" id="child-select-list">
										<option value="test1">Test</option>
										<option value="test2">Test 2</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					
					<div id="calendar-box" class="row">
						<div class="box-outer">
							<div class="box-inner">
								<div type='text' class="datepicker-here" data-position="right top" data-range="true" data-language="en" id="calendar"></div>
							</div>
						</div>
					</div>
					
					<div id="buttons-box" class="row">
						<div class="box-outer" style="padding-top: 0">
							<div class="box-inner">
								<div class="center-group">
									<button type="button" id="generate-report-button" class="rounded-button purple-button">Generate Report</button>
									<button type="button" id="save-report-button" class="rounded-button blue-button">Save Report</button>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
		</div>
		
		<!--
							<form id="report-form" class="vertical-center jumbotron">
						<label>Child:</label><br>
						<input type="text" name="childName"><br>
						<label>Start Date:</label><div id='start-date-widget' class="calendar"></div>
						<label>End Date:</label><div id='end-date-widget' class="calendar"></div>
					</form>
		-->

		<?php
			$scripts = array("report_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
