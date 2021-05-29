<?php
include 'conexionAndroid.php';
$usu_usuario=$_POST['mail'];
$usu_password=$_POST['contrasena'];

//echo $usu_usuario="aa@aa.es";
//echo $usu_password="aa";

			$result_sql = "SELECT * FROM clientes WHERE mail = '$usu_usuario'";
			$result = $pdo ->prepare($result_sql);
			$result->execute();
			$resultado = $result->fetch(PDO::FETCH_ASSOC);
			//var_dump($resultado);
			if ($resultado && $usu_usuario!=""){				
				$hash = $resultado['contrasena']; //lo que guarda la base de datos, que será el hash de la contraseña
				$hashAdm=$resultado['contrasenaAdm'];				
				if (password_verify($usu_password, $hash)|| password_verify($contrasena, $hashAdm)){
					//echo "correcto";	
					echo json_encode($resultado,JSON_UNESCAPED_UNICODE); 					
				}
				
			}

?>
