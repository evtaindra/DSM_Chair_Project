<?php

    require_once '../db_connection/connection.php';

    $stories = array();

    Class Story {

        public function __construct($story_id, $story_title, $story_image_name, $story_image_format, 
        $story_image_path, $story_approved, $user_id, $category_id) {
            $this->story_id = $story_id;
            $this->story_title = $story_title;
            $this->story_image_name = $story_image_name;
            $this->story_image_format = $story_image_format;
            $this->story_image_path = $story_image_path;
            $this->story_approved = $story_approved;
            $this->user_id = $user_id;
            $this->category_id = $category_id;

        }
    }

    $query = "SELECT * FROM stories WHERE category_id = ".$_GET['category_id'];

    $result = $connection->query($query);

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $story = new Story($row['story_id'],
                $row['story_image_name'],
                $row['story_image_format'],
                $row['story_image_path'],
                $row['story_title'],
                $row['user_id'],
                $row['category_id']);

            array_push($stories, $story);

        }
        
    } else {
        echo "0 results";
    }

    header('Content-Type: application/json');
    echo json_encode($stories);

    $connetion->close();

?>