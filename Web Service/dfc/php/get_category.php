<?php
header('Content-Type: text/html; charset=utf-8');
include 'config.php';
if(isset($_POST))
{
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
	mysqli_set_charset($con,'utf8');
	$query = "SELECT * FROM `category`";
	$result = mysqli_query($con,$query);
	while($row = mysqli_fetch_assoc($result))
	{	
		$output[] = array 
						(
						 'CategoryID' => $row["CategoryID"],
						 'Name' => $row["Name"],
						 'Detail' => $row["Detail"]
						);
	}
	$JS = array('json_data' => $output);
	echo json_encode($JS);
}
?>