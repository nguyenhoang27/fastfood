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
	$randomString = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, 8);
	include 'config.php';
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE);
	mysqli_set_charset($con,'utf8');
	if(isset($_POST['themsanpham']))
	{
		$ProductID = $_POST['ProductID'];
		$ProductName = $_POST['ProductName'];
		$Detail = $_POST['Detail'];
		$Price = $_POST['Price'];
		$Avatar = $_POST['Avatar'];
		$CategoryID = $_POST['CategoryID'];
		$query1 = "Insert into `products` 
				   Values('$ProductID','$ProductName','$Detail','$Price','$Avatar','$CategoryID')";
		$result = mysqli_query($con,$query1);
	}
echo "<form action=them-san-pham.php method = post>";
echo "<table>";
	echo "<tr>";
		echo "<td>Mã sản phẩm: </td>";
		echo "<td><input type='text' name=ProductID readonly='readonly' value=".$randomString."></td>";
	echo "</tr>";
	
	echo "<tr>";
		echo "<tr><td>Tên sản phẩm: </td>";
		echo "<td> <input type='text' name=ProductName></td>";
	echo "</tr>";
	
	echo "<tr>";
		echo "<tr><td>Thông tin sản phẩm: </td>";
		echo "<td> <input type='text' name=Detail></td>";
	echo "</tr>";
	
	echo "<tr>";
		echo "<tr><td>Giá sản phẩm: </td>";
		echo "<td> <input type='number' name=Price></td>";
	echo "</tr>";
	
	echo "<tr>";
		echo "<tr><td>Link hình sản phẩm: </td>";
		echo "<td> <input type='text' name=Avatar></td>";
	echo "</tr>";
	
	echo "<tr>";
		echo "<tr><td>Mã loại sản phẩm: </td>";
		echo "<td>";
		echo "<select name=CategoryID>";
		$query = "SELECT CategoryID FROM `category`";
		$result = mysqli_query($con,$query);
		while($row = mysqli_fetch_assoc($result))
		{
			echo "<option>".$row['CategoryID']."</option>";
		}
		echo "</select>";
		echo "</td>";
	echo "</tr>";
	
	echo "<tr>";
	echo "<td>";
	echo "</td>";
	echo "<td>";
		echo "<input type='submit' name=themsanpham value='Thêm Sản Phẩm'/>";
	echo "</td>";
	echo "</tr>";
	
echo "</table>";
echo "</form>";
	mysqli_close($con);
?>
</table>
</body>
</html>