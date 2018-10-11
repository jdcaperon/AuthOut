<?php
    include('php/head.php');
	include('php/nav_admin.php');
	include ("php/database_connect.php");
?>
    <body>
	<div id="admin-content"	>
		<div id="title">
			<h3>Visual Breakdowns</h3>
			<small>Note that mailing list subscriptions are equivalent to purchases</small>
		</div>





		<div class="container">
			<div class="row">
				<div class="col-sm-6">
					<?php
						$db = new DatabaseConnection();

						$result = $db->query('SELECT COUNT(*) FROM `views`');
						$result = $result->fetch_assoc();
						$result = $result['COUNT(*)'];

						echo "<h4>Total Views: $result</h4>";

						$result = $db->query('SELECT * FROM `mailing_list`');

						$db->disconnect();
					?>
					<h5>Views Per Week</h5>
					<div id="chart">
						<canvas id="Weekly" width="150" height="100"></canvas>
					</div>

				</div>

				<div class="col-sm-6">
					<?php
						$db = new DatabaseConnection();

						$result = $db->query('SELECT COUNT(*) FROM `mailing_list` WHERE 1');
						$result = $result->fetch_assoc();
						$result = $result['COUNT(*)'];

						echo "<h4>Mailing list subsciptions: $result</h4>";

						$result = $db->query('SELECT * FROM `mailing_list` WHERE 1');

						$db->disconnect();
					?>
					<h5>Update Frequecy Breakdown</h5>
					<div class="pie">
						<canvas id="frequency-chart" width="400" height="400"></canvas>
					</div>
					<h5>Early Bird Special Offer Breakdown</h5>
					<div class="pie">
						<canvas id="early-chart" width="400" height="400"></canvas>
					</div>
				</div>

			</div>
		</div>

		<div id="title">
			<h3>Raw Data</h3>
		</div>
		<div id="table-wrapper">
			<table class="table table-striped table-sm">
				<thead class="thead-dark">
					<tr>
						<th>Emails</th>
						<th>Names</th>
						<th>Frequency</th>
						<th>Early Bird</th>
					</tr>
				</thead>

				<tbody>
					<?php
						$db = new DatabaseConnection();

						$result = $db->query('SELECT * FROM `mailing_list` WHERE 1');
						while ($row = $result->fetch_assoc()) {
							echo "<tr>";
							echo "<td>".$row['email']."</td>";
							echo "<td>".$row['name']."</td>";
							echo "<td>".$row['updates']."</td>";
							echo "<td>".$row['early_bird']."</td>";
							echo "</tr>";
						}
					?>
				</tbody>
			</table>
		</div>

        <?php include 'php/footer_scripts.php'?>

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/admin.js" rel="javascript" type="text/javascript"></script>


    </body>
</html>
