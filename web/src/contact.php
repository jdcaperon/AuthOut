<?php session_start() ?>
<!DOCTYPE html>
<html lang="en">
	<?php
		$pageName = "Contact";
		$stylesheets = array();
		include_once ('php_inclusions/head.php');
	?>

	<body>
		<?php include ('php_inclusions/nav.php'); ?>
		
		<div class="content">
			<div class="row row-override" id="contact-greeting">
				<div class="col">
					<div class="box-outer">
						<div class="box-inner">
							<p>We'd love to hear from you! Use this form to send us your questions and comments</p>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row row-override"  id="contact-info">
				<div class="col-9" class="contact-info">
					<div class="box-outer" style="padding-top: 0;">
						<div class="box-inner">
												<form class="form-horizontal" role="form" method="post" action="index.php">
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="name" name="name" placeholder="First & Last Name" value="">
							</div>
						</div>
						<div class="form-group">
							<label for="email" class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<input type="email" class="form-control" id="email" name="email" placeholder="example@domain.com" value="">
							</div>
						</div>
						<div class="form-group">
							<label for="message" class="col-sm-2 control-label">Message</label>
							<div class="col-sm-10">
								<textarea class="form-control" rows="4" name="message"></textarea>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-10 col-sm-offset-2">
								<input id="submit" name="submit" type="submit" value="Send" class="rounded_button purple_button">
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
						<img  src="img/rp_logo.png" alt="3CS Logo" id="logo"/>
						<img  src="img/3cs.png" alt="3CS Logo" id="logo"/>
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
