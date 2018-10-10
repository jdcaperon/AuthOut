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
				<h2 class="graph_title">Graph 1 Title</h2>
				<div class = "jumbotron" style="height:100%;">
					<canvas id="Weekly"></canvas>
				</div>
			</div>
			<div id="graph2" class="col">
				<h2 class="graph_title">Graph 2 Title</h2>
				<div class = "jumbotron" style="height:100%;">
					<canvas id="Daily"></canvas>
				</div>
			</div>
			<div id="table" class="col-2">
				<div  class="jumbotron">
					<table id="statistics-table" width="100%" height="100%">
						<tr>
							<td>Statistic 1</td>
							<td>##</td>
						</tr>
						<tr>
							<td>Statistic 2</td>
							<td>##</td>
						</tr>
						<tr>
							<td>Statistic 3</td>
							<td>##</td>
						</tr>
						<tr>
							<td>Statistic 4</td>
							<td>##</td>
						</tr>
						<tr>
							<td>Statistic 5</td>
							<td>##</td>
						</tr>
						<tr>
							<td>Statistic 6</td>
							<td>##</td>
						</tr>
						<tr>
							<td>Statistic 7</td>
							<td>##</td>
						</tr>
						<tr>
							<td>Statistic 8</td>
							<td>##</td>
						</tr>
						<tr>
							<td>Statistic 9</td>
							<td>##</td>
						</tr>
						<tr>
							<td>Statistic 10</td>
							<td>##</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</body>
	
	<?php 
		$scripts = array("home_script.js");
		include_once ('scripts.php');
	?>
	
</html>
			
			

