<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php 
		$pageName = "Live Monitor";
		$stylesheets = array("monitor_style.css");
		include_once ('head.php');
		
	?>
	
	<body>
		<?php include ('../html/nav.html'); ?>
		
		<div id="monitor-content" class="row">
			<div id="graph1" class="col">
				<!--Graph here-->
				<img src="../img/placeholder.png" style="width:100%; height:100%; border: 1px solid black">
			</div>
			
			<div id="graph2" class="col">
				<!--Graph here-->
				<img src="../img/placeholder.png" style="width:100%; height:100%; border: 1px solid black">
			</div>
		</div>
	</body>
	
	<?php 
		$scripts = array("monitor_script.js");
		include_once ('scripts.php');
	?>
</html>