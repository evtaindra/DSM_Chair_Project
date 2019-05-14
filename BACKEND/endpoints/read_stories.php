<?php

    require_once '../db_connection/connection.php';

    $stories = array();

    Class Story {

        public function __construct($story_id, $image_name, $image_format, $image_route, $title, $userId, $category_id) {
            $this->story_id = $story_id;
            $this->image_name = $image_name;
            $this->image_format = $image_format;
            $this->image_route = $image_route;
            $this->title = $title;
            $this->userId = $userId;
            $this->category_id = $category_id;

        }
    }

    $query = "SELECT * FROM stories WHERE category_id = ".$_GET['category_id'];

    $result = $connection->query($query);

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $story = new Story($row['story_id'],
                $row['image_name'],
                $row['image_format'],
                $row['image_route'],
                $row['title'],
                $row['userId'],
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