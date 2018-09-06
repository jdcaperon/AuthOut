<?php
    $stylesheets = array("css/index_style.css");
    include('php/head.php');
    

?>
    <body>
        <div class="page_content">
        	<div id="sign-in" class="login-screen">
            	<form>
            		<h2>Please sign in:</h2>

            		<ul id ="sign-in-form" class="account-form">
                		<li>
                			<label for="email">Email:</label>
            				<span><input type="text" placeholder="abcd@domain.com" name="email" data-toggle="tooltip" data-placement="right" data-trigger="manual"></span>
            			</li>

        				<li>
                			<label for="password">Password:</label>
                			<span><input type="password" placeholder="password" name="password" data-toggle="tooltip" data-placement="right" data-trigger="manual"></span>
                		</li>
        			</ul>

        			<p id="sign-in-error" class="form-error"></p>

        			<button type="submit"  class="btn btn-light">Sign in</button>
            	</form>

            	<p>New here? You might want to <a href="#" id="sign-up-buttton">Sign Up</a> first.</p>
        	</div>

        	<div id="sign-up" class="login-screen">
            	<form>
            		<h2>Create an account:</h2>

            		<ul id ="sign-up-form" class="account-form">
            			<li>
                			<label for="first-name">First name:</label>
            				<span><input type="text" placeholder="Jane" name="first-name" data-toggle="tooltip" data-placement="right" data-trigger="manual"></span>
            			</li>

            			<li>
                			<label for="first-name">Last name:</label>
            				<span><input type="text" placeholder="Doe" name="last-name" data-toggle="tooltip" data-placement="right" data-trigger="manual"></span>
            			</li>

                		<li>
                			<label for="email">Email:</label>
            				<span><input type="text" placeholder="abcd@domain.com" name="email" data-toggle="tooltip" data-placement="right" data-trigger="manual"></span>
            			</li>

        				<li>
                			<label for="password">Choose Password:</label>
                			<span><input type="password" placeholder="password" name="password" data-toggle="tooltip" data-placement="right" data-trigger="manual"></span>
                		</li>

                		<li>
                			<label for="confirm-password">Confirm Password:</label>
                			<span><input type="password" placeholder="password" name="confirm-password" data-toggle="tooltip" data-placement="right" data-trigger="manual" ></span>
                		</li>
        			</ul>

        			<p id="sign-up-error" class="form-error"></p>

        			<button type="submit" class="btn btn-light">Sign up</button>
            	</form>


            	<p>Already have an account? Please <a href="#" id="sign-in-buttton">Sign In</a>.</p>
        	</div>
        </div>

        <?php include 'php/footer_scripts.php'?>
        <script src="js/index_script.js" rel="javascript" type="text/javascript"></script>

    </body>
</html>
