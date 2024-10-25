<?php
try {
    $cn = new PDO("mysql:host=localhost;dbname=lovepet", "lovepet", "sa");
    $cn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if (isset($_GET['tipo_id'])) {
        $tipo_id = $_GET['tipo_id'];
        $sql = "SELECT m.id, m.nombre, m.descripcion, m.foto, m.edad, m.tipo_id, t.tipo 
                FROM mascotas m 
                INNER JOIN tipos_animales t ON m.tipo_id = t.tipo_id 
                WHERE m.tipo_id = :tipo_id";
        $stmt = $cn->prepare($sql);
        $stmt->bindParam(':tipo_id', $tipo_id, PDO::PARAM_STR);
        $stmt->execute();

        $detalles = $stmt->fetchAll(PDO::FETCH_ASSOC);

        header('Content-Type: application/json');
        echo json_encode($detalles);
    } else {
        throw new Exception('tipo_id not provided');
    }
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
} catch (Exception $e) {
    echo "Error: " . $e->getMessage();
}
?>
