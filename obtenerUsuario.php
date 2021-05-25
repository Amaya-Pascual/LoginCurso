<?php
include 'conexionAndroid.php';
//$usu_usuario=$_POST['mail'];
$usu_usuario="kk@kk.es";



	$sql="select * from clientes where mail='$usu_usuario'";
            	
			$result = $pdo ->prepare($sql);
			$result->execute();
			$resultado = $result->fetch(PDO::FETCH_ASSOC);
			echo json_encode($resultado,JSON_UNESCAPED_UNICODE); 	
			
			$cuenta = $result->rowCount();
			        
    


?>