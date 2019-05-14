<?php

    require_once "../db_connection/connection.php";

    $categories = array();
    //Fabulas, Aventuras, Leyendas, Terror

    Class Category {

        public function __construct($category_id, $name_category, $image_name, $image_format, $image_route) {

            $this->category_id = $category_id;
            $this->name_category = $name_category;
            $this->image_name = $image_name;
            $this->image_format = $image_format;
            $this->image_route = $image_route;

        }

    }

    $query = "SELECT * FROM categories";

    $result = $connection->query($query);

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $category = new Category($row['category_id'],
                $row['name_category'],
                $row['image_name'],
                $row['image_format'],
                $row['image_route']);

            array_push($categories, $category);


        }
        
    } else {
        echo "0 results";
    }

    header('Content-Type: application/json');
    echo json_encode($categories);
?>