<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		// Include head and desired style sheets
		$stylesheets = array("login.css");
	?>
	
	<head>
		<title>AuthOut</title>
		<meta charset="utf-8" name="description" content="A web-application to facilitate the signing in/out process in child care centres ."/>

		<!--Bootstrap-->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Alex+Brush|Cookie|Lobster|Playball|Tangerine" rel="stylesheet">

		<!--Main stylesheet-->
		<link href="css/main.css" rel="stylesheet" type="text/css">

		<!--Widgets style sheet-->
		<link rel="stylesheet" href="lib/jqwidgets/styles/jqx.base.css" type="text/css" />

		<!--Font-->
		<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
		<!--Icon-->
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
		rel="stylesheet">

		<!-- JS Inclusions -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js"></script>


		<!--Extra stylesheets-->
		<?php
		if (isset($stylesheets)) {
			for ($i = 0; $i < count($stylesheets); $i++) {
				echo("<link href=css/".$stylesheets[$i]." rel=\"stylesheet\" type=\"text/css\">" );
			}
		}
		?>

		<!--Favicon-->
		<link rel="shortcut icon" type="image/png" href="img/favicon.png">
	</head>

	<?php 
		// Include index page contents
		include ('html/login.html');
		
		// Include scripts
		$scripts = array("index_script.js");
	?>
	
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="js/main.js" rel="javascript" type="text/javascript"></script>
	
	<!--Extra scripts-->
	<?php
	// e.g. $scripts = array("script1.js")
	if (isset($scripts)) {
		for ($i = 0; $i < count($scripts); $i++) {
			
			echo("<script src=js/".$scripts[$i]." rel=\"javascript\" type=\"text/javascript\"></script>");
			
		}
	}
	?>
</html>
