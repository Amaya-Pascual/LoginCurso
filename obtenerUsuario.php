<?php
include 'conexionAndroid.php';
$usu_usuario=$_POST['mail'];
//$usu_usuario="kk@kk.es";

 if(empty($usu_usuario)){
	 echo "ERROR 1";
	 
 } else{

	$sql="select * from clientes where mail='$usu_usuario'";
            	
			$result = $pdo ->prepare($sql);
			$result->execute();
			$resultado = $result->fetch(PDO::FETCH_ASSOC);	
			
			$cuenta = $result->rowCount();		
			
		    echo json_encode($resultado,JSON_UNESCAPED_UNICODE); 
			
 }		        
    


?>