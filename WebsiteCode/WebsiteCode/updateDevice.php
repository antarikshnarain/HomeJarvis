<?php
if($_GET["deviceid"] || $_GET["pin"]){
	$con=mysqli_connect("arduinoyuniot.cwlgwf0etvez.ap-southeast-1.rds.amazonaws.com","arduinoyunpro","arduinoyunpro","iot");
	if (!$con) {
		die("Connection failed: " . mysqli_connect_error());
	}
	$myid=$_GET["deviceid"];
	$mystatus=$_GET["status"];
	$mypin=$_GET["pin"];
	#echo $myid.",".$mypin.",".$mystatus;
	$sql="UPDATE deviceStatus set status=$mystatus and pin=$mypin where deviceid=$myid";
	if (mysqli_query($con,$sql)) {
		echo "1";
	}
	else{
		echo "0";
	}
	mysqli_close($con);
}
?>
