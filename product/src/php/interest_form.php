<?php
	include('database_connect.php');
	
	$db = new DatabaseConnection();
	
	$email = $_POST['email'];
	$name = $_POST['name'];
	$frequency = $_POST['frequency'];
	$earlyBird = $_POST['earlyBird'];
	
	// Check if they have already submitted
	$result = $db->prepared_query('SELECT COUNT(*) FROM `mailing_list` WHERE `email` = ? AND `name` = ?', "ss", $email, $name);
	$result = $result->fetch_assoc()['COUNT(*)'];
	
	// Already submitted
	if ($result > 0) {
		$result = $db->prepared_query('UPDATE `mailing_list` SET `updates`=?,`early_bird`=? WHERE `email`=? AND `name`=?', "siss", $frequency, $earlyBird, $email, $name);
		echo 1;
	// Not submitted yet
	} else {
		$result = $db->prepared_query('INSERT INTO `mailing_list`(`email`, `name`, `updates`, `early_bird`) VALUES (?, ?, ?, ?)', "sssi", $email, $name, $frequency, $earlyBird);
		echo 0;
	}
	
	$db->disconnect();
?>