<?php
include 'conexion.php';

$user_id = $_GET["user_id"] ?? 0;

$statement = mysqli_prepare($con, "SELECT categoria_color, COUNT(*) as cantidad FROM pedidos WHERE user_id = ? GROUP BY categoria_color");
mysqli_stmt_bind_param($statement, "i", $user_id);
mysqli_stmt_execute($statement);

$result = mysqli_stmt_get_result($statement);
$conteos = array();

while ($row = mysqli_fetch_assoc($result)) {
    $conteos[$row['categoria_color']] = $row['cantidad'];
}

echo json_encode(["success" => true, "conteos" => $conteos]);

mysqli_stmt_close($statement);
mysqli_close($con);
?>