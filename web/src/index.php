<?php
	$stylesheets = array("css/index_style.css");
    include('php/head.php');
?>
    <body>
		<header>
			<img src="img/placeholder.png" alt="logo" id="login-logo">
			<h1>AuthOut Admin Portal</h1>
		</header>
        <div id="login-window">
			<div id="login-content">
				<i id = "login-icon" class="material-icons text-center">account_circle</i>
				
				<form id="login-form" class="container clearfix">
					<div class="clearfix"><label for="username">Username:</label><input type="text" name="username" id="username"></div>
					<div class="clearfix"><label for="password">Password:</label><input type="text" name="password" id="password"></div>
				</form>
				
				<button type="button" id="login-button" class="rounded_button blue_button">LOGIN</button>
				<button type="button" class="rounded_button purple_button">DON'T HAVE AN ACCOUNT?</button>
			</div>
		</div>
        <?php include 'php/footer_scripts.php'?>
        <script src="js/index_script.js" rel="javascript" type="text/javascript"></script>

    </body>
</html>
