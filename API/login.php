<?php

include 'conexion.php';

$usuarios = $_GET['username'];
$contrasena = $_GET['password'];

try{
    // Preparamos la cadena para hacer la consulta a la base de datos.
    $sentencia = $conexion->prepare("SELECT * FROM user WHERE username=? AND password=?");
    $sentencia->bind_param('ss', $usuarios, $contrasena);
    $sentencia->execute();

    $resultado = $sentencia->get_result();
    $json = [];


    if ($resultado->fetch_assoc()) { /* en caso de que se haya podido llevar a cabo la consulta con los datos
        brindados */
        $identificador = $usuarios . $contrasena;
        $json = [
            "status" => "ok",
            "key" => hash("sha256", $identificador),
        ];
    } else {    /* En caso de que no haya coincidencias entre los datos brindados y los registros */
        $json = [
            "status" => "failed",
            "Message" => "Wrong username or password",
        ];
    }

    echo json_encode($json, JSON_UNESCAPED_UNICODE);
    $sentencia->close();
    $conexion->close();
} catch (Exception $ex) {   /* Reporte de alguna excepción que se produzca al realizar la consulta */
    http_response_code();
    echo json_encode([
        "status" => "ServerError",
        "message"=> $ex->getMessage()
    ]);
}
