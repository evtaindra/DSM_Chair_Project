<?php 

require_once "../db_connection/connection.php";

//Url params
$user_id = $_GET['user_id'];

//Lista categorias
$query = "select s.story_id, s.story_title, s.story_image_name, s.story_image_format, s.story_image_path, s.story_description, s.story_approved, s.user_id, s.category_id,
u.user_name, u.user_lastname from stories as s inner join users as u on s.user_id = u.user_id where u.user_id = " .$user_id;

$sql = $pdo -> prepare($query);
$sql -> execute();
$list_categories = $sql -> fetchAll();

header('Content-Type: application/json');
echo json_encode($list_categories);


?>