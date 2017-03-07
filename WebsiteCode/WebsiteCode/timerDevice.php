<?php
if(isset($_GET["timer"])){
	$con=mysqli_connect("arduinoyuniot.cwlgwf0etvez.ap-southeast-1.rds.amazonaws.com","arduinoyunpro","arduinoyunpro","iot");
	if (!$con) {
		die("Connection failed: " . mysqli_connect_error());
	}
	$myid=$_GET["deviceid"];
	$mytimer=$_GET["timer"];
	#echo $myid.",".$mytimer;
	$sql="UPDATE deviceStatus set timer=$mytimer where deviceid=$myid";
	if (mysqli_query($con,$sql)) {
		echo "1";
	}
	else{
		echo "0";
	}
	mysqli_close($con);
}
?>
