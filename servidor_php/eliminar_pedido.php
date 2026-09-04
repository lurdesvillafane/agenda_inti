<?php
include 'conexion.php';

$id = $_POST["id"] ?? 0;

$statement = mysqli_prepare($con, "DELETE FROM pedidos WHERE id_pedido = ?");
mysqli_stmt_bind_param($statement, "i", $id);

if (mysqli_stmt_execute($statement)) {
    echo json_encode(["success" => true]);
} else {
    echo json_encode(["success" => false]);
}

mysqli_stmt_close($statement);
mysqli_close($con);
?>