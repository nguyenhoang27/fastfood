<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Web Order</title>
<style>
	body{
		background-image:url(bg.jpg);
		background-attachment:fixed;
		background-repeat: repeat;
	}
 </style>
</head>
<body>
<?php
	$randomString = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, 4);
	include 'config.php';
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE);
	mysqli_set_charset($con,'utf8');
	if(isset($_POST['themdanhmuc']))
	{
		$CategoryID = $_POST['CategoryID'];
		$Name = $_POST['Name'];
		$Detail = $_POST['Detail'];
		$query1 = "Insert into `category` 
				   Values('$CategoryID','$Name','$Detail')";
		$result = mysqli_query($con,$query1);
	}
echo "<form action=them-danh-muc.php method = post>";
echo "<table>";
	echo "<tr>";
		echo "<td>Mã danh mục: </td>";
		echo "<td><input type='text' name=CategoryID readonly='readonly' value=".$randomString."></td>";
	echo "</tr>";
	
	echo "<tr>";
		echo "<tr><td>Tên danh mục: </td>";
		echo "<td> <input type='text' name=Name></td>";

	echo "<tr>";
		echo "<tr><td>Thông tin danh mục: </td>";
		echo "<td> <input type='text' name=Detail></td>";
	echo "</tr>";
	
	echo "<tr>";
	echo "<td>";
	echo "</td>";
	echo "<td>";
		echo "<input type='submit' name=themdanhmuc value='Thêm Danh Mục'/>";
	echo "</td>";
	echo "</tr>";
	
echo "</table>";
echo "</form>";
	mysqli_close($con);
?>
</table>
</body>
</html>