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
							<canvas id="attendance-chart"></canvas>
						</div>
					</div>
				</div>

				<div class="col-8">
					<div class="box-outer">
						<div class="box-inner">
						Today's Actvity
						<table id="dtBasicExample" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
							  <thead>
							    <tr>
							      <th class="th-sm">Time
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							      <th class="th-sm">Parent
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							      <th class="th-sm">Child
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							      <th class="th-sm">Action
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							    </tr>
							  </thead>
							  <tfoot>
							    <tr>
							      <th>Time
							      </th>
							      <th>Parent
							      </th>
							      <th>Child
							      </th>
							      <th>Action
							      </th>
							    </tr>
							  </tfoot>
							</table>
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


</html>
