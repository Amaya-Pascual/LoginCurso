<?php
  
  //$host_name = 'localhost';
  //$database = 'subasta';
  //$user_name = 'root';
  //$password = '';
  //$pdo = null;

	//sarenet
	$host_name = 'localhost';
  	$database = 'lavin';
  	$user_name = 'lavin';
  	$password = '*******';
  	$pdo = null;
	

  try {
    $pdo = new PDO("mysql:host=$host_name; dbname=$database;charset=UTF8", $user_name, $password);
  } catch (PDOException $e) {
    echo "Error!: Problema en la conexión " . $e->getMessage() . "<br/>";
    die();
  }
