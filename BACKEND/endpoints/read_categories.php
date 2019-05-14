<?php

require_once "../db_connection/connection.php";

//Lista categorias
$query = 'SELECT * FROM categories';

$sql = $pdo -> prepare($query);
$sql -> execute();
$list_categories = $sql -> fetchAll();

header('Content-Type: application/json');
echo json_encode($list_categories);

?>