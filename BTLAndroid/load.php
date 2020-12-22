<?php
require"init.php";
$user_id=$_POST["user_id"];

$res=array();

$sql="CALL `user_picture`('$user_id');";
$result=mysqli_query($con,$sql);
if(mysqli_num_rows($result)>0){
$res['success']=1;
$pics=array();
while($row=mysqli_fetch_assoc($result)){
array_push($pics,$row);
}
$res['pics']=$pics;
}
else{
$res['success']=0;
}
echo json_encode($res);
mysqli_close($con);
?>
