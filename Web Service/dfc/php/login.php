<?php
header('Content-Type: text/html; charset=utf-8');
include 'config.php';
if(isset($_POST))
{
	if(isset($_POST['user'])) $Username = $_POST['user'];
    else $Username = '';
	
    if(isset($_POST['pass'])) $Password = $_POST['pass'];
    else $Password = '';
	
	$RegID = $_POST['RegID'];
	
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE); //Kết nối database.
	if (!$con)
		$output = array('status' => 0); //Lỗi kết nối Database.
	else
	{
		mysqli_set_charset($con,'utf8');
		$query = "SELECT * FROM `accounts` WHERE Username = '$Username' AND Password = '$Password'";
		$result = mysqli_query($con,$query);
		if(!$result)
			$output = array('status' => 1); //Lỗi câu truy vấn.
		else
		{
			while($row = mysqli_fetch_assoc($result))
			{	
				$output[] = array 
								(
								 'UserID' => $row["UserID"],
								 'Username' => $row["Username"],
								 'Password' => $row["Password"],
								 'FirstName' => $row["FirstName"],
								 'LastName' => $row["LastName"],
								 'Address' => $row["Address"],
								 'District' => $row["District"],
								 'City' => $row["City"],
								 'Phone' => $row["Phone"],
								 'Email' => $row["Email"],
								 'status' => 2
								);
			}
			$gcm_query = "UPDATE `accounts` SET `RegID` = '$RegID' WHERE Username = '$Username'";
			$gcm_result = mysqli_query($con,$gcm_query);
		}
	}
	mysqli_close($con);
	$JS = array('json_data' => $output);
	echo json_encode($JS);
}
?>