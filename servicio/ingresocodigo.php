<?php
try {
    $cn = new PDO("mysql:host=localhost;dbname=lovepet", "lovepet", "sa");
    $cn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $telefono = $_POST['telefono'];
        $codigo = $_POST['codigo'];

        $sql = "SELECT * FROM ingreso WHERE telefono = :telefono AND codigo = :codigo";
        $stmt = $cn->prepare($sql);
        $stmt->bindParam(':telefono', $telefono);
        $stmt->bindParam(':codigo', $codigo);
        $stmt->execute();

        if ($stmt->rowCount() > 0) {
            echo json_encode(["success" => true]);
        } else {
            echo json_encode(["success" => false, "message" => "Código incorrecto."]);
        }
    }
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Error: " . $e->getMessage()]);
}
?>
