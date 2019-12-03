<?php
// manejamos utf-8 en todos los servlets
mb_internal_encoding("utf-8");
// headers autorizados
header("Access-Control-Allow-Headers: Content-Type, Authorization");

/* imports necesarios */
require_once __DIR__."/exceptions.php";
require_once __DIR__."/../db/database.php";

/**
 * Clase base para los servlets
 */
abstract class Servlet {
    /**
     * Maneja una petición POST
     */
    function doPost() {}
    /**
     * Maneja una petición GET
     */
    function doGet() {}
    /**
     * Maneja una petición DELETE
     */
    function doDelete() {}
    /**
     * Invoca el correspondiente Handler según el tipo de petición
     * @throws ManualException cuando recibe una petición no soportada
     */
    function invoke() {
        try{
            $res = new stdClass();
            switch($_SERVER["REQUEST_METHOD"]){
                case "GET":
                    $res = $this->doGet();
                    break;
                case "POST":
                    $res = $this->doPost();
                    break;
                case "DELETE":
                    $res = $this->doDelete();
                    break;
                default:
                    throw new ManualException("Método no soportado");
            }
            echo json_encode($res);
        }catch(ManualException $e) {
            http_response_code(400);
            $errorMessage = [
                "status" => "Failed",
                "message" => $e->getMessage()
            ];
            echo json_encode($errorMessage);
        }catch(AuthenticationException $e){
            http_response_code(401);
            $errorMessage = [
                "status" => "Unauthorized",
                "message" => $e->getMessage()
            ];
            echo json_encode($errorMessage);
        }catch(Exception $e){
            http_response_code(500);
            $errorMessage = [
                "status" => "Server error",
                "message" => $e->getMessage()
            ];
            echo json_encode($errorMessage);
        }
    }
    
    /**
     * Obtiene el cuerpo de la petición.
     * @return string el contenido del cuerpo de la petición.
     */
    function getBody() {
        $body = file_get_contents('php://input');
        return json_decode($body);
    }
    
    /**
     * Verifica que todas las propiedades se encuentren dentro del array
     * @param array $array el arreglo a verificar
     * @param array $parameters las propiedades que debe contener
     * @throws ManualException si a $array le falta alguno de los parámetros recibidos
     */
    function assert_array_parameters($array, $parameters) {
        foreach($parameters as $parameter) 
            if (!array_key_exists($parameter, $array) || $array[$parameter] == 'undefined') 
                throw new ManualException("Falta el parámetro: ".$parameter);
    }
    
    /**
     * Verifica que todas las propiedades se encuentren dentro del objeto
     * @param object $json el objeto a verificar
     * @param array $parameters las propiedades que dene contener
     * @throws ManualException si alguno de los parámetros no se encuentre en el objeto.
     */
    function assert_json_parameters($json, $parameters) {
        foreach($parameters as $parameter)
            if (!property_exists($json, $parameter))
                throw new ManualException("Falta el parámetro: ".$parameter);
    }

    /**
     * Verifica la validéz de una llave de autenticación
     * @param string $key la llave de autenticación
     * @return string el id del usuario que corresponde a la llave de autenticación
     * @throws AuthenticationException si la llave no coincide con un usuario en la base
     */
    function assert_user($key) {
        $bd = Database::getInstance();
        $rs = $bd->call_procedure("sp_auth", "'$key'")[0];
        if ($rs["id"] == 0) {
            throw new AuthenticationException('Llave incorrecta');
        }
        return $rs["id"];
    }
}

