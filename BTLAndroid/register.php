<?php
require"init.php";
$user_name=$_POST["user_name"];
$user_password=$_POST["user_password"];
$sql="CALL `check_user`('$user_name');";

$result=mysqli_query($con,$sql);
if(mysqli_num_rows($result)>0){
echo"exist";
exit();
}
mysqli_free_result($result);
mysqli_next_result($con);

$sql_query="CALL `insert_user`('$user_name','$user_password');";
$result=mysqli_query($con,$sql_query);
if($result){
echo "register_success";
}
else{
echo "register_fail";
}

mysqli_close($con);
?>