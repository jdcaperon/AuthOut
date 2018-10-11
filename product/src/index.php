<?php
	include('php/database_connect.php');

	// Make all dates the same
	date_default_timezone_set("Australia/Brisbane");

    $db = new DatabaseConnection();

	$date = date('Y-m-d H:i:s');

	$db->prepared_query('INSERT INTO `views`(`date`) VALUES (?)', "s", $date);

	$db->disconnect();

    include('php/head.php');
	include('php/nav.php');
?>
    <body>

		<div id ="scroll" data-spy="scroll" data-target=".navbar" data-offset="50">
			<div id="content">
				<div class = "page">
					<div id="overview">
						<h3>Overview</h3>
							<div id="overview-1" >
							<p>AuthOut uses facial recognition technologies to track attendance in childcare centres.</p>
							</div>
							<div id="overview-2" >
								<iframe src="https://www.youtube.com/embed/phisEhrYKUI?autoplay=1" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
							</div>
							<p></p>


					</div>
				</div>

				<div class = "page" id = "coloured-page">
					<div id ="features">
						<h3>Features</h3>
						<div class="container">
							<div class="row">
								<div class="col-sm">
									<p class="paragraph-margin">We've embraced progressive design techniques and technologies in creating AuthOut. </p>
									<p>Our product features:</p>
									<ul>
										<li>Easy Setup Kiosk</li>
										<li>Touch Screen</li>
										<li>High Quality Camera</li>
										<li>User-friendly Interface</li>
										<li>Admin Web Portal</li>
										<li>24-7 support</li>
									</ul>
									<p class="paragraph-margin-bottom">Know someone who could use these features? Share this page!</p>
									<div class="addthis_inline_share_toolbox"></div>
								</div>
								<div class="col-sm" id ="carousel-container">

									<div id="carousel" class="carousel slide" data-ride="carousel">

										<div class="carousel-inner">
											<div class="carousel-item active">
											  <img class="d-block w-100" src="img/ryan.png" alt="Ryan">
											</div>
											<div class="carousel-item ">
											  <img class="d-block w-100" src="img/interface.png" alt="interface">
											</div>
											<div class="carousel-item">
											  <img class="d-block w-100" src="img/done.png" alt="Third slide">
											</div>
											<a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
											            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
											            <span class="sr-only">Previous</span>
											</a>
											<a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
											            <span class="carousel-control-next-icon" aria-hidden="true"></span>
											            <span class="sr-only">Next</span>
											</a>
										</div>

										</a>
									</div>
								</div>

							</div>
						</div>


					</div>
				</div>
				<div class = "page">
					<div id="mail">
						<h3 >Purchase</h3>
						<div id="mail-content">
							<p>Our product is still under development.</p>
								<form id="email-form">
								<div class="form-group">
									<p> Sign up to our mailing list to receive AuthOut status updates.</p>
								</div>
								<div class="form-group">
								    <label>Email address</label>
								    <input type="email" class="form-control" aria-describedby="emailHelp" placeholder="Enter email" required>
								    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
							  	</div>
								<div class="form-group">
									<label >Name</label>
									<input type="name" class="form-control"  placeholder="Enter first name" required>
								</div>
							  	<div class="form-group">
								    <label>Frequency</label>
								    <select class="form-control" required>
									    <option>All updates</option>
									    <option>Monthly newsletter</option>
								      	<option>Product announcements</option>
								    </select>
							  	</div>
								<div class="form-group form-check">
									<div class="custom-control custom-checkbox">
									  <input type="checkbox" class="custom-control-input" id="customCheck1" checked="checked" required>
									  <label class="custom-control-label" for="customCheck1">Yes, I would like to register for early-bird offers.</label>
									</div>

							  	</div>
							  	<button type="submit" class="btn btn-primary rounded_button blue_button" id="submit-button">Submit</button>
							</form>
						</div>
					</div>
				</div>

			</div>
	</div>

        <?php include 'php/footer_scripts.php'?>

		<!-- www.addthis.com/dashboard Sharing tools -->
		<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5bbd909968418185"></script>

    </body>
</html>
