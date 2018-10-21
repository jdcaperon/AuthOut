<?php session_start() ?>
<?php 

	$username = $_POST['username'];
	$password = $_POST['password'];
	$remote_url = 'https://deco3801.wisebaldone.com/api/login';
	
	// Determine authentication
	$auth = base64_encode("$username:$password");
	
	// Setup request
	$curl = curl_init();	
	curl_setopt_array($curl, array(
		CURLOPT_RETURNTRANSFER => true,
		CURLOPT_POST => false,
		CURLOPT_URL => $remote_url,
		CURLOPT_USERAGENT => 'User Agent X',
		CURLOPT_HTTPHEADER => array(
            "Authorization: Basic $auth",
		)
	));
	
	// Execute request
	$response = curl_exec($curl);
	// Get HTTP status
	$status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
	
	// User is authorised
	if ($status == 200) {
		echo $response;
		
		// Ger variables from response
		$arrayResponse = json_decode($response, true);
		$name = $arrayResponse['name'];
		$email = $arrayResponse['email'];

		// Save session varibles
		$_SESSION['name'] = $name;
		$_SESSION['email'] = $email;
		$_SESSION['auth'] = $auth;
	// Not authorised
	} else {
		// Return empty JSON object
		echo json_encode(json_decode("{}"));
	}
	
	curl_close($curl);

	// other stuff todo, make a entry.php and have it when it gets a get to do the get request jordans done client side and pass the data back to the caller. will need to set the header argument on that get request though.

	// more other stuff todo, will have todo the same with the new query stuff.

?>
