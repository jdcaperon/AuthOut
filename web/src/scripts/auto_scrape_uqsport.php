#!/usr/bin/env php
<?php
    if (!isset($_SESSION)){
        //if we're calling this script from a webpage
        include('/var/www/htdocs/php/database_connect.php');
        require('/var/www/htdocs/libs/simplehtmldom_1_5/simple_html_dom.php');
    }else{
        //if cron job
        include('../php/database_connect.php');
        require('../libs/simplehtmldom_1_5/simple_html_dom.php');
    }

	$db = new DatabaseConnection();

	function insert_game( $home, $away, $formatted_date, $time, $location, $team_name, $db, $home_score = null, $away_score = null){
        $teams = array("Netty Gals", "Sweet Potatoes", "The Spring Ligaments", "Netfall", "Don&#39;t Forget The Balls", "Nets Top Models", "Physibros", "Holes and Poles");

        // Iterate over allowed teams
        foreach ($teams as $team) {
            // Find the team we want
	        if ($team_name == $team) {
                // Get team ID
                $result = $db->prepared_query("SELECT `team_id` FROM `team` WHERE team_name=?", "s", $team);
                $teamID = $result->fetch_array(MYSQLI_NUM)[0];
                echo "Team ID: ".$teamID."\r\n";

                // Get game ID
                $result = $db->prepared_query("SELECT `game_id` FROM `game` WHERE `date`=? AND `time`=? AND `court_location`=? AND `team_id`=?", "sssi", $formatted_date, $time, $location, $teamID);
                // Game not in table - add it
                if ($result->num_rows == 0) {
                    echo "Adding...\r\n";
                    $db->prepared_query("INSERT INTO `game` (`home_team`, `away_team`, `date`,`time`, `court_location`, `team_id`, `home_score`, `away_score`) VALUES (?,?,?,?,?,?,?,?)", "sssssiii",  $home, $away, $formatted_date, $time, $location, $teamID, $home_score, $away_score);
                    echo "Added...\r\n";
                // Game already in table - update it
                } else {
                    echo "Updating...\r\n";
                    $gameID = $result->fetch_array(MYSQLI_NUM)[0];
	                $db->prepared_query("UPDATE `game` SET `home_team`=?, `away_team`=?, `date`=?, `time`=?, `court_location`=?, `home_score`=?, `away_score`=? WHERE `game_id`=?", "sssssiii",  $home, $away, $formatted_date, $time, $location, $home_score, $away_score, $gameID);
	                echo "Updated...\r\n";
                }

            }

        }

    }



	$dates = array("2018-03-14", "2018-03-21", "2018-03-28", "2018-04-18", "2018-05-02", "2018-05-09", "2018-05-16", "2018-05-23","2018-05-30","2018-06-06");

	for($date = 0; $date < sizeof($dates); $date++ ) {

	    $html = file_get_html("https://teams.uqsport.com.au/Team/Round/417/2?Date=".$dates[$date]);


	    $rows = $html->find('.table-container tbody tr');

	    //loop over all rows in table (skip first header)
	    for($row = 1; $row < sizeof($rows); $row++){
	        $tds = $rows[$row]->find('td, th');
	        $found = 0;

			//loop over all entries in row
	        for ($td = 0 ; $td < sizeof($tds); $td++){
	            $element = $tds[$td];
	            if(strpos($element, "<a href")!= 0){
	            	// Get the team associated to the game
	            	$team_name = $element->plaintext;

					$formatted_date =$dates[$date];
			        $time = $tds[0]->plaintext;
			        $location = $tds[1]->plaintext;
			        $home = $tds[2]->plaintext;

			        if(sizeof($tds) == 7 ||sizeof($tds) == 8 ){
			        	//game already played
			            $home_score = $tds[3]->plaintext;
			            $away = $tds[5]->plaintext;
			            $away_score = $tds[6]->plaintext;
			            insert_game($home, $away, $formatted_date, $time, $location, $team_name, $db, $home_score, $away_score);
					} else if (sizeof($tds) == 5 || sizeof($tds) == 6){
			        	//no scores to insert (not finals game)
			        	$away = $tds[4]->plaintext;
			            insert_game($home, $away, $formatted_date, $time, $location, $team_name, $db);
					}
				}
			}
	    }
	}

	$db->disconnect();

?>
