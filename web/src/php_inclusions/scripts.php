<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- Boostrap -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<!--Chart.js, http://www.chartjs.org/ authors: https://github.com/chartjs/Chart.js/graphs/contributors -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js"></script>

<!-- MDBootstrap Datatables  -->
<script type="text/javascript" src="lib/MDB/js/addons/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.flash.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.print.min.js"></script>

<!--Calendar-->
<script src="lib/dist/js/datepicker.min.js"></script>
<script src="lib/dist/js/i18n/datepicker.en.js"></script>

<!-- Loading overlay, https://gasparesganga.com/labs/jquery-loading-overlay/ -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/gasparesganga-jquery-loading-overlay@2.1.6/dist/loadingoverlay.min.js"></script>

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

<!-- Multiple Select http://wenzhixin.net.cn/p/multiple-select -->
<script src="lib/multiple-select-master/multiple-select.js"></script>

<!-- Custom scripts  -->
<script src="js/main.js" rel="javascript" type="text/javascript"></script>

<!--Extra scripts-->
<?php
	if (isset($scripts)) {
		for ($i = 0; $i < count($scripts); $i++) {
			echo("<script src=js/".$scripts[$i]." rel=\"javascript\" type=\"text/javascript\"></script>");
		}
	}

?>
