<?php
require"init.php";
$user_name=$_POST["login_name"];
$user_pass=$_POST["login_password"];

$sql_query="CALL `login_user`('$user_name', '$user_pass');";

$result=mysqli_query($con,$sql_query);

if(mysqli_num_rows($result)>0)
{
$row=mysqli_fetch_assoc($result);
$id=$row["user_id"];
echo $id;
}
else{
echo "non_exist";
}
mysqli_close($con);
?>