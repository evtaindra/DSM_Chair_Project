<?php 

require_once '../db_connection/connection.php';

//getting the values
$user_name = $_POST['user_name'];
$user_lastname = $_POST['user_lastname'];
$user_phone = $_POST['user_phone'];
$user_email = $_POST['user_email'];
$user_password = $_POST['user_password'];;

//check if the user is already exist in the db
$query = $connection->prepare("SELECT user_id FROM users WHERE user_email = ?");
$query->bind_param("s", $user_email);
$query->execute();
$query->store_result();

if ($query->num_rows > 0) {
	$response['error'] = true;
	$response['message'] = "This user is already registered.";
	$query->close();

} else {
	//if user is new
	$query = $connection->prepare("INSERT INTO users(user_name, user_lastname, user_email, user_phone, user_password) VALUES(?, ?, ?, ?, ?)");

	$query->bind_param("sssss", $user_name, $user_lastname, $user_email, $user_phone, $user_password);

	//if the user is succesfully added to database
	if ($query->execute()) {
		//fetching the user data
		$query = $connection->prepare("SELECT * FROM users WHERE user_email = ?");
		$query->bind_param("s", $user_email);
		$query->execute();
		$query->bind_result($user_id, $user_name, $user_lastname, $user_email, $user_phone, $user_password);
		$query->fetch();

		$user = array(
			'user_id'=>$user_id,
			'user_name'=>$user_name,
			'user_lastname'=>$user_lastname,
			'user_email'=>$user_email,
			'user_phone'=>$user_phone,
			'user_password'=>$user_password);

		$query->close();

		//adding the user data in response
		$response['error'] = false;
		$response['message'] = "User registered successfully.";
		$response['user'] = $user;

	}

}

//display json response structure
header('Content-Type: application/json');
echo json_encode($response);

?>