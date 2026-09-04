<?php
include 'conexion.php';

$user_name = $_POST["user_name"] ?? '';
$password  = $_POST["password"] ?? '';

$statement = mysqli_prepare($con, "SELECT user_id, name FROM user WHERE user_name = ? AND password = ?");

if ($statement) {
    mysqli_stmt_bind_param($statement, "ss", $user_name, $password);
    mysqli_stmt_execute($statement);
    mysqli_stmt_bind_result($statement, $user_id, $name);

    $response = array();

    if (mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["user_id"] = $user_id;
        $response["name"]    = $name;
    } else {
        $response["success"] = false;
    }

    echo json_encode($response);
    mysqli_stmt_close($statement);
} else {
    echo json_encode(["success" => false, "error" => mysqli_error($con)]);
}

mysqli_close($con);
?>