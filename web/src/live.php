<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Live Monitor";
		$stylesheets = array("live_style.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>
		<div class="content">
			<div class="row row-override">
				<div class="col-4">
					<div class="box-outer" style="padding-right: 0">
						<div class="box-inner">
							<div class="data-div">
								<canvas id="attendance-chart"></canvas>
							</div>
							<div class="data-div">
								<h3>12</h3>
								<p>Signed in</p>
							</div>
							<div class="data-div">
								<h3>19</h3>
								<p>Signed out</p>
							</div>

						</div>
					</div>
				</div>

				<div class="col-8">
					<div class="box-outer">
						<div class="box-inner" id="table-box">
						Today's Actvity
						<table id="dtBasicExample" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
							  <thead>
							    <tr>
							      <th class="th-sm">Time
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							      <th class="th-sm">Parent
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							      <th class="th-sm">Child
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							      <th class="th-sm">Action
							        <i class="fa fa-sort float-right" aria-hidden="true"></i>
							      </th>
							      </th>
							    </tr>
							  </thead>
							  <tbody>
							    <tr>
							      <td>Tiger Nixon</td>
							      <td>System Architect</td>
							      <td>Edinburgh</td>
							      <td>61</td>
							    </tr>
							    <tr>
							      <td>Garrett Winters</td>
							      <td>Accountant</td>
							      <td>Tokyo</td>
							      <td>63</td>
							    </tr>
							    <tr>
							      <td>Ashton Cox</td>
							      <td>Junior Technical Author</td>
							      <td>San Francisco</td>
							      <td>66</td>
							    </tr>
							    <tr>
							      <td>Cedric Kelly</td>
							      <td>Senior Javascript Developer</td>
							      <td>Edinburgh</td>
							      <td>22</td>
							    </tr>
							    <tr>
							      <td>Airi Satou</td>
							      <td>Accountant</td>
							      <td>Tokyo</td>
							      <td>33</td>
							    </tr>
								<tr>
							      <td>Jennifer Chang</td>
							      <td>Regional Director</td>
							      <td>Singapore</td>
							      <td>28</td>
							    </tr>
							    <tr>
							      <td>Brenden Wagner</td>
							      <td>Software Engineer</td>
							      <td>San Francisco</td>
							      <td>28</td>
							    </tr>
							    <tr>
							      <td>Fiona Green</td>
							      <td>Chief Operating Officer (COO)</td>
							      <td>San Francisco</td>
							      <td>48</td>
							    </tr>
							    <tr>
							      <td>Shou Itou</td>
							      <td>Regional Marketing</td>
							      <td>Tokyo</td>
							      <td>20</td>
							    </tr>
							    <tr>
							      <td>Jennifer Chang</td>
							      <td>Regional Director</td>
							      <td>Singapore</td>
							      <td>28</td>
							    </tr>
							    <tr>
							      <td>Brenden Wagner</td>
							      <td>Software Engineer</td>
							      <td>San Francisco</td>
							      <td>28</td>
							    </tr>
							    <tr>
							      <td>Fiona Green</td>
							      <td>Chief Operating Officer (COO)</td>
							      <td>San Francisco</td>
							      <td>48</td>
							    </tr>
							    <tr>
							      <td>Shou Itou</td>
							      <td>Regional Marketing</td>
							      <td>Tokyo</td>
							      <td>20</td>
							    </tr>
							  </tbody>

							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<?php
			$scripts = array("live_script.js");
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
