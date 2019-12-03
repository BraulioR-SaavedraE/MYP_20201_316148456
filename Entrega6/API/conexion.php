<?php
$hostname='localhost';
$database='id11511314_entrega6';
$username='id11511314_dogspott';
$password='Entrar567*';

$conexion=new mysqli($hostname,$username,$password,$database); // conexión con la base de datos.

if($conexion->connect_errno){
    echo "Estamos experimentando problemas con el sitio web";
}
?>
