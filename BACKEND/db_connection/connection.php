<?php
    $server_name = "localhost";
    $db_name = "db_stories";
    $user = "root";
    $password = "";
    $charset = "utf8";

    try {
        $connection = new PDO("mysql:host=$server_name; dbname=$db_name;", $user, $password);
        $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        

    } catch(PDOException $e) {
        echo "Conection failed: " . $e->getMessage();
    }
?>