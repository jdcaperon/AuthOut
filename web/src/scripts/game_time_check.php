#!/usr/bin/env php

<?php
    include('/var/www/htdocs/php/common_functions.php');
    include('/var/www/htdocs/scripts/rsvp_email.php');
    //include('../php/common_functions.php');
    //include('rsvp_email.php');

	$db = new DatabaseConnection();

	date_default_timezone_set("Australia/Brisbane");

    $today = date("Y-m-d");
    $oneDay = 86400;

    // get all team IDs
    $teamIDs = get_team_ids($db);

    // For every team, find the next game and send emails
    foreach ($teamIDs as $teamID) {
        // Get the game ID of the next game
        $gameDetails = get_next_game($teamID, $db);
        $gameDate = $gameDetails[0];
        $formattedDate = date_format(date_create($gameDate),"D jS F");
        $gameID = $gameDetails[1];
        $time = $gameDetails[2];

        // Calculate time until next game
        $timeToGame = strtotime($gameDetails[0]) - strtotime($today);

        // Check how long it is until the game
        if ($timeToGame <= ($oneDay * 7) && $timeToGame > $oneDay * 6) {
            // Send manager reminder email (testing purposes only)
            send_manager_email($teamID, $gameID, $formattedDate, $time, $db);
            
            echo "7 days to game<br>";
        } else if ($timeToGame <= ($oneDay * 6) && $timeToGame > $oneDay * 5) {
            echo "6 days to game<br>";
        } else if ($timeToGame <= ($oneDay * 5) && $timeToGame > $oneDay * 4) {
            echo "5 days to game<br>";
        } else if ($timeToGame <= ($oneDay * 4) && $timeToGame > $oneDay * 3) {
            echo "4 days to game<br>";
        } else if ($timeToGame <= ($oneDay * 3) && $timeToGame > $oneDay * 2) {
            echo "3 days to game<br>";

            // Get players that need to be sent an email
            $toSend = get_rsvp_reminders($teamID, $gameID, $db);

            // Send an email for each user
            foreach ($toSend as $user) {
                $email = $user[0];
                $roleID = $user[1];

                // Get the name of the user
                $names = get_user_names($email, $db);
                $firstName = $names[0];
                $lastName = $names[1];
                $fullName = $firstName." ".$lastName;

                $subject = "Please RSVP to your game on $formattedDate.";
                $body = "Don't forget to RSVP to your upcoming game! Please visit <a href='http://infs3202-050e474d.uqcloud.net'>Coachless</a> to sign in and RSVP.";
                $altBody = "Don't forget to RSVP to your upcoming game! Please visit Coachless (http://infs3202-050e474d.uqcloud.net) to sign in and RSVP.";

                send_email($email, $fullName, $subject, $body, $altBody);
            }
        } else if ($timeToGame <= ($oneDay * 2) && $timeToGame > $oneDay) {
            echo "2 days to game<br>";
        } else if ($timeToGame <= $oneDay) {
            // Send manager reminder email (testing purposes only)
            send_manager_email($teamID, $gameID, $formattedDate, $time, $db);

        	echo "1 day to game";

        }
        
        $db->disconnect();

    }
    
    function send_manager_email($teamID, $gameID, $formattedDate, $time, $db) {
        // Get details for the email
        $emails = $db->prepared_query("SELECT `user_email` FROM `user_roles` WHERE `team_id`=? AND (`role_type`='manager' OR `role_type`='player_manager')", "i", $teamID);
        $teamNames = $db->prepared_query("SELECT `team_name` FROM `team` WHERE `team_id`=?", "i", $teamID);
        $furtherDetails = $db->prepared_query("SELECT `home_team`, `away_team`, `court_location` FROM `game` WHERE `team_id`=? AND `game_id`=?", "ii", $teamID, $gameID);
        $statusGoing = $db->prepared_query("SELECT `status` FROM `rsvp` WHERE `game_id`=? AND status=1", "i", $gameID);
        $statusNotGoing = $db->prepared_query("SELECT `status` FROM `rsvp` WHERE `game_id`=? AND status=0", "i", $gameID);
                
        // Pull the details from the queries
        $managerEmail = $emails->fetch_array(MYSQLI_NUM)[0];
        $teamName = $teamNames->fetch_array(MYSQLI_NUM)[0];
        $furtherDetails = $furtherDetails->fetch_array(MYSQLI_NUM);
        $homeTeam = $furtherDetails[0];
        $awayTeam = $furtherDetails[1];
        $court = $furtherDetails[2];
        $going = $statusGoing->num_rows;
        $notGoing = $statusNotGoing->num_rows;
        
        // Get the opponent's name
        if ($teamName == $homeTeam) {
            $against = $awayTeam;
        } else {
            $against = $homeTeam;
        }
        
        // Construct email
        $names = get_user_names($managerEmail, $db);
        $firstName = $names[0];
        $lastName = $names[1];
        $fullName = $firstName." ".$lastName;
        
        $subject = "Game details for $formattedDate $time";
        $body = "
            	Hey $firstName,<br>
            	<br>
            	Your next game is against $against at $time on $formattedDate, $court.<br>
            	You currently have <strong>$going</strong> players going and <strong>$notGoing</strong> who have said they can't make it.<br>
            	Please sign in to <a href='http://infs3202-050e474d.uqcloud.net'>Coachless</a> to check the lineup and further details.<br>
            	<br>
            	Warm regards,<br>
            	<br>
            	The Coachless team.
            ";
        $altBody = "
            	Hey $firstName,
            	
            	Your next game is against $against at $time on $formattedDate, $court.
            	You currently have $going players going and $notGoing who have said they can't make it.
            	Please sign in to Coachless (http://infs3202-050e474d.uqcloud.net) to check the lineup and further details.
            	
            	Warm regards,
            	
            	The Coachless team.
            ";
        
        echo "$body<br>";
        echo "$managerEmail<br>";
        
        // Send email
        send_email($managerEmail, $fullName, $subject, $body, $altBody);
    }

?>
