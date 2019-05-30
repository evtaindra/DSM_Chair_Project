<?php 

require_once '../db_connection/connection.php';

//getting values
$user_email = $_POST['user_email'];
$user_password = $_POST['user_password'];

//creating query
$query = $connection->prepare("SELECT * FROM users WHERE user_email = ? AND user_password = ?");
$query->bind_param("ss", $user_email, $user_password);
$query->execute();
$query->store_result();

//if the user exist
if ($query->num_rows > 0) {
	$query->bind_result($user_id, $user_name, $user_lastname, $user_email, $user_phone, $user_password);
	$query->fetch();

	//create a user with fetched data
	$user = array(
		'user_id'=>$user_id,
		'user_name'=>$user_name,
		'user_lastname'=>$user_lastname,
		'user_email'=>$user_email,
		'user_phone'=>$user_phone,
		'user_password'=>$user_password);

	//set the response success data
	$response['error'] = false;
	$response['message'] = 'Login successfull.';
	$response['user'] = $user;

	} else {
		//if user not found
		$response['error'] = true;
		$response['message'] = "Invalid username or password.";
		$response['user'] = null;

	}

//display json response structure
header('Content-Type: application/json');
echo json_encode($response);

?>