<?php
header('Content-Type: text/html; charset=utf-8');
include 'config.php';
if(isset($_POST))
{
	$OrderID = $_POST['OrderID'];
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
	mysqli_set_charset($con,'utf8');
	$query = "	SELECT `products`.`ProductName`, `orders_detail`.*
				FROM `products`, `orders_detail`
				WHERE `OrderID` = '$OrderID'
				AND `products`.`ProductID` = `orders_detail`.`ProductID`";
	$result = mysqli_query($con,$query);
	$output = array();
	while($row = mysqli_fetch_assoc($result))
	{	
		$output[] = array (	'ProductName' => $row["ProductName"],
							'DetailID' => $row["DetailID"],
							'OrderID' => $row["OrderID"],
							'ProductID' => $row["ProductID"],
							'Quantity' => $row["Quantity"],
							'Note' => $row["Note"]
						);
	}
	$JS = array('orders_detail' => $output);
	echo json_encode($JS);
}
?>