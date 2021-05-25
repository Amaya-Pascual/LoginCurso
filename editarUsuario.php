<?php
include 'conexionAndroid.php';
$usu_usuario=$_POST['mail'];
$usu_nombre=$_POST['nomCliente'];
$usu_apellido=$_POST['ape1Cliente'];
@$form_pass=$_POST['contrasena'];
@$hash= password_hash($form_pass, PASSWORD_BCRYPT);


//echo " mail ". $usu_usuario="kk@kk.es";
//echo "\nNombre ". $usu_nombre="kk";
//echo "\nape ".$usu_apellido="kk";
//echo "\npassw ".$contrasena="kk";
//echo "\nformpass ".@$form_pass="kk";
//echo "\nhash ".@$hash= password_hash($form_pass, PASSWORD_BCRYPT);

    // VERIFICAMOS QUE NO ESTEN VACIAS LAS VARIABLES
    if(empty($usu_usuario) || empty($usu_nombre) || empty($usu_apellido) || empty($form_pass)) {

        // SI ALGUNA VARIABLE ESTA VACIA MUESTRA ERROR
        //echo "Se deben llenar los campos";
        echo "ERROR 1";

    } else {
        // CREAMOS LA CONSULTA
	$sql="UPDATE clientes SET nomCliente = '$usu_nombre', ape1Cliente = '$usu_apellido', mail='$usu_usuario', contrasena= '$hash' WHERE mail = '$usu_usuario'";	
            	
			$result = $pdo ->prepare($sql);
			$result->execute();
			$cuenta = $result->rowCount();
			if ($cuenta<>0) echo "MENSAJE";		        
    }


?>