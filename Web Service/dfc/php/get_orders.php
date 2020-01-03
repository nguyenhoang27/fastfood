<?php
header('Content-Type: text/html; charset=utf-8');
include 'config.php';
if(isset($_POST))
{
	$UserID = $_POST['UserID'];
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
	mysqli_set_charset($con,'utf8');
	$query = "SELECT * FROM `orders` where `UserID` = '$UserID'";
	$result = mysqli_query($con,$query);
	while($row = mysqli_fetch_assoc($result))
	{	
		$output[] = array 
						(
						 'OrderID' => $row["OrderID"],
						 'OrderDate' => $row["OrderDate"],
						 'Address' => $row["Address"],
						 'Phone' => $row["Phone"],
						 'Price' => $row["Price"],
						 'OrderStatus' => $row["OrderStatus"]
						);
	}
	$JS = array('json_data' => $output);
	echo json_encode($JS);
}
?>