<?php
class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }

/**
*Storing new user
*returns user details
*/
public function storeUser($FirstName,$LastName,$Email,$Local_Guardians_Name,$Guardians_Mobile_No,$Birth_Certificate_No,$National_ID_Card_No,$University_Name,$University_ID,$Company_Name,$Employ_ID,$MobileNumber,$Password,$Area,$House,$Flat,$Road){
$stmt = $this->conn->prepare("INSERT INTO user_info(FirstName,LastName,Email,Local_Guardians_Name,Guardians_Mobile_No,Birth_Certificate_No,National_ID_Card_No,University_Name,University_ID,Company_Name,Employ_ID,MobileNumber,Password,Area,House,Flat,Road) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
$stmt->bind_param("sssssddsdsdssssss",$FirstName,$LastName,$Email,$Local_Guardians_Name,$Guardians_Mobile_No,$Birth_Certificate_No,$National_ID_Card_No,$University_Name,$University_ID,$Company_Name,$Employ_ID,$MobileNumber,$Password,$Area,$House,$Flat,$Road);
$result=$stmt->execute();
$stmt->close();



if($result){

 if($stmt=$this->conn->prepare("SELECT *FROM user_info WHERE MobileNumber= ?"))
 {
    $stmt->bind_param("s",$MobileNumber);
    $stmt->execute();
    $user=$stmt->get_result()->fetch_assoc();
    $stmt->close();

    return $user;
}
else
{
echo " DATABAse query error";

}
}
else{
return false;
	}
}

/**
*get user by mobileNumber and password
*/

public function getUserByMobileNumberAndPassword($MobileNumber,$Password){

$stmt=$this->conn->prepare("SELECT * FROM user_info WHERE MobileNumber= ?");
$stmt->bind_param("s",$MobileNumber);

if($stmt->execute()){
		$user=$stmt->get_result()->fetch_assoc();
		$stmt->close();

//verifying user password-

	$server_Password=$user['Password'];

//check for password equality

	if($server_Password==$Password){

//user authentication details are correct

	return $user;

	}
   }
else{

return NULL;
	}
  }

public function getUserByMobileNumber($MobileNumber){
$stmt=$this->conn->prepare("SELECT * FROM user_info WHERE MobileNumber = ?");
$stmt->bind_param("s",$MobileNumber);

	if($stmt->execute()){
$user= $stmt->get_result()->fetch_assoc();
$stmt->close();
return $user;
	}



else{
return NULL;
	}
 }

/**
*check user is existed or not
*/


public function isUserExisted($MobileNumber){

$stmt=$this->conn->prepare("SELECT MobileNumber from user_info WHERE MobileNumber=?");
$stmt->bind_param("s",$MobileNumber);
$stmt->execute();
$stmt->store_result();

	if($stmt->num_rows>0){
//user existed
	$stmt->close();
return true;
}
else{
//user not existed 
$stmt->close();
return false;
	}
}

public function getAllUser(){
$stmt=$this->conn->prepare("SELECT * FROM user_info");

	if($stmt->execute()){
$rows=array();
$result=$stmt->get_result();
while($r=$result->fetch_assoc()){

$rows[]=$r;
}

print json_encode($rows);
$stmt->close();
}
 else{

return NULL;

}
}

public function updateUser($User_ID,$FirstName,$LastName,$Gender,$Email,$Local_Guardians_Name,$Guardians_Mobile_No,$National_ID_Card_No,$University_Name,$University_ID,$Company_Name,$Employ_ID,$MobileNumber,$Password,$Area,$House,$Flat){

$stmt= $this->conn->prepare("update user_info set FirstName=?,LastName=?,Gender=?,Email=?,Local_Guardians_Name=?,Guardians_Mobile_No=?,National_ID_Card_No=?,University_Name=?,University_ID=?,Company_Name=?,Employ_ID=?,MobileNumber=?,Password=?,Area=?,House=?,Flat=? where ID=?");
$stmt->bind_param("ssssssdsdsdsssssd",$FirstName,$LastName,$Gender,$Email,$Local_Guardians_Name,$Guardians_Mobile_No,$National_ID_Card_No,$University_Name,$University_ID,$Company_Name,$Employ_ID,$MobileNumber,$Password,$Area,$House,$Flat,$ID);
$result=$stmt->execute();
$stmt->close();


//check for successful store
if($result){
return 1;
}
else{
return 0;
}
}






//**************************store_order*************************
public function storeOrder($Lunch_Amount,$Guest_Lunch_Amount,$Dinner_Amount,$Guest_Dinner_Amount,$Total_Lunch,$Total_Dinner,$Total_Meal,$Total_cost,$Meal_Rate,$To_Pay,$Paid_Amount,$Date,$Time,$User_ID)
{
$stmt = $this->conn->prepare("INSERT INTO b_order(Lunch_Amount,Guest_Lunch_Amount,Dinner_Amount,Guest_Dinner_Amount,Total_Lunch,Total_Dinner,Total_Meal,Total_cost,Meal_Rate,To_Pay,Paid_Amount,Date,Time,User_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
$stmt->bind_param("dddddddddddssd",$Lunch_Amount,$Guest_Lunch_Amount,$Dinner_Amount,$Guest_Dinner_Amount,$Total_Lunch,$Total_Dinner,$Total_Meal,$Total_cost,$Meal_Rate,$To_Pay,$Paid_Amount,$Date,$Time,$User_ID);
$result=$stmt->execute();
$stmt->close();


 if($result){

 if($stmt=$this->conn->prepare("SELECT *FROM b_order WHERE Lunch_Amount= ?"))
 {
    $stmt->bind_param("d",$Lunch_Amount);
    $stmt->execute();
    $user=$stmt->get_result()->fetch_assoc();
    $stmt->close();

    return $user;
}
else
{
echo " DATABAse query error";

}
}
else{
return false;
	}
}


//230***********************payment status**********************
public function getOrderByPaymentStatus($Total_Meal,$Total_cost,$Total_Lunch,$Total_Dinner,$Meal_Rate,$To_Pay,$Paid_Amount,$User_ID){
$stmt=$this->conn->prepare("SELECT * FROM b_order WHERE User_ID = ?");
$stmt->bind_param("s",$User_ID);

if($stmt->execute())
{
		$user=$stmt->get_result()->fetch_assoc();
		$stmt->close();
	        return $user;

}
else{

return NULL;
	}
  }




public function getOrderByPaymentStatusbefore(){
$stmt=$this->conn->prepare("SELECT * FROM b_order where Total_Meal=(select max(Total_Meal) from b_order)");
//$stmt->bind_param("s",$User_ID);

if($stmt->execute())
{
		$user=$stmt->get_result()->fetch_assoc();
		$stmt->close();
	        return $user;

}
else{

return NULL;
	}
  }



 //*********deleteOrder************

/*public function deleteOrder($Order_ID){
	
	$stmt= $this->conn->prepare(DELETE FROM b_order WHERE User_ID=?);
	$stmt->bind_param("d",$Order_ID);
	$result= $stmt->execute();
	$stmt->close();
	
	if($result){
		
		return "1";
	}
	
	else{
		return "0";
	}
}*/




}
?>