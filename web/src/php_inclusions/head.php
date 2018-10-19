<?php
    $isIndex = strpos($_SERVER["PHP_SELF"], "index.php"); //True if index

    // Set timezone to GMT+10
    date_default_timezone_set ("Australia/Brisbane");

    //if not signed in, redirect to index.php
	//TODO: uncomment when signin done
    // if(!isset($_SESSION['email'])){
    //     if (!$isIndex){
    //         header('Location: index.php');
    //         exit();
    //     }
    // }
?>

<head>
	<title>AuthOut</title>
	<meta charset="utf-8" name="description" content="A web-application to facilitate the signing in/out process in child care centres ."/>

	<!--Bootstrap-->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link href="https://fonts.googleapis.com/css?family=Alex+Brush|Cookie|Lobster|Playball|Tangerine" rel="stylesheet">

	<!--Main stylesheet-->
	<link href="css/main.css" rel="stylesheet" type="text/css">

	<!--Widgets style sheet-->
	<link rel="stylesheet" href="lib/jqwidgets/styles/jqx.base.css" type="text/css" />

	<!--Font-->
	<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
	



	<!--Extra stylesheets-->
	<?php
	if (isset($stylesheets)) {
		for ($i = 0; $i < count($stylesheets); $i++) {
			echo("<link href=css/".$stylesheets[$i]." rel=\"stylesheet\" type=\"text/css\">" );
		}
	}
	
	if (isset($externalSheets)) {
		for ($i = 0; $i < count($externalSheets); $i++) {
			echo("<link href=".$externalSheets[$i]." rel=\"stylesheet\" type=\"text/css\">" );;
		}
	}
	?>

	<!--Favicon-->
	<link rel="shortcut icon" type="image/png" href="img/favicon.png">
</head>
