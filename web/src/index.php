<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php 
		// Include head and desired style sheets
		$stylesheets = array("login.css");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/head.php';
		
		// Include index page contents
		include $_SERVER['DOCUMENT_ROOT'].'/html/login.html';
		
		// Include scripts
		$scripts = array("index_script.js");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/scripts.php';
	?>
</html>
