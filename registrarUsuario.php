<?php
include 'conexionAndroid.php';
$usu_usuario=$_POST['mail'];
$usu_nombre=$_POST['nomCliente'];
$usu_apellido=$_POST['ape1Cliente'];
@$form_pass=$_POST['contrasena'];
@$hash= password_hash($form_pass, PASSWORD_BCRYPT);


//$usu_usuario="lola@lola.es";
//$usu_nombre="Lola";
//$usu_apellido="Gomez";
//$contrasena="1234";
//@$form_pass=$_POST['contrasena'];
//@$hash= password_hash($form_pass, PASSWORD_BCRYPT);

    // VERIFICAMOS QUE NO ESTEN VACIAS LAS VARIABLES
    if(empty($usu_usuario) || empty($usu_nombre) || empty($usu_apellido) || empty($form_pass)) {

        // SI ALGUNA VARIABLE ESTA VACIA MUESTRA ERROR
        //echo "Se deben llenar los dos campos";
        echo "ERROR 1";

    } else {
        // CREAMOS LA CONSULTA
            $sql = "INSERT INTO clientes (nomCliente, ape1Cliente, mail, contrasena) VALUES ('$usu_nombre','$usu_apellido','$usu_usuario','$hash' )";				
			$result = $pdo ->prepare($sql);
			$result->execute();
			$cuenta = $result->rowCount();
			if ($cuenta<>0) echo "MENSAJE";		        
    }


?>