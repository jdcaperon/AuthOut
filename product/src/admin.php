<?php
    include('php/head.php');
	include('php/nav_admin.php');
?>
    <body>
	<div id="admin-content"	>
		<h3>Mailing list subsciptions: 50</h3>
		<div class="container">
			<div class="row">
				<div class="col-sm-4">
					<div id="type">
						<canvas id="pieChart" width="400" height="400"></canvas>
						<!-- <table class="table">
						  <thead class ="thead-dark">
						    <tr>
						      <th scope="col">All Updates</th>
						      <th scope="col">Monthly Newsletter</th>
						      <th scope="col">Product Announcements</th>
							  <th scope="col">Total</th>
						    </tr>
						  </thead>
						  <tbody>
						    <tr>
						      <th scope="row">1</th>
						      <td>20</td>
						      <td>30</td>
							  <td>51</td>
						    </tr>
						  </tbody>
						</table> -->
					</div>

				</div>
				<div class="col-sm-8">
					<div id="chart">
					<canvas id="Weekly" width="150" height="100"></canvas>
					</div>
				</div>
			</div>
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

			var ctx = document.getElementById("pieChart").getContext('2d');
			var myChart = new Chart(ctx, {
			    type: 'pie',
			    data: {
			        labels: ["All Updates", "Monthly Newsletter", "Product Announcements"],
			        datasets: [{
			            label: 'Clicks Per Week',
			            data: [12, 19, 3],
			            backgroundColor: [
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
