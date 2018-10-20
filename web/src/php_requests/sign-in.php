<?php
session_start();

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    //sign in

}else {
    session_destroy();
}
?>
