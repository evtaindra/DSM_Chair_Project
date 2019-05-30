<?php

require_once "../db_connection/connection.php";

//Url params
$story_id = $_GET['story_id'];

//Lista categorias
$query = 'SELECT * FROM sections where story_id = ' .$story_id;

$sql = $pdo -> prepare($query);
$sql -> execute();
$list_categories = $sql -> fetchAll();

header('Content-Type: application/json');
echo json_encode($list_categories);

?>