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
				<div class = "jumbotron" style="height:100%;">
					<canvas id="Weekly"></canvas>
				</div>
			</div>
			<div id="graph2" class="col">
				<div class = "jumbotron" style="height:100%;">
					<canvas id="Daily"></canvas>
				</div>
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
	
	<script>
			var ctx = document.getElementById("Weekly").getContext('2d');
			var myChart = new Chart(ctx, {
			    type: 'bar',
			    data: {
			        labels: ["White", "Orange", "Blue", "Pink"],
			        datasets: [{
			            label: 'Clicks Per Week',
			            data: [12, 19, 3, 5],
			            backgroundColor: [
			                '#F2F3F1',
			                '#DCA726',
			                '#03BABD',
			                '#BC72AF'
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
					responsive:true,
					maintainAspectRatio: false,
			        scales: {
			            yAxes: [{
			                ticks: {
			                    beginAtZero:true
			                }
			            }]
			        }
			    }
			});
			
			var ctx2 = document.getElementById("Daily").getContext('2d');
			var myChart2 = new Chart(ctx2, {
			    type: 'bar',
			    data: {
			        labels: ["White", "Orange", "Blue", "Pink"],
			        datasets: [{
			            label: 'Clicks Per Week',
			            data: [12, 19, 3, 5],
			            backgroundColor: [
			                '#F2F3F1',
			                '#DCA726',
			                '#03BABD',
			                '#BC72AF'
			            ],
			            borderWidth: 1
			        }]
			    },
			    options: {
					responsive:true,
					maintainAspectRatio: false,
			        scales: {
			            yAxes: [{
			                ticks: {
			                    beginAtZero:true
			                }
			            }]
			        }
			    }
			});
	</script>
	
</html>
			
			

