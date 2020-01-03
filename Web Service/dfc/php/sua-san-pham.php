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
	if(isset($_POST['suasanpham']))
	{
		$query1 = "Update `products` 
				   Set ProductName='$_POST[ProductName]',
					   Detail='$_POST[Detail]',
					   Price='$_POST[Price]',
					   Avatar='$_POST[Avatar]'
				   Where ProductID='$_POST[ProductID]'";
		$result = mysqli_query($con,$query1);
	}
echo "<table border=1>";
echo "<tr>";
echo "<th>Mã sản phẩm</th>";
echo "<th>Tên Sản phẩm</th>";
echo "<th>Thông tin sản phâm</th>";
echo "<th>Giá sản phẩm</th>";
echo "<th>Hình ảnh sản phẩm</th>";
echo "<th>Mã danh mục sản phẩm</th>";
echo "</tr>";
	$query = "SELECT * FROM `products`";
	$result = mysqli_query($con,$query);
	while($row = mysqli_fetch_array($result))
	{
		echo "<form action=sua-san-pham.php method = post>";
		echo "<tr>";
				echo "<td><input type='text' name=ProductID readonly='readonly' value=".$row['ProductID']."></td>";
				echo "<td><input type='text' name=ProductName value='".$row['ProductName']."'></td>";
				echo "<td><input type='text' name=Detail value='".$row['Detail']."'></td>";
				echo "<td><input type='number' name=Price value='".$row['Price']."'></td>";
				echo "<td><input type='text' name=Avatar value='".$row['Avatar']."'></td>";
				echo "<td><input type='text' name=CategoryID readonly='readonly' value=".$row['CategoryID']."></td>";
				echo "<td><input type='submit' name=suasanpham value='Sửa Sản Phẩm'/></td>";
		echo "</tr>";
		echo "</form>";
	}
echo "</table>";

	mysqli_close($con);

?>
</table>
</body>
</html>






<?php
/*
function getID()
{
	$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE);
	mysqli_set_charset($con,'utf8');
	$query = "SELECT CategoryID FROM `category`";
	$result = mysqli_query($con,$query);
	while($row = mysqli_fetch_assoc($result))
	{
		$category_id[] = array($row['CategoryID']);
	}
	echo "<select name='CategoryID'>";
					for($i = 0; $i < count($category_id); $i++)
					{
						echo "<option>".implode("",$category_id[$i])."</option>";
					}
	echo "</select>";
	mysqli_close($con);
}
*/
?>