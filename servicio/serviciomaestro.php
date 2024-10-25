<?php
try {
    $cn = new PDO("mysql:host=localhost;dbname=lovepet", "lovepet", "sa");
    $cn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = "SELECT tipo_id, tipo, descripcion, foto FROM tipos_animales";
    $stmt = $cn->query($sql);
    $tipos_animales = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        // Agrega un parámetro de versión con un timestamp a la URL de la imagen
        $row['foto'] .= '?v=' . time();
        $tipos_animales[] = $row;
    }
    header('Content-Type: application/json');
    echo json_encode($tipos_animales);
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>
