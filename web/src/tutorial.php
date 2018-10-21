<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Tutorial";
		$stylesheets = array("tutorial_style.css");
		include_once ('php_inclusions/head.php');

	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>

		<div class="content">
			<div class="row row-override">
				<div class="col-4">
					<div class="box-outer" style="padding-right: 0">
						<div class="box-inner">

								<p class="no-bottom-margin">We've kept things as </p><b>simple as possible</b><p  class="no-top-margin"> to make your life easy.</p>
								<a href="contact.php"><span data-feather="help-circle" id="help"></span>
								<p>Contact Us </a> with any questions.</p>

						</div>
					</div>
				</div>

				<div class="col">
					<div class="box-outer">
						<div class="box-inner">
							<p class="no-margin">Site Overview</p>
							<img  src="img/tutorial.png" alt="Website tutorial" id="tute-image"/>
						</div>
					</div>
				</div>
			</div>
		</div>



		<?php
			$scripts = array();
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
