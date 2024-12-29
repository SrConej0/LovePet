<?php
try {
    $cn = new PDO("mysql:host=localhost;dbname=ParquesDB", "root", "yourpassword");
    $cn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if (isset($_GET['id'])) {
        $id = $_GET['id'];
        $sql = "SELECT nombre, latitud, longitud, foto FROM ParquesMascotas WHERE id = :id";
        $stmt = $cn->prepare($sql);
        $stmt->bindParam(':id', $id, PDO::PARAM_INT);
        $stmt->execute();

        $park = $stmt->fetchAll(PDO::FETCH_ASSOC);

        header('Content-Type: application/json');
        echo json_encode($park);
    } else {
        throw new Exception('ID no proporcionado');
    }
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
} catch (Exception $e) {
    echo "Error: " . $e->getMessage();
}
?>
