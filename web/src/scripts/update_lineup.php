#!/usr/bin/env php

<?php
    include('/var/www/htdocs/php/common_functions.php');
    //include('../php/common_functions.php');

    $db = new DatabaseConnection();

    date_default_timezone_set("Australia/Brisbane");

    // get all team IDs
    $teamIDs = get_team_ids($db);

    // For every team, update the lineup
    foreach ($teamIDs as $teamID) {
        // Get the game ID of the next game
        $gameDetails = get_next_game($teamID, $db);
        // Update the lineup
        update_lineup($teamID, $gameDetails[1], $db);
    }
    
    $db->disconnect();

    // Updates the lineup
	function update_lineup($teamID, $nextGame, $db) {
		// Array of role IDs for players who have rsvp'd
		$rsvpPlayers = array();
		// Multi-dimensional array where each element contains a role id followed by a list of preffered positions
		$preferredPositions = array();
		// A multi-dimensional array of quarters with role IDs in the order: GK, GD, WD, C, WA, GA, GS
		// eg: [
		//	[1, 3, 5, 4, 7, 2, 6],
		//	[1, 8, 5, 9, 7, 2, 6],
		//	[1, 5, 8, 4, 3, 2, 6],
		//	[9, 5, 5, 4, 3, 2, 1]
		//]
		$lineup = array(
			1 => array(),
			2 => array(),
			3 => array(),
			4 => array()
		);

		$result = $db->prepared_query("SELECT `role_id` FROM `user_roles` WHERE `team_id`=?", "i", $teamID);

		while ($row = $result->fetch_array(MYSQLI_NUM)) {
			$roleID = $row[0];

			$statusResult = $db->prepared_query("SELECT `status` FROM `rsvp` WHERE `game_id` =? AND `role_id`=?", "ii", $nextGame, $roleID);

			// Check if player has responded as going
			while ($status = $statusResult->fetch_array(MYSQLI_NUM)) {
				if ($status[0] == 1) {
					array_push($rsvpPlayers, $roleID);
				}

			}

		}

		// Enough players to calculate lineup
		if (sizeof($rsvpPlayers) >= 7) {
			// Add each players preferences to the preferences array
			foreach ($rsvpPlayers as $roleID) {
				$result = $db->prepared_query("SELECT * FROM `player_preferences` WHERE `role_id`=?", "i", $roleID);
				$row = $result->fetch_array(MYSQLI_NUM);
				array_push($preferredPositions, $row);
			}

			$lineup = calculate_lineup($preferredPositions, $db);
			add_to_database($lineup, $nextGame, $db);

		}

	}

	function add_to_database($lineup, $nextGame, $db) {
		// Determine if we are updating or inserting
		$result = $db->prepared_query("SELECT * FROM `lineup` WHERE `game_id`=?", "i", $nextGame);
		$numRows = $result->num_rows;

		// Add for each quarter
		for ($i = 0; $i < sizeof($lineup); $i++) {
			$quarter = $i + 1;
			for ($j = 0; $j < sizeof($lineup[$quarter]); $j++) {
				$position = get_position($j);
				$roleID = $lineup[$quarter][$j];

				// Already there - update
				if ($numRows) {
					$db->prepared_query("UPDATE `lineup` SET `position`=? WHERE `game_id`=? AND `quarter`=? AND `role_id`=?", "siii", $position, $nextGame, $quarter, $roleID);
				// Not there - insert
				} else {
					$db->prepared_query("INSERT INTO `lineup`(`game_id`, `quarter`, `position`, `role_id`) VALUES (?, ?, ?, ?)", "iisi", $nextGame, $quarter, $position, $roleID);
				}

			}

		}

	}

	// Calculates the preferred position for each player
	function calculate_player_position($playerPreference, &$quarterLineup, &$noPosition) {
		// Roll ID of the player
		$roleID = $playerPreference[0];
		// Index of the best position for the player
		$bestIndex = -1;
		// Tracks if player is already assigned a position
		$hasPosition = 0;

		// Calculate if player is already in the lineup
		foreach ($quarterLineup as $positionRoleID) {
			if ($positionRoleID == $roleID) {
				$hasPosition = 1;
			}

		}

		// Only calculate if the player doesn't already have a position
		if (!$hasPosition) {
			// Iterate over preferences
			for ($j = 0; $j < 3; $j++) {
				$index = get_position_index($playerPreference[$j+1]);

				// Set the index of the most suitable position for the player
				if ($quarterLineup[$index] == "" && $bestIndex == -1) {
					$bestIndex = $index;
				}

			}

			// Position found based on preferences
			if ($bestIndex != -1) {
				$quarterLineup[$bestIndex] = $roleID;
			// No position found for the player
			} else {
				array_push($noPosition, $playerPreference);
			}

		}

	}

	function calculate_lineup($preferredPositions, $db) {
		// Array of players without a position
		$noPosition = array();
		// Array of role IDs representing positions for each quarter
		$quarterLineup = ["", "", "", "", "", "", ""];
		// A multi-dimensional array of quarters with role IDs in the order: GK, GD, WD, C, WA, GA, GS
		// eg: [
		//	[1, 3, 5, 4, 7, 2, 6],
		//	[1, 8, 5, 9, 7, 2, 6],
		//	[1, 5, 8, 4, 3, 2, 6],
		//	[9, 5, 5, 4, 3, 2, 1]
		//]
		$lineup = array(
			1 => array(),
			2 => array(),
			3 => array(),
			4 => array()
		);
		// Array for holding preferences of players who didn't play last quarter
		$noPositionPreferences = array();

		for ($i = 0; $i < 4; $i++) {
			// Initialise array to be blank
			for ($j = 0; $j < 7; $j++) {
				$quarterLineup[$j] = "";
			}

			// Check players who didn't play last quarter and calculate for them first
			while ($playerPreference = array_shift($noPosition)) {
				calculate_player_position($playerPreference, $quarterLineup, $noPosition);
			}

			// Calculate position for each player
			foreach ($preferredPositions as $playerPreference) {
				// Check if preferences have been set
				if ($playerPreference[1] == "") {
					array_push($noPosition, $playerPreference);
				} else {
					calculate_player_position($playerPreference, $quarterLineup, $noPosition);
				}

			}

			// Fill in any gaps in the lineup
			for ($j = 0; $j < sizeof($quarterLineup); $j++) {
				if ($quarterLineup[$j] == "") {
					$playerPreference = array_shift($noPosition);
					$quarterLineup[$j] = $playerPreference[0];
				}
			}

			$lineup[$i + 1] = $quarterLineup;
		}

		return $lineup;

	}

	// Index of position in the lineup array
	function get_position_index($position) {
		switch ($position) {
			case "GK":
				return 0;
			case "GD":
				return 1;
			case "WD":
				return 2;
			case "C":
				return 3;
			case "WA":
				return 4;
			case "GA":
				return 5;
			case "GS":
				return 6;
			default:
				return -1;
		}

	}

	// Gets a position given an index
	function get_position($index) {
		switch ($index) {
			case 0:
				return "GK";
			case 1:
				return "GD";
			case 2:
				return "WD";
			case 3:
				return "C";
			case 4:
				return "WA";
			case 5:
				return "GA";
			case 6:
				return "GS";
			default:
				return "";
		}

	}

?>
