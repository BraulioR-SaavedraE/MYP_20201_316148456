<?php
require_once __DIR__."/db/database.php";
require_once __DIR__."/bases/servlet.php";
class Detalles extends Servlet {
	function doGet() {
		parent::assert_array_parameters($_GET, ["key", "dog_id"]);
		parent::assert_user($_GET["key"]);
		$dog = $_GET["dog_id"];
		$consultad="SELECT * FROM dog WHERE idDog='$dog'";
		$consultac="SELECT * FROM feed WHERE idDog='$dog'";
		$db = Database::getInstance();

		try {
			$drs = $db->consulta($consultad);
			$crs = $db->consulta($consultac);

			if (count($drs) == 0) {
				return [
					"status" => "failed",
					"Message" => "No se encontro perro especificado"
				];
			} else {
				$comentarios = [];
				if (count($crs) > 0) {
					for ($i = 0; $i < count($crs); $i++)
						array_push($comentarios, [
							"fecha" => $crs[$i]['date'],
							"user_id" => $crs[$i]['idUser'],
							"text" => $crs[$i]['text']
						]);
				}

				return [
					"name" => $drs[0]['name'],
					"imagen" => $drs[0]['image'],
					"likes" => $drs[0]['likes'],
					"comentarios" => $comentarios
				];
			}
		} catch(Exception $e) {
			return [
				"status" => "failed",
				"Message" => $e->getMessage(),
			];
		}

		echo json_encode($resultado);
	}
}
(new Detalles())->invoke();
