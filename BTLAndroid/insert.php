<?php
require"init.php";
$user_id=$_POST["user_id"];
$image_id=$_POST["image_id"];
$image_name=$_POST["image_name"];
$image_data=$_POST["image_data"];
$image_date=$_POST["image_date"];


$sql="CALL `insert_picture`('$user_id', '$image_id', '$image_name', '$image_data', '$image_date');";
$result=mysqli_query($con,$sql);
if($result){
echo"insert_success";
}
else{
echo "insert_fail";
}
mysqli_close($con);

?>