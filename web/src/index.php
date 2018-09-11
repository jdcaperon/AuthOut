<?php
    $stylesheets = array("css/index_style.css");
    include('php/head.php');

?>
    <body>
		<header>
			<img src="img/placeholder.png" alt="logo" id="login-logo">
			<h1>Login</h1>
		</header>
        <div class="login_window">			
			<i id = "login-icon" class="material-icons">account_circle</i>
			
			<form id="login-form" class="container">
				<span><label for="username">Username:</label><input type="text" name="username" id="username"></span><br>
				<span><label for="password">Password:</label><input type="text" name="password" id="password"></span>
			</form>
			
			<button type="button" class="rounded_button blue_button">LOGIN</button>
			<button type="button" class="rounded_button purple_button">DON'T HAVE AN ACCOUNT?</button>
		</div>
        <?php include 'php/footer_scripts.php'?>
        <script src="js/index_script.js" rel="javascript" type="text/javascript"></script>

    </body>
</html>
