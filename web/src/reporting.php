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
				<div class="col-5">
					<div id="child-select-box" class="row" >
						<div class="box-outer" style="padding-bottom: 0; padding-right: 0;">
							<div class="box-inner">
								<div class="center-group">
									<p>Select a child</p>
									<select name="children" id="child-select-list">
										<!-- Filled with names from the database -->
									</select>
								</div>
							</div>
						</div>
					</div>

					<div id="calendar-box" class="row">
							<div class="box-outer"  id="jordan-hack" style="padding-right: 0;">
								<div class="box-inner" id="cal">
									<div >
										<p>Select a range of dates</p>
										<div type='text' class="datepicker-here" data-position="right top" data-range="true" data-language="en" id="calendar">
										</div>
									</div>

								</div>
							</div>
					</div>

					<div id="buttons-box" class="row">
						<div class="box-outer" style="padding-top: 0;padding-right: 0;">
							<div class="box-inner">
								<div class="center-group">
									<button type="button" id="generate-report-button" class="rounded-button purple-button">Generate Report</button>
									<a id="export-link" class="btn  rounded-button blue-button" download="MailingList.csv" href="#" onclick="return ExcellentExport.csv(this, 'user-table');">Save Report</a>




								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-7">
					<div class="box-outer" >
						<div class="box-inner" id="table-box">
							<h3>Attendance Report</h3>
							<table id="user-table" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th class="th-sm">Day
											<i class="fa fa-sort float-right" aria-hidden="true"></i>
										</th>
										<th class="th-sm">Attendance
											<i class="fa fa-sort float-right" aria-hidden="true"></i>
										</th>
									</tr>
									<tr>
										<td>Test
										</td>
										<td>Test
										</td>
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
	</body>


</html>
