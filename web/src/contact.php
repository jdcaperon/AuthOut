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
		<div class="content container">
			<div class="row">
				<div class="col">

					<p>We'd love to hear from you! Use this form to send us your questions and comments</p>
				</div>

				<div class="col">
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
								<input id="submit" name="submit" type="submit" value="Send" class="btn btn-primary">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-10 col-sm-offset-2">

							</div>
						</div>
					</form>
				</div>
				<div class="col">
					<p>AuthOut proudly built by Rocket Potatoes in collaboration with 3CS Software. <p>
						<img  src="img/rp_logo.png" alt="3CS Logo" id="logo"/>
						<img  src="img/3cs.png" alt="3CS Logo" id="logo"/>
				</div>
			</div>

		</div>

		<?php
			$scripts = array();
			include_once ('php_inclusions/scripts.php');
		?>
	</body>


</html>
