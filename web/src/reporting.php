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
			
			<div class="box-outer">
				<div class="box-inner" id="child-reporting">
					
					<div id="child-select-box">
						<h3>Select Data</h3><br>
						<div class="row">
							<div class="col-4">
								<form id="child-form">
									<p>Select children: </p>
									<select name="children" id="child-select-list" multiple required>
										<!-- Filled with names from the database -->
									</select>
								</form>
							</div>
						
							<div class="col-4" id="date-select">
								<p>Select a range of dates: </p>
								<input type='text' name="daterange" id="calendar" value=""/>
							</div>
						
							<div class="col-4" id="generate-button-container">
								<button type="button" id="generate-report-button" class="rounded-button purple-button">Generate Report</button>
							</div>
						</div>
					</div>
					
					<div id="child-report-box">
						<h3>Attendance Report</h3><br>
						<table id="user-table" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
							<thead>
								<tr>
									<th class="th-sm">Name
										<i class="fa fa-sort float-right" aria-hidden="true"></i>
									</th>
									<th class="th-sm">Date
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
		
		<?php
			$scripts = array("report_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
		
		<!-- Date range picker http://www.daterangepicker.com -->
		<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/plug-ins/1.10.19/sorting/datetime-moment.js"></script>
	</body>


</html>
