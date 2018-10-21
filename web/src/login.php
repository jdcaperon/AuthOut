<?php session_start() ?>
<?php 

	$username = $_POST['username']
	$password = $_POST['password']
	$remote_url = 'https://deco3801.wisebaldone.com/api/login'

	$opts = array(
		'http'=>array(
			'method'=>'GET',
			'header'=>"Authorization: Basic" . base64_encode("$username:$password")
		)
	)

	// do a get request, if we get a 200 status then this was valid and we should 
	// store the name that comes back at us from the json object { "name": "Tom Richardson" }. We should also store the base64_encode(....) in the session variables for rerouting the entry requests through php.

	// other stuff todo, make a entry.php and have it when it gets a get to do the get request jordans done client side and pass the data back to the caller. will need to set the header argument on that get request though.

	// more other stuff todo, will have todo the same with the new query stuff.

?>
