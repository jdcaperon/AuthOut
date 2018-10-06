<?php
	include('database_connect.php');
	
	$db = new DatabaseConnection();
	
	$email = $_POST['email'];
	$name = $_POST['name'];
	$frequency = $_POST['frequency'];
	$earlyBird = $_POST['earlyBird'];
	
	$result = $db->prepared_query('INSERT INTO `mailing_list`(`email`, `name`, `updates`, `early_bird`) VALUES (?, ?, ?, ?)', "sssi", $email, $name, $frequency, $earlyBird);
	
	echo $result;
	
	$db->disconnect();
?>