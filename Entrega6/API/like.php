<?php
require_once __DIR__."/db/database.php";
require_once __DIR__."/bases/servlet.php";
class Like extends Servlet{
    function doGet() {
        parent::assert_array_parameters($_GET, ["key"]);
        parent::assert_user($_GET["key"]);
        $dog_id = $_GET["dog_id"];
        $bd = Database::getInstance();
        $rs = $bd->call_procedure("sp_like", "'$dog_id'")[0];
        if ($rs["msj"] != "ok") {
            throw new ManualException($rs["msj"]);
        }
        return new stdClass();
    }
}
(new Like())->invoke();
