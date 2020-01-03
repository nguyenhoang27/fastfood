<?php
header('Content-Type: text/html; charset=utf-8');
include 'config.php';
if(isset($_POST))
{
	$UserID = $_POST['UserID'];
	$Username = $_POST['Username'];
	$Password = $_POST['Password'];
	$FirstName = $_POST['FirstName'];
	$LastName = $_POST['LastName'];
	$Address = $_POST['Address'];
	$District = $_POST['District'];
	$City = $_POST['City'];
	$Phone = $_POST['Phone'];
	$Email = $_POST['Email'];
	
	
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
	if (!$con)
		$output = array('status' => 0); //Lỗi kết nối Database.
	else
	{
		mysqli_set_charset($con,'utf8');
		$query = "INSERT INTO `accounts`(`UserID`, `Username`, `Password`, `FirstName`, `LastName`, `Address`, `District`, `City`, `Phone`, `Email`)
		VALUES ('$UserID','$Username','$Password','$FirstName','$LastName','$Address','$District','$City','$Phone','$Email')";
		$result = mysqli_query($con,$query);
if(!$result)
			$output = array('status' => 1); //Lỗi câu truy vấn.
		else
			$output = array('status' => 2); //Thêm thông tin vào Database Thành công.
	}
	mysqli_close($con);
	echo json_encode($output);
}
?>