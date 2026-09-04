<?php
include 'conexion.php';

$name      = $_POST["name"] ?? 'sin_datos';
$email     = $_POST["email"] ?? 'sin_datos';
$user_name = $_POST["user_name"] ?? 'sin_datos';
$password  = $_POST["password"] ?? 'sin_datos';

$statement = mysqli_prepare($con, "INSERT INTO user (name, email, user_name, password) VALUES (?, ?, ?, ?)");

if ($statement) {
    mysqli_stmt_bind_param($statement, "ssss", $name, $email, $user_name, $password);
    $exec = mysqli_stmt_execute($statement);

    if ($exec) {
        $response = [
            "success" => true,
            "test_conexion" => "ARCHIVO_CORRECTO_EDITADO",
            "datos_recibidos" => [
                "name" => $name,
                "email" => $email,
                "user_name" => $user_name
            ]
        ];
    } else {
        $response = [
            "success" => false,
            "mysql_error" => mysqli_stmt_error($statement)
        ];
    }
    mysqli_stmt_close($statement);
} else {
    $response = [
        "success" => false,
        "mysql_error" => mysqli_error($con)
    ];
}

echo json_encode($response);
mysqli_close($con);
?>