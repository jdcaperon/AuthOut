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
							<div type='text' class="datepicker-here" data-position="right top" data-range="true" data-language="en" id="calendar"><div/>

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
		</div>


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
