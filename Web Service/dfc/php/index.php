<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Admin Page</title>
</head>
<style>
    div{
      position:fixed;
      width:200px;
      height:200px;
      left: 50%;
      margin-left: -100px;
      top: 50%;
      margin-top: -100px; 
    }
	body{
		background-image:url(bg.jpg);
		background-attachment:fixed;
		background-repeat: repeat;
	}
 </style>
<body>
<div>
<table border=1>
	<tr>
		<td  colspan="3" align="center">
			<form action="form.php">
			<input type="submit"  value="Quản Lý Đơn Hàng"/>
			</form>
		</td>
	</tr>
	
	<tr>
		<td>
			<form action="them-san-pham.php">
			<input type="submit"  value="Thêm Sản Phẩm"/>
			</form>
		</td>
		
		<td>
			<form action="sua-san-pham.php">
			<input type="submit"  value="Sửa Sản Phẩm"/>
			</form>
		</td>
			
		<td>
			<form action="xoa-san-pham.php">
			<input type="submit"  value="Xóa Sản Phẩm"/>
			</form>
		</td>
	</tr>
		<td>
			<form action="them-danh-muc.php">
			<input type="submit"  value="Thêm Danh Mục"/>
			</form>
		</td>
		
		<td>
			<form action="sua-danh-muc.php">
			<input type="submit"  value="Sửa Danh Mục"/>
			</form>
		</td>
			
		<td>
			<form action="xoa-danh-muc.php">
			<input type="submit"  value="Xóa Danh Mục"/>
			</form>
		</td>
	<tr>
	</tr>
</table>
</div>
</body>
</html>
