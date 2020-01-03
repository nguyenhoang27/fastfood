<?php
header('Content-Type: text/html; charset=utf-8');
include 'config.php';
if(isset($_POST))
{
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
	mysqli_set_charset($con,'utf8');
	$query = "SELECT max(Version),UpdateTime FROM `dbversion`";
	$result = mysqli_query($con,$query);
	$output = array();
	while($row = mysqli_fetch_assoc($result))
	{		
		$output[] = array (
						 'Version' => $row["max(Version)"],
						 'UpdateTime' => $row["UpdateTime"]
						  );
	}
	mysqli_close($con);
	echo json_encode($output);
}
?>