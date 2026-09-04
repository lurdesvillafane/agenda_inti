<?php
$host = "localhost";
$user = "root";
$password = "";
$database = "usuarios";

$con = mysqli_connect($host, $user, $password, $database);

if (!$con) {
    header('Content-Type: application/json');
    echo json_encode(["success" => false, "message" => "Error de conexión a la base de datos: " . mysqli_connect_error()]);
    exit();
}
?>