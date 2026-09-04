<?php
include 'conexion.php';

$user_id         = $_POST["user_id"] ?? 0;
$titulo          = $_POST["titulo"] ?? '';
$descripcion     = $_POST["descripcion"] ?? '';
$pago            = $_POST["pago"] ?? '';
$fecha_inicio    = $_POST["fecha_inicio"] ?? '';
$fecha_entrega   = $_POST["fecha_entrega"] ?? '';
$cliente         = $_POST["cliente"] ?? '';
$categoria_color = $_POST["categoria_color"] ?? 'Categoria 1';

$statement = mysqli_prepare($con, "INSERT INTO pedidos (user_id, titulo, descripcion, pago, fecha_inicio, fecha_entrega, cliente, categoria_color) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

if ($statement) {
    mysqli_stmt_bind_param($statement, "isssssss", $user_id, $titulo, $descripcion, $pago, $fecha_inicio, $fecha_entrega, $cliente, $categoria_color);
    $exec = mysqli_stmt_execute($statement);

    if ($exec) {
        $response["success"] = true;
    } else {
        $response["success"] = false;
        $response["error"] = mysqli_stmt_error($statement);
    }
    mysqli_stmt_close($statement);
} else {
    $response["success"] = false;
    $response["error"] = mysqli_error($con);
}

echo json_encode($response);
mysqli_close($con);
?>