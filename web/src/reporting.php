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
				<div id="child-select-box" class="row" >
					<div class="box-outer" style="padding-bottom: 0;">
						<div class="box-inner">	
							<div class="row">
								<div class="col-4">
									<p>Select a child</p>
									<select name="children" id="child-select-list" multiple>
										<!-- Filled with names from the database -->
									</select>
								</div>
								
								<div class="col-4">
									<p>Select a range of dates</p>
									<input type='text' name="daterange" id="calendar" value=""/>
								</div>
								
								<div class="col-4">
									<button type="button" id="generate-report-button" class="rounded-button purple-button">Generate Report</button>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row row-override">
					<div class="box-outer" >
						<div class="box-inner" id="table-box">
							<h3>Attendance Report</h3>
							<table id="user-table" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th class="th-sm">Name
											<i class="fa fa-sort float-right" aria-hidden="true"></i>
										</th>
										<th class="th-sm">Day
											<i class="fa fa-sort float-right" aria-hidden="true"></i>
										</th>
										<th class="th-sm">Attendance
											<i class="fa fa-sort float-right" aria-hidden="true"></i>
										</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<?php
			$scripts = array("report_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
		
		<!-- Date range picker http://www.daterangepicker.com -->
		<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
	</body>


</html>
