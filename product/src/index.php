<?php
    include('php/head.php');
	include('php/nav.php');
?>
    <body>
		<div id ="scroll">
			<div id="content">
				<div class = "page">
					<div id="overview">
						<h3>Overview</h3>
						<div id="overview-1">
						<p>AuthOut uses facial recognition technologies to track attendance in childcare centres</p>
						</div>
						<div id="overview-2">
							<iframe width="560" height="315" src="https://www.youtube.com/embed/phisEhrYKUI" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
						</div>
					</div>
				</div>

				<div class = "page" id = "coloured-page">
					<div id ="features">
						<h3>Features</h3>
						<div class="container">
							<div class="row">
								<div class="col-sm">
									<br><br><br>
									<p>We've embraced progressive design techniques and technologies in creating AuthOut. </p><br><br>
									<p>Our product features:</p>
									<ul>
										<li>Easy Setup Kiosk</li>
										<li>Touch Screen</li>
										<li>High Quality Camera</li>
										<li>User-friendly Interface</li>
										<li>Admin Web Portal</li>
										<li>24-7 support</li>
									</ul>
								</div>
								<div class="col-sm">
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
								</div>

							</div>
						</div>


					</div>
				</div>
				<div class = "page">
					<div id="mail">
						<h3 >Purchase</h3>
						<div id="mail-content">
							<p>Our product is still under development. Sign up to our mailing list to receive AuthOut status updates.</p>
							<form id="email-form">
								<div class="form-group">
								    <label >Email address</label>
								    <input type="email" class="form-control" aria-describedby="emailHelp" placeholder="Enter email">
								    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
							  	</div>
								<div class="form-group">
									<label >Name</label>
									<input type="name" class="form-control"  placeholder="Enter first name">
								</div>
							  	<div class="form-group">
								    <label>Frequency</label>
								    <select class="form-control" >
									    <option>All updates</option>
									    <option>Monthly newsletter</option>
								      	<option>Product Announcements</option>
								    </select>
							  	</div>
								<div class="form-group form-check">
									<input class="form-check-input" type="radio" id="offer" value="offer" checked>
									 <small class="form-check-label" for="offer"> Yes, I would like to register for early-bird offers.</small>
							  	</div>
							  	<button type="submit" class="btn btn-primary rounded_button blue_button">Submit</button>
							</form>
						</div>
					</div>
				</div>

			</div>
	</div>

        <?php include 'php/footer_scripts.php'?>

		<script src="//code.jquery.com/jquery-3.1.0.slim.min.js"></script>


    </body>
</html>
