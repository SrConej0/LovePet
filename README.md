Aplicación Android con Base de Datos en PHP y MySQL

Descripción:
Esta aplicación interactúa con una base de datos MySQL mediante servicios PHP alojados en un servidor local configurado con XAMPP.

Requisitos previos:

Android Studio instalado.

XAMPP instalado.

Navegador web para acceder a phpMyAdmin.

Pasos para configurar el proyecto:

Configurar XAMPP:

Descarga e instala XAMPP 

Abre el panel de control de XAMPP.

Inicia los servicios Apache y MySQL.

Preparar archivos PHP:

Copia los archivos de servicios PHP y las imágenes en la carpeta:
C:\xampp\htdocs\mi_proyecto

Verifica que los archivos sean accesibles desde el navegador:
http://localhost/mi_proyecto

Configurar la base de datos en phpMyAdmin:

Accede a phpMyAdmin desde: http://localhost/phpmyadmin

Crea una base de datos llamada "mi_base_de_datos".

Importa el archivo SQL del proyecto:

Haz clic en la pestaña Importar.

Selecciona el archivo .sql.

Haz clic en Continuar.

Configurar el proyecto Android:

Abre el proyecto en Android Studio.

Cambia la URL base en el código a:
http://<tu_IP_local>/mi_proyecto
(En emulador, usa http://10.0.2.2).

Crea o edita el archivo local.properties en el proyecto y agrega:
sdk.dir=C:\Users<tu_nombre_de_usuario>\AppData\Local\Android\Sdk

Ejecutar la aplicación:

Configura la versión del SDK en el archivo build.gradle:
compileSdkVersion 35
targetSdkVersion 35

Inicia la aplicación desde Android Studio.

Verifica que los datos de la base de datos se carguen correctamente.

Notas adicionales:

Asegúrate de que Apache y MySQL estén en ejecución si encuentras problemas.

Usa Logcat en Android Studio para depurar posibles errores.

Este archivo README puede ser utilizado para configurar y ejecutar el proyecto paso a paso de manera sencilla.

