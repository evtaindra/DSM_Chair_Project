<?php

$link = 'mysql:host=localhost;dbname=db_stories2;charset=utf8';
$server = 'localhost';
$db = 'db_stories2';
$user = 'root';
$password = '';

try{
    $pdo = new PDO($link,$user,$password);
    $connection = new mysqli($server, $user, $password, $db);
    mysqli_set_charset($connection,"utf8");
    //echo "SUCCESSFUL CONNECTION DB_STORIES";
}catch(PDOException $e){
    print '¡ERROR!: ' . $e -> getMessage()."</br>";
    die();
}