<?php    
	function get_rsvp_reminders($teamID, $gameID, $db) {
	    // Array of players to send emails to
	    $toSend = array();
	    
	    $playerDetails = get_user_emails($teamID, $db);
	    
	    // Check RSVP status for each player
	    foreach ($playerDetails as $details) {
            $result = $db->prepared_query("SELECT `status` FROM `rsvp` WHERE `game_id`=? AND `role_id`=?", "ii", $gameID, $details[1]);
            $status = $result->fetch_array(MYSQLI_NUM)[0];
	        
	        // Send the email
	        if ($status === null) {
	           array_push($toSend, [$details[0], $details[1]]);
	        }
	        
	    }
	    
	    return $toSend;
	    
	}
    
	// Get the user eamils and role IDs for each member of the team, return in an array
    function get_user_emails($teamID, $db) {
        $playerDetails = array();
        
        $result = $db->prepared_query("SELECT `user_email`, `role_id` FROM user_roles WHERE `team_id` = ? AND (`role_type`='player' OR `role_type`='player_manager')", "i", $teamID);
        
        while ($row = $result->fetch_array(MYSQLI_NUM)) {
            array_push($playerDetails, [$row[0], $row[1]]);
        }
        
        return $playerDetails;
    }
?>
