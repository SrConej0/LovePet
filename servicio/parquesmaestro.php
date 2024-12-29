<?php
try {
    $cn = new PDO("mysql:host=localhost;dbname=ParquesDB", "root", "yourpassword");
    $cn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = "SELECT id, nombre, descripcion, foto, visitantes, areas_verdes, instalaciones FROM ParquesMascotas";
    $stmt = $cn->query($sql);
    $parques = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $row['foto'] .= '?v=' . time(); // Agrega un parámetro de versión con un timestamp a la URL de la imagen
        $parques[] = $row;
    }
    header('Content-Type: application/json');
    echo json_encode($parques);
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>
