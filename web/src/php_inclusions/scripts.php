<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="js/main.js" rel="javascript" type="text/javascript"></script>

<!-- Chart.js Inclusions -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js"></script>

<!-- MDBootstrap Datatables  -->
<script type="text/javascript" src="lib/MDB/js/addons/datatables.min.js"></script>


<!--jq widgest scripts-->
<script type="text/javascript" src="lib/jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="lib/jqwidgets/jqxdatetimeinput.js"></script>
<script type="text/javascript" src="lib/jqwidgets/jqxcalendar.js"></script>
<script type="text/javascript" src="lib/jqwidgets/jqxtooltip.js"></script>
<script type="text/javascript" src="lib/jqwidgets/globalization/globalize.js"></script>

<!-- Icons -->
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
	feather.replace()
</script>

<!--Extra scripts-->
<?php
	if (isset($scripts)) {
		for ($i = 0; $i < count($scripts); $i++) {
			echo("<script src=js/".$scripts[$i]." rel=\"javascript\" type=\"text/javascript\"></script>");
		}
	}

?>
