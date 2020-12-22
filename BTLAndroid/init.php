<?php
$db_name='paintx';
$server_name='localhost';
$mysql_user='root';
$mysql_pass='';

$con=mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
if(!$con)
{
exit();
}
?>