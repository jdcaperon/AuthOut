<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Live Monitor";
		$stylesheets = array("monitor_style.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>

		<div id="monitor-content" class="row">
			<div id="graph1" class="col">
				<h2 class="graph_title">Sign-in History</h2>
				<div  class="jumbotron">
					<table id="sign-in-table" class="live_table" width="100%" height="100%">
						<tr>
							<th>Parent</th>
							<th>Confidence</th>
							<th>Child</th>
							<th>Time</th>
						</tr>

						<?php
							for($i = 0; $i < 50; $i++) {
								echo "
									<tr>
										<td>Example</td>
										<td>100%</td>
										<td>Exmple</td>
										<td>00:00</td>
									</tr>
								";
							}

						?>
					</table>
				</div>
			</div>

			<div id="graph2" class="col">
				<h2 class="graph_title">Sign-out History</h2>
				<div  class="jumbotron">
					<table id="sign-out-table" class="live_table" width="100%" height="100%">
						<tr>
							<th>Parent</th>
							<th>Confidence</th>
							<th>Child</th>
							<th>Time</th>
						</tr>

						<?php
							for($i = 0; $i < 50; $i++) {
								echo "
									<tr>
										<td>Example</td>
										<td>100%</td>
										<td>Exmple</td>
										<td>00:00</td>
									</tr>
								";
							}

						?>
					</table>
				</div>
			</div>
		</div>
		<?php
			$scripts = array("monitor_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
