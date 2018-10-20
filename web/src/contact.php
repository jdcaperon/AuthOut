<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Contact";
		$stylesheets = array("contact.css");
		include_once ('php_inclusions/head.php');
	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>

		<div class="content">
			<div class="row row-override" id="welcome-row">
				<div class="col">
					<div class="box-outer">
						<div class="box-inner" id="welcome-message">
							<h3>We'd love to hear from you! </h3><h6>Get in touch with your questions and comments</h6>
						</div>
					</div>
				</div>
			</div>

			<div class="row row-override"  id="info-row">
				<div class="col-8" class="contact-info">
					<div class="box-outer" style="padding-top: 0;">

						<div class="box-inner">
							<div class="container" id="contact-details">
								<div class="row row-littlest">
									<h4>Old School? Send us a letter</h4><br>
								</div>
								<div class="row row-little">
									<address>
									  	General Purpose South Building (Building 78)<br>
									  	University of Queensland<br>
										QLD 4072<br>
									</address>
								</div>
								<div class="row">

									<form class="form" enctype="text/plain" role="form" action="mailto:UQrocketpotatoes@gmail.com?subject=Authout Admin Enquiry" method="GET">
										<h4>Alternatively, send us an email</h4>
										<div >
											<div class="form-group">
												<textarea class="form-control" name="body" placeholder="Write your message here"></textarea>
											</div>
										</div>

										<div class="form-group">
											<div>
												<input id="submit" name="submit" type="submit" value="Send" class="rounded-button purple-button">
											</div>
										</div>
									</form>
								</div>
							</div>

						</div>
					</div>
				</div>

				<div class="col">
					<div class="box-outer" style="padding-left: 0; padding-top: 0;">
						<div class="box-inner">

						<img  src="img/rp_logo.png" alt="3CS Logo" id="rplogo"/>
						<img  src="img/3cs.png" alt="3CS Logo" id="threecslogo"/>
						<p>AuthOut proudly built by Rocket Potatoes in collaboration with 3CS Software. <p>
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
