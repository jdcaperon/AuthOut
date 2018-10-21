<?php
	include('database_connect.php');
	
	// Make all dates the same
	date_default_timezone_set("Australia/Brisbane");
	
	// Counts from each week
	$counts = [];
	
	$db = new DatabaseConnection();
	
	// Get the dates
	$date = date("Y-m-d");
	$oneWeek = date("Y-m-d", time() + (60 * 60 * 24 * - 7) );
	$twoWeek = date("Y-m-d", time() + (60 * 60 * 24 * - (7 * 2)) );
	$threeWeek = date("Y-m-d", time() + (60 * 60 * 24 * - (7 * 3)) );
	$fourWeek = date("Y-m-d", time() + (60 * 60 * 24 * - (7 * 4)) );
	
	// Last week
	$result = $db->prepared_query('SELECT COUNT(*) FROM `views` WHERE `date` BETWEEN ? AND ?', "ss", $oneWeek, $date);
	$result = $result->fetch_assoc();
	$counts[0] = $result['COUNT(*)'];
	
	// Two weeks ago
	$result = $db->prepared_query('SELECT COUNT(*) FROM `views` WHERE `date` BETWEEN ? AND ?', "ss", $twoWeek, $oneWeek);
	$result = $result->fetch_assoc();
	$counts[1] = $result['COUNT(*)'];
	
	// Three weeks ago
	$result = $db->prepared_query('SELECT COUNT(*) FROM `views` WHERE `date` BETWEEN ? AND ?', "ss", $threeWeek, $twoWeek);
	$result = $result->fetch_assoc();
	$counts[2] = $result['COUNT(*)'];
	
	// Four weeks ago
	$result = $db->prepared_query('SELECT COUNT(*) FROM `views` WHERE `date` BETWEEN ? AND ?', "ss", $fourWeek, $threeWeek);
	$result = $result->fetch_assoc();
	$counts[3] = $result['COUNT(*)'];
	
	// Count of "all" updates
	$result = $db->query("SELECT COUNT(*) FROM `mailing_list` WHERE `updates` = 'all'");
	$result = $result->fetch_assoc();
	$counts[4] = $result['COUNT(*)'];
	
	// Count of "monthly" updates
	$result = $db->query("SELECT COUNT(*) FROM `mailing_list` WHERE `updates` = 'monthly'");
	$result = $result->fetch_assoc();
	$counts[5] = $result['COUNT(*)'];
	
	// Count of "announcments" updates
	$result = $db->query("SELECT COUNT(*) FROM `mailing_list` WHERE `updates` = 'announcements'");
	$result = $result->fetch_assoc();
	$counts[6] = $result['COUNT(*)'];
	
	// Count of early bird offers
	$result = $db->query("SELECT COUNT(*) FROM `mailing_list` WHERE `early_bird` = 1");
	$result = $result->fetch_assoc();
	$counts[7] = $result['COUNT(*)'];
	
	// Count of no early bird offers
	$result = $db->query("SELECT COUNT(*) FROM `mailing_list` WHERE `early_bird` = 0");
	$result = $result->fetch_assoc();
	$counts[8] = $result['COUNT(*)'];
	
	echo json_encode($counts);
	
	$db->disconnect();
?>