<!DOCTYPE html>
<html lang="en">
	<?php 
		$pageName = "Help";
		//$stylesheets = array("help_style.css");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/head.php';
		include $_SERVER['DOCUMENT_ROOT'].'/html/nav.html';
		
	?>
	
	<body>
		<p style="padding-top: 120px"><?php echo $pageName ?> contnet here!</p>
	</body>
	
	<?php 
		//$scripts = array("help_script.js");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/scripts.php';
	?>
</html>