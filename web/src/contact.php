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

		<div class="content container">
			<div class="row row-override" id="welcome-row">
				<div class="col">
					<div class="box-outer">
						<div class="box-inner" id="welcome-message">
							<p>We'd love to hear from you! Get in touch with your questions and comments</p>
						</div>
					</div>
				</div>
			</div>

			<div class="row row-override"  id="info-row">
				<div class="col-8" class="contact-info">
					<div class="box-outer" style="padding-top: 0;">
						<div class="box-inner">
							<form class="form-horizontal" enctype="text/plain" role="form" action="mailto:UQrocketpotatoes@gmail.com?subject=Authout Admin Enquiry" method="GET" action="index.php">
								<div class="form-group">
									<label for="message" class="col-sm-2 control-label">Message</label>
									<div class="col-sm-10">
										<textarea class="form-control" rows="4" name="body"></textarea>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-10 col-sm-offset-2">
										<input id="submit" name="submit" type="submit" value="Send" class="rounded-button purple-button">
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>

				<div class="col">
					<div class="box-outer" style="padding-left: 0; padding-top: 0;">
						<div class="box-inner">
						<p>AuthOut proudly built by Rocket Potatoes in collaboration with 3CS Software. <p>
						<img  src="img/rp_logo.png" alt="3CS Logo" id="rplogo"/>
						<img  src="img/3cs.png" alt="3CS Logo" id="threecslogo"/>
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
