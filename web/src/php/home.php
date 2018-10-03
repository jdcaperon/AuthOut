<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php 
		$pageName = "Home";
		$stylesheets = array("home_style.css");
		include_once ('head.php');
		
	?>
	
	<body>
		<?php include ('../html/nav.html'); ?>
		
		<div id="home-content" class="row">
			<div id="graph1" class="col">
				<!--Graph here-->
				<img src="../img/placeholder.png" style="width:100%; height:100%; border:1px solid black">
			</div>
			<div id="graph2" class="col">
				<!--Graph here-->
				<img src="../img/placeholder.png" style="width:100%; height:100%; border:1px solid black">
			</div>
			<div id="table" class="col-2">
				<!--Table here-->
				<img src="../img/placeholder.png" style="width:100%; height:100%; border:1px solid black">
			</div>
		</div>
	</body>
	
	<?php 
		$scripts = array("home_script.js");
		include_once ('scripts.php');
	?>
</html>
			
			

