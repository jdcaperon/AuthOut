<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Tutorial";
		$stylesheets = array();
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>
		<div class="content container">
			<div class="row">
				<div class="col">
					<h3>Welcome!</h3>
					<p>Welcome to Authout's Admin Portal. We've tried to keep things as simple as possible to make your time here easy.
					<p> We hope you enjoy using AuthOut. Please visit the <a href="contact.php">Contact Us</a> page if you have any questions, queries or concerns</p>
				</div>

				<div class="col">
					<h3>Site Overview</h3>
					<ul>
						<li><a href="live.php">Live</a> shows real-time information about your centre's attendance activities. You can track how many childern are signed in and view the latest drop offs and pick ups.</li>
						<li> <a href="stats.php">Statistics</a> displays long-term trends in attendance. Select a date range on the calendar to view daily and hourly attendance statistics.</li>
						<li> <a href="reporting.php">Reporting</a> allows you to view attendance data for a specific child. You can download this data to share with other people.</li>
						<li> <a href="accounts.php">Accounts</a> is where you can manage the accounts at your centre.</li>
					</ul>
				</div>
			</div>

		</div>


		<?php
			$scripts = array();
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
