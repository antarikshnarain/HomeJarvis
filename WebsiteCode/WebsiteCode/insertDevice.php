<?php
if(isset($_GET["pin"])){
   $con=mysqli_connect("arduinoyuniot.cwlgwf0etvez.ap-southeast-1.rds.amazonaws.com","arduinoyunpro","arduinoyunpro","iot");
   if (!$con) {
	die("Connection failed: " . mysqli_connect_error());
   }
   $mypin=$_GET["pin"];
   $sql="INSERT INTO deviceStatus (pin) VALUES ($mypin)";
   if (mysqli_query($con,$sql)) {
	  echo "1,";
	   $sql="SELECT deviceid FROM deviceStatus ORDER BY deviceid DESC limit 1";
	   $result = mysqli_query($con,$sql);
	   $row=mysqli_fetch_assoc($result);
	   $myid=$row["deviceid"];
	   echo $myid;
   }
   else{
	   echo "0,";
   }
   mysqli_close($con);
}
?>
