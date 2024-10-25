<?php
$target_dir = "C:/xampp/htdocs/imagenes/";
if (!file_exists($target_dir)) {
    mkdir($target_dir, 0777, true);
}
$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
$uploadOk = 1;
$imageFileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));

// Chequear si el archivo es una imagen
if(isset($_POST["submit"])) {
  $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
  if($check !== false) {
    echo "El archivo es una imagen - " . $check["mime"] . ".";
    $uploadOk = 1;
  } else {
    echo "El archivo no es una imagen.";
    $uploadOk = 0;
  }
}

// Chequear si el archivo ya existe
if (file_exists($target_file)) {
  echo "Lo siento, el archivo ya existe.";
  $uploadOk = 0;
}

// Limitar el tamaño del archivo
if ($_FILES["fileToUpload"]["size"] > 500000) {
  echo "Lo siento, tu archivo es demasiado grande.";
  $uploadOk = 0;
}

// Limitar a ciertos formatos de archivo
if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
&& $imageFileType != "gif" ) {
  echo "Lo siento, solo se permiten archivos JPG, JPEG, PNG y GIF.";
  $uploadOk = 0;
}

// Chequear si $uploadOk está en 0 por algún error
if ($uploadOk == 0) {
  echo "Lo siento, tu archivo no fue subido.";
// Si todo está bien, intentar subir el archivo
} else {
  if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
    echo "El archivo ". basename( $_FILES["fileToUpload"]["name"]). " ha sido subido.";
  } else {
    echo "Lo siento, hubo un error subiendo tu archivo.";
  }
}
?>
