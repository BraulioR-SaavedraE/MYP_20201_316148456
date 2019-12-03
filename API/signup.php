<?php
require_once __DIR__."/db/database.php";
require_once __DIR__."/bases/servlet.php";
/**
 * Servlet para registrar usuarios
 */
class Signup extends Servlet {
    function doGet() {
        parent::assert_array_parameters($_GET, ["username", 'password']);
        $username = $_GET["username"];
        $password = $_GET["password"];
        $bd = Database::getInstance();
        $rs = $bd->call_procedure("sp_signup", "'$username','$password'")[0];
        if ($rs["msj"] == 'ok') {
            return [
                "status" => 'ok'
            ];
        } else {
            throw new ManualException($rs["msj"]);
        }
    }
}
(new Signup())->invoke();