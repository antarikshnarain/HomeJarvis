<?php
   $con=mysqli_connect("arduinoyuniot.cwlgwf0etvez.ap-southeast-1.rds.amazonaws.com","arduinoyunpro","arduinoyunpro","iot");
   if (!$con) {
	die("Connection failed: " . mysqli_connect_error());
   }
   $sql="SELECT * FROM deviceStatus";
   $result = mysqli_query($con,$sql);
   if(mysqli_num_rows($result)>0){
	   while($row=mysqli_fetch_assoc($result)){
		   echo $row["deviceid"].",".$row["status"].",".$row["timer"].",".$row["pin"]."<br/>";
	   }
   }
   else{
	   echo "0";
   }
   mysqli_close($con);
?>
