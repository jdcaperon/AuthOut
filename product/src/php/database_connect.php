<?php
	class DatabaseConnection {
	    // Variable for the database connection
	    public $con  = NULL;
	    public $stmt = NULL;
		
	    // Class constructore, connects to the database
	    function __construct() {
	        // Connection settings
	        $user = "root";
	        $password = "infs3202";
	        $database = "authout_product";
			
	        // Connect to the database server and check for errors
		    $this->con = mysqli_connect("127.0.0.1", $user, $password);
			
			if(!$this->con){
				die("Could not connect: ".mysqli_connect_error());
			}
			
			// Select the database and check for errors
			$db = mysqli_select_db($this->con, $database);
			
			if(!$db){
				die ('Cannot use : ' . $database);
			}
			
			// Return the connection
			return $this->con;

		}
		
		// Generic query function, check for errors and returns the result
		function query($query) {
		    $result = mysqli_query($this->con,$query);
		    
		    if(mysqli_error($this->con)){
		        die("Error: ".mysqli_error($this->con));
		    }
		    
		    return $result;
		}
		
		// Disconnect from the database
		function disconnect() {
			mysqli_close($this->con);
			
			if ($this->stmt) {
			    $this->stmt->close();
			}
			
		}
		
		// Prepare, bind, execute and return a statement
		function prepared_query($query, $types, ...$values) {
		    // Start a new query
		    if ($this->stmt) {
		        $this->stmt->close();
		    }
		    
			$this->stmt = $this->con->prepare($query);
			$this->stmt->bind_param($types, ...$values);
			$this->stmt->execute();
			
			return $this->stmt->get_result();
		}
		
	}
	
?>