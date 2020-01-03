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
	
	if(isset($_POST['duyetdon']))
	{
		$query1 = "UPDATE `orders` 
					  SET `OrderStatus` = '1'
					  WHERE OrderID = '$_POST[OrderID]'";
		$result = mysqli_query($con,$query1);
	}
	if(isset($_POST['giaohang']))
	{
		$query1 = "UPDATE `orders` 
					  SET `OrderStatus` = '2'
					  WHERE OrderID = '$_POST[OrderID]'";
		$result = mysqli_query($con,$query1);
	}
				$query = "SELECT * FROM `orders`";
				$result = mysqli_query($con,$query);
				echo "<table border = 1>";
				while($row = mysqli_fetch_assoc($result))
				{	
				echo "<tr>";
				echo "<td>";
					echo "<form action=form.php method = post>";
					echo "<input type='text' name=OrderID readonly='readonly' value=".$row['OrderID']."></br>";
					echo "UserID: ".$row["UserID"]."</br>";
					echo "OrderDate: ".$row["OrderDate"]."</br>";
					echo "Address: ".$row["Address"]."</br>";
					echo "Phone: ".$row["Phone"]."</br>";
					echo "Price: ".$row["Price"]."</br>";
					echo "OrderStatus: ".$row["OrderStatus"]."</br>";
				echo "</td>";
				echo "<td>";
					echo "<input type='submit' name=duyetdon value='Duyệt Đơn Hàng'/>";
					echo "</br>";
					echo "</br>";
					echo "<input type='submit' name=giaohang value='Giao Hàng'/>";
				echo "</td>";
					
					echo "</form>";
				}
				echo "</table>";
				mysqli_close($con);
	?>
</table>
</body>
</html>
