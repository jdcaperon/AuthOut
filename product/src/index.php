<?php
    include('php/head.php');
	include('php/nav.php');
?>
    <body>
		<div id="content">
			<iframe width="420" height="315" src="https://www.youtube.com/watch?v=phisEhrYKUI&feature=youtu.be?autoplay=1"> </iframe>
			<h3 id="overview">Overview</h3>
			<p>
				The paper-based sign in/out process currently used by the majority of childcare centres is outdated. Authout uses facial scanning and recognition technologies to identify parents, match them to their children and, with a single touch, sign their children in or out of the childcare service.
			</p>
			<p>
				Our product will deliver value to both parents and child care staff because it streamlines current processes, saves people time and minimises human errors associated with manual data entry
			</p>

			<h3 id="features">Features</h3>
			<ul>
				<li>Touch Screen</li>
				<li>High Quality Camera</li>
				<li>User-friendly Interface</li>
				<li>Admin Web Portal</li>
			</ul>

			<h3 id="mail">Mailing list</h3>
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


        <?php include 'php/footer_scripts.php'?>

		<script src="//code.jquery.com/jquery-3.1.0.slim.min.js"></script>


    </body>
</html>
