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
							<iframe src="https://www.youtube.com/watch?v=phisEhrYKUI&feature=youtu.be?autoplay=1"> </iframe>
						</div>
					</div>
				</div>

				<div class = "page" id = "coloured-page">
					<div id ="features">
						<h3>Features</h3>
						<p>We've embraced progressive design techniques and technologies in creating AuthOut. Our product features:</p>
						<ul>
							<li>Easy Setup Kiosk</li>
							<li>Touch Screen</li>
							<li>High Quality Camera</li>
							<li>User-friendly Interface</li>
							<li>Admin Web Portal</li>
							<li>24-7 support</li>
						</ul>
						<p>We understand that parents don't want to line up and sign to collect their children every day.</p>
						<p>We understand that administrators hate data entry.</p>
						<p>We understand the importance of secure attendance systems.</p>
						<p>At AuthOut, we understand you.</p>
					</div>
				</div>
				<div class = "page">
					<div id="mail">
						<h3 >Mailing list</h3>
						<p>Sign up to our mailing list to receive updates about AuthOut.</p>
						<form id="email-form">
							<div class="form-group">
							    <label for="exampleInputEmail1">Email address</label>
							    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
							    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
						  	</div>
						  	<div class="form-group">
							    <label for="email-frequency">Frequency</label>
							    <select class="form-control" id="email-frequency">
								    <option>All updates</option>
								    <option>Monthly newsletter</option>
							      	<option>Product Announcements</option>
							    </select>
						  	</div>
						  	<button type="submit" class="btn btn-primary">Submit</button>
						</form>
					</div>
				</div>

			</div>
	</div>

        <?php include 'php/footer_scripts.php'?>

		<script src="//code.jquery.com/jquery-3.1.0.slim.min.js"></script>


    </body>
</html>
