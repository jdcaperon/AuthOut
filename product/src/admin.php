<?php
    include('php/head.php');
	include('php/nav_admin.php');
	include ("php/database_connect.php");
?>
    <body>
	<div id="admin-content"	>
		<?php
			$db = new DatabaseConnection();
			
			$result = $db->query('SELECT COUNT(*) FROM `mailing_list` WHERE 1');
			$result = $result->fetch_assoc();
			$result = $result['COUNT(*)'];
			
			echo "<h3>Mailing list subsciptions: $result</h3>";
			
			$result = $db->query('SELECT * FROM `mailing_list` WHERE 1');
			
			$db->disconnect();
		?>
	
		<div class="container">
			<div class="row">
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
			</div>
			
			<div class="row">
				<div class="col-sm-4">
					<div id="type">
						<canvas id="frequency-chart" width="400" height="400"></canvas>
					</div>	
				</div>
				
				<div class="col-sm-4">
					<div id="type">
						<canvas id="early-chart" width="400" height="400"></canvas>
					</div>	
				</div>
				
				<div class="col-sm-8">
					<div id="chart">
						<canvas id="Weekly" width="150" height="100"></canvas>
					</div>
				</div>
			</div>
			<small>Note that mailing list subscriptions are equivalent to purchases</small>

	</div>

        <?php include 'php/footer_scripts.php'?>

		<script src="js/admin.js" rel="javascript" type="text/javascript"></script>


    </body>
</html>
