<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php 
		$pageName = "Statistics";
		//$stylesheets = array("stats_style.css");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/head.php';
		
	?>
	
	<body>
		<?php include $_SERVER['DOCUMENT_ROOT'].'/html/nav.html'; ?>
		
		<p style="padding-top: 120px"><?php echo $pageName ?> contnet here!</p>
	</body>
	
	<?php 
		$scripts = array("stats_script.js");
		include_once $_SERVER['DOCUMENT_ROOT'].'/php/scripts.php';
	?>
</html>