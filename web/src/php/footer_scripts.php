<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<?php
$mapsAPIKey = "AIzaSyB4BQsFibXn3CW33vNlggzbj3QbJdHgxFc";

	// Map include is different for game details page because it needs to callback function
	if (strpos($_SERVER['REQUEST_URI'], 'game_details.php') === false) {
		echo "<script src='https://maps.googleapis.com/maps/api/js?key=$mapsAPIKey' async defer></script>";
	} else {
		echo "<script src='https://maps.googleapis.com/maps/api/js?key=$mapsAPIKey&callback=initMap' async defer></script>";
	}

?>
<script type="text/javascript" src="libs/jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="libs/jqwidgets/jqxnotification.js"></script>
<script src='libs/spectrum/spectrum.js'></script>
<script src="js/main.js" rel="javascript" type="text/javascript"></script>
<script type="text/javascript" src="js/account_setup_script.js"></script>
