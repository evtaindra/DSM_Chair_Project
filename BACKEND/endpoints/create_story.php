<?php

    require_once "../db_connection/connection.php";
    
    $queryInsertStory = "INSERT INTO stories (story_title, user_id, category_id) values (?, ?, ?)";
?>