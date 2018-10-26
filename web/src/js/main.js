$(document).ready(function() {
	// Signout button click
   $('#signout').on('click', function(event) {
	   event.preventDefault();
	   $.ajax({
		   url: "php_requests/sign-in.php",
		   data: {
			   "function": "signout"
		   }, type: "GET",
		   success: function(data){
			  window.location.href = "index.php";
		   }
	   });

   });

});
