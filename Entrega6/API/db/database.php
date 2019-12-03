<?php
define("DATABASE_CONECTION_ERROR", "Error al conectar con la base de datos");
define("EXCEPTION_SQLERROR", "Ocurrió un error con la base de datos, por favor, intentalo mas tarde");
define("EXCEPTION_NOSESSION", "NO_SESSION");

/**
 * Clase que maneja la conexión con la base de datos
 */
class Database{
    // instancia única de la clase
    private static $instance = null;
    // conexión con la base
    private $conn;
    // dirección del servidor de bases
    private $url = "localhost";
    // puerto del servidor de bases
    private $prt = "3306";
    // nombre de la base
    private $bd = "id11511314_entrega6";
    // usuario de la base
    private $usr = "id11511314_dogspott";
    // contseña del usuario
    private $psw = "Entrar567*";

    /**
     * Devuelve el singleton de la clase
     * @return Database el singleton
     */
    public static function getInstance() {
        if (self::$instance == null) {
            self::$instance = new Database();
        }
        return self::$instance;
    }
    
    /**
     * Función constructora: inicia la conexión con la base
     * @throws Exception si ocurre un error al conectar con la base
     */
    private function __construct() {
        $this->conn = new mysqli($this->url, $this->usr, $this->psw, $this->bd, $this->prt);
        if($this->conn->connect_error){
            throw new Exception(DATABASE_CONECTION_ERROR."->".$this->conn->connect_error);
        }
        if(!$this->conn->set_charset("utf8")){
            throw new Exception(DATABASE_CONECTION_ERROR."->".$this->conn->connect_error);
        }
    }
    
    /**
     * Cambia valores vacíon de una cadena por un "null".
     * @param params $params el cuerpo del comando para la base-
     * @return string el cuerpo del comando modificado
     */
    public static function parseEmptyParams($params) {
        return str_replace("''", "null", $params);
    }
    
    /**
     * Convierte una fecha en cadena al formato de la Ciudad de México
     * @param string $utc la fecha a parsear
     * @return string la fecha parseada
     */
    public static function parseUTCDate($utc) {
        if ($utc == '') {
            return $utc;
        }
        date_default_timezone_set("America/Mexico_City");
        $d = strtotime($utc);
        return date("Y-m-d H:i:s", $d);
    }
    
    /**
     * Llama un procedure en la base
     * @param string $procName en nombre del procedure
     * @param string $params los parametros del procedure, separados por comas
     * @param boolean $assert validar o no si la llamada devuelve un mensaje diferente a "ok"
     * @return mixed un arreglo de arreglos asociativos, que son las filas devueltas por la llamada
     * al procedure, o False si el procedure no devuelve filas
     * @throws Exception si ocurre un error al llamar al procedure
     */
    public function call_procedure($procName, $params="", $assert = false){
        if(!$this->conn) {
            throw new Exception("The MySQLi connection is invalid.");
        }else{
            // Execute the SQL command.
            // The multy_query method is used here to get the buffered results,
            // so they can be freeded later to avoid the out of sync error.
            $params = self::parseEmptyParams($params);
            $sql = "CALL {$procName}({$params});";
            $sqlSuccess = $this->conn->multi_query($sql);
            if($sqlSuccess){
                if($this->conn->more_results()){
                    // Get the first buffered result set, the one with our data.
                    $result = $this->conn->use_result();
                    $output = array();
                    // Put the rows into the outpu array
                    while($row = $result->fetch_assoc()){
                        $output[] = $row;
                    }
                    // Free the first result set.
                    // If you forget this one, you will get the "out of sync" error.
                    $result->free();
                    // Go through each remaining buffered result and free them as well.
                    // This removes all extra result sets returned, clearing the way
                    // for the next SQL command.
                    while($this->conn->more_results() && $this->conn->next_result()){
                        $extraResult = $this->conn->use_result();
                        if($extraResult instanceof mysqli_result){
                            $extraResult->free();
                        }
                    }
                    if ($assert && $output[0]["msj"] != 'ok') {
                        http_response_code(400);
                        echo $output[0]["msj"];
                        exit();
                    }
                    return $output;
                }else{
                    return false;
                }
            }else{
                throw new Exception("Error de la base de datos: " . $this->conn->error);
            }
        }
    }
    
    /**
     * Ejecuta un comando en la base.
     * @param string $con el comando a ejecutar.
     * @return mixed un arreglo de arreglos asociativos, que son las filas devueltas por la llamada
     * al procedure, o False si el procedure no devuelve filas.
     * @throws Exception si ocurre un error en la base de datos.
     */
    function consulta($con){
        $result = $this->conn->query($con);
        $output = [];
        while($row = $result->fetch_assoc()){
            $output[] = $row;
        }
        $result->free();
        if($this->conn->error){
            throw new Exception("Error de la base de datos -> " . $this->conn->error);
        }
        return $output;
    }
}
