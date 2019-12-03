<?php
require_once __DIR__."/db/database.php";
require_once __DIR__."/bases/servlet.php";
/**
 * Servlet para obtener el Feed de perros
 */
class Feed extends Servlet {
    function doGet() {
        parent::assert_array_parameters($_GET, ["key"]);
        parent::assert_user($_GET["key"]);
        $bd = Database::getInstance();
        return $bd->consulta("select * from dog order by rand() limit 2000;");
    }
}
(new Feed())->invoke();