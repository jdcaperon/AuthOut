<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php 
		$pageName = "Live Monitor";
		//$stylesheets = array("monitor_style.css");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/head.php';
		include $_SERVER['DOCUMENT_ROOT'].'/html/nav.html';
		
	?>
	
	<body>
		<p style="padding-top: 120px"><?php echo $pageName ?> contnet here!</p>
	</body>
	
	<?php 
		//$scripts = array("monitor_script.js");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/scripts.php';
	?>
</html>