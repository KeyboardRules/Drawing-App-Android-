<?php
require"init.php";
$image_id=$_POST["image_id"];
$image_name=$_POST["image_name"];
$image_data=$_POST["image_data"];


$sql_query="CALL `update_picture`('$image_id','$image_name','$image_data');";

$result=mysqli_query($con,$sql_query);

if($result)
{
echo"update_success";
}
else{
echo"update_fail";
}
mysqli_close($con);
?>