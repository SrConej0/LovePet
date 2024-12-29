<?php
try {
    $cn = new PDO("mysql:host=localhost;dbname=lovepet", "lovepet", "sa");
    $cn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Consulta SQL para obtener los datos de la tabla
    $sql = "SELECT m.id, m.nombre, m.descripcion, m.edad, t.tipo 
            FROM mascotas m
            JOIN tipos_animales t ON m.tipo_id = t.tipo_id
            ORDER BY m.id"; 
    $stmt = $cn->query($sql);

    // Array para almacenar los resultados
    $mascotas = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $mascotas[] = $row;
    }

    // Devolver los resultados en formato JSON
    header('Content-Type: application/json');
    echo json_encode($mascotas);
} catch (PDOException $e) {
    // Manejo de errores
    echo "Error: " . $e->getMessage();
}
?>
            