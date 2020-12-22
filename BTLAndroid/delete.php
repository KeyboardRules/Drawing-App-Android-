<?php
require"init.php";
$image_id=$_POST["image_id"];

$sql_query="CALL `delete_picture`('$image_id');";

$result=mysqli_query($con,$sql_query);

if($result)
{
echo"delete_success";
}
else{
echo"delete_fail";
}
mysqli_close($con);
?>