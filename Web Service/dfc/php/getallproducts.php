<?php
header('Content-Type: text/html; charset=utf-8');
include 'config.php';
if(isset($_POST))
{
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
	mysqli_set_charset($con,'utf8');
	$query = "SELECT * FROM `products`";
	$result = mysqli_query($con,$query);
	$output = array();
	while($row = mysqli_fetch_assoc($result))
	{	
		$output[] = array (
							'ProductID' => $row["ProductID"],
							'ProductName' => $row["ProductName"],
							'Detail' => $row["Detail"],
							'Price' => $row["Price"],
							'Avatar' => $row["Avatar"],
							'CategoryID' => $row["CategoryID"]
							);
	}
	mysqli_close($con);
	$JS = array('Mon_an' => $output);
	echo json_encode($JS);
}
?>