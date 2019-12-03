<?php
require_once __DIR__."/db/database.php";
require_once __DIR__."/bases/servlet.php";
/**
 * Servlet para dejar un comentario para un perro
 */
class Comentar extends Servlet{
    function doPost() {
        parent::assert_array_parameters($_GET, ["key"]);
        parent::assert_array_parameters($_POST, ["comentario"]);
        $idUser = parent::assert_user($_GET["key"]);
        $dog_id = $_GET["dog_id"];
        $comentario = $_POST["comentario"];
        $bd = Database::getInstance();
        $rs = $bd->call_procedure("sp_feed", "'$dog_id','$idUser','$comentario'")[0];
        if ($rs["msj"] != "ok") {
            throw new ManualException($rs["msj"]);
        }
        return new stdClass();
    }
}
(new Comentar())->invoke();

