<?php
    include('php/head.php');
	include('php/nav_admin.php');
?>
    <body>
	<div id="admin-content"	>
		<h3>Mailing list subsciptions: 51</h3>



		<canvas id="Weekly" width="300"></canvas>
		<small>Note that mailing list subscriptions are equivalent to purchases</small>
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

	</div>

        <?php include 'php/footer_scripts.php'?>

		<script src="//code.jquery.com/jquery-3.1.0.slim.min.js"></script>


    </body>
</html>
