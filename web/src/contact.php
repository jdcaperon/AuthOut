<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Contact";
		$stylesheets = array();
		include_once ('php_inclusions/head.php');
	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>
		<div class="content container">
			<div class="row">
				<div class="col">

					<p>We'd love to hear from you! Use this form to send us your questions and comments</p>
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
				<div class="col">
					<p>AuthOut proudly built by Rocket Potatoes in collaboration with 3CS Software. <p>
						<img  src="img/3cs.png" alt="3CS Logo" id="logo"/>
						<img  src="img/rp_logo.png" alt="3CS Logo" id="logo"/>
				</div>
			</div>

		</div>

		<?php
			$scripts = array();
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
