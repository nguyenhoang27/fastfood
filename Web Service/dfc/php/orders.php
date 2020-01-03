<?php
header('Content-Type: text/html; charset=utf-8');
include 'config.php';
if(isset($_POST))
{
	$OrderID = $_POST['OrderID'];
	$UserID = $_POST['UserID'];
	$OrderDate = $_POST['OrderDate'];
	$Address = $_POST['Address'];
	$Phone = $_POST['Phone'];
	$Price = $_POST['Price'];
	$Detail = json_decode($_POST["Detail"], true);
	
	$orderReturn = 0;
    $orderReturn = insertOrder($OrderID, $UserID, $OrderDate, $Address, $Phone, $Price);
	
	if($orderReturn > 0)
	{
        $insert = 0;
        for($i = 0; $i < count($Detail); $i++)
		{
            $ProductID = $Detail[$i]['ProductID'];
            $Quantity = $Detail[$i]['Quantity'];
            $Note = $Detail[$i]['Note'];
			
            $insert = insertOrderDetail($OrderID, $ProductID, $Quantity, $Note);
        }
        $output = array('status' => $insert);
    }
    else
        $output = array('status' => 0);
    echo json_encode($output);
	
}

function insertOrder($OrderID, $UserID, $OrderDate, $Address, $Phone, $Price)
{
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
	if (!$con)
		return -1; //Lỗi kết nối Database.
	else
	{
		mysqli_set_charset($con,'utf8');
		$query = "INSERT INTO `orders`(`OrderID`,`UserID`,`OrderDate`,`Address`,`Phone`,`Price`, `OrderStatus`)
							VALUES ('$OrderID','$UserID',NOW(),'$Address','$Phone',$Price,'0')";
		$result = mysqli_query($con,$query);
		if(!$result)
			return 0; //Lỗi câu truy vấn.
		else
            return 1; //Thêm thông tin vào Database Thành công.
	}
	mysqli_close($con);
}

function insertOrderDetail($OrderID, $ProductID, $Quantity, $Note)
{
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
	if (!$con)
		return 0; //Lỗi kết nối Database.
	else
	{
		mysqli_set_charset($con,'utf8');
		$query = "INSERT INTO `orders_detail`(`OrderID`,`ProductID`,`Quantity`,`Note`)
									VALUES ('$OrderID','$ProductID',$Quantity,'$Note')";
		$result = mysqli_query($con,$query);
		if(!$result)
			return 1; //Lỗi câu truy vấn.
		else
			return 2; //Thêm thông tin vào Database Thành công.
	}
	mysqli_close($con);
}
?>