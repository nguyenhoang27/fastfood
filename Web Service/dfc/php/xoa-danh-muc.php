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
	include 'config.php';
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE);
	mysqli_set_charset($con,'utf8');
	if(isset($_POST['xoadanhmuc']))
	{
		$query1 = "Delete from `category`
				   Where CategoryID='$_POST[CategoryID]'";
		$result = mysqli_query($con,$query1);
	}
echo "<table border=1>";
echo "<tr>";
echo "<th>Mã danh mục</th>";
echo "<th>Tên danh mục</th>";
echo "<th>Thông tin danh mục</th>";
echo "</tr>";
	$query = "SELECT * FROM `category`";
	$result = mysqli_query($con,$query);
	while($row = mysqli_fetch_array($result))
	{
		echo "<form action=xoa-danh-muc.php method = post>";
		echo "<tr>";
				echo "<td><input type='text' name=CategoryID readonly='readonly' value=".$row['CategoryID']."></td>";
				echo "<td><input type='text' name=Name value='".$row['Name']."'></td>";
				echo "<td><input type='text' name=Detail value='".$row['Detail']."'></td>";
				echo "<td><input type='submit' name=xoadanhmuc value='Xóa Danh Mục'/></td>";
		echo "</tr>";
		echo "</form>";
	}
echo "</table>";

	mysqli_close($con);

?>
</table>
</body>
</html>