<?php

require_once "../db_connection/connection.php";

//getting the values
$story_title = $_POST['story_title'];
$image_name = $_POST['image_name'];
$image_format= $_POST['image_format'];
$image_path = $_POST['image_route'];
$story_description = $_POST['story_description'];
$story_approved = $_POST['story_approved'];
$user_id = $_POST['user_id'];
$category_id = $_POST['category_id'];

$query = $connection->prepare("INSERT INTO stories(story_title, story_image_name, story_image_format, story_image_path, story_description, story_approved, user_id, category_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
$query->bind_param("ssssssss", $story_title, $image_name, $image_format, $image_path, $story_description, $story_approved, $user_id, $category_id);

//if the story is succesfully added to database
if ($query->execute()) {
    //fetching teh user back
	$query = $connection->prepare("SELECT * FROM stories WHERE story_title = ?");
	$query->bind_param("s", $story_title);
	$query->execute();
	$query->bind_result($story_id, $story_title, $story_image_name, $story_image_format, $story_image_path, $story_description, $story_approved, $user_id, $category_id);
	$query->fetch();

	$story = array(
		'story_id'=>$story_id,
		'story_title'=>$story_title,
		'story_image_name'=>$story_image_name,
		'story_image_format'=>$story_image_format,
		'story_image_path'=>$story_image_path,
		'story_description'=>$story_description,
		'story_approved'=>$story_approved,
		'user_id' => $user_id,
		'category_id' => $category_id
	);

	$query->close();

		//adding the user data in response
	$response['error'] = false;
	$response['message'] = "The story has been registered successfully.";
	$response['story'] = $story;

} else {

	$response['error'] = true;
	$response['message'] = "There was a problem to register your story.";
	$response['story'] = $story;


}

//display json response structure
header('Content-Type: application/json');
echo json_encode($response);



?>