<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Tutorial";
		$stylesheets = array("tutorial_style.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>

		<div class="content">
			<div class="row row-override">
				<div class="col-4">
					<div class="box-outer" style="padding-right: 0">
						<div class="box-inner">
							<h3>Welcome!</h3>
							<p>We've kept things as simple as possible here to make your life easy.
							<p>Please visit the <a href="contact.php">Contact Us</a> page if you have any questions, queries or concerns</p>
						</div>
					</div>
				</div>

				<div class="col">
					<div class="box-outer">
						<div class="box-inner">
							<h3>Site Overview</h3>
							<ul>
								<li><a href="live.php">Live</a> shows real-time information about your centre's attendance activities. View headcount numbers and latest drop offs and pick ups.</li>
								<li> <a href="stats.php">Statistics</a> displays long-term attendance trends. Select a date range on the calendar to view averaged daily and hourly statistics.</li>
								<li> <a href="reporting.php">Reporting</a> allows you to view attendance data for a specific child. You can download this data to share it with other people.</li>
								<li> <a href="accounts.php">Accounts</a> is where you can manage user accounts for your centre.</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>



		<?php
			$scripts = array();
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
