<?php
include 'conexion.php';

$user_id = $_GET["user_id"] ?? 0;
$fecha   = $_GET["fecha"] ?? '';

$statement = mysqli_prepare($con, "SELECT id_pedido AS id, titulo, descripcion, pago, fecha_inicio, fecha_entrega, cliente, categoria_color FROM pedidos WHERE user_id = ? AND fecha_inicio = ?");
mysqli_stmt_bind_param($statement, "is", $user_id, $fecha);
mysqli_stmt_execute($statement);

$result = mysqli_stmt_get_result($statement);
$pedidos = array();

while ($row = mysqli_fetch_assoc($result)) {
    $pedidos[] = $row;
}

echo json_encode(["success" => true, "pedidos" => $pedidos]);

mysqli_stmt_close($statement);
mysqli_close($con);
?>