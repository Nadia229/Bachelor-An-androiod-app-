<?php
require_once  __DIR__.'\DB_Functions.php';
$db = new DB_Functions();

//json response array
$response=array("error" => FALSE);
 echo"if will enter";

if(isset($_GET['FirstName']) && isset($_GET['LastName']) && isset($_GET['Email']) && isset($_GET['Local_Guardians_Name']) && isset($_GET['Guardians_Mobile_No']) && isset($_GET['Birth_Certificate_No'])&& isset($_GET['National_ID_Card_No'] ) && isset($_GET['University_Name']) && isset($_GET['University_ID']) && isset($_GET['Company_Name']) && isset($_GET['Employ_ID'])&& isset($_GET['Password']) && isset($_GET['Area'])&& isset($_GET['House'])&& isset($_GET['Flat'])&& isset($_GET['Road']) && isset($_GET['MobileNumber'])){

//receiving   the post params
	$FirstName=$_GET['FirstName'];
	$LastName=$_GET['LastName'];
	
	$Email=$_GET['Email'];
	$Local_Guardians_Name=$_GET['Local_Guardians_Name'];
	$Guardians_Mobile_No=$_GET['Guardians_Mobile_No'];
	$Birth_Certificate_No=$_GET['Birth_Certificate_No'];
	$National_ID_Card_No=$_GET['National_ID_Card_No'];
	$University_Name=$_GET['University_Name'];
	$University_ID=$_GET['University_ID'];
	$Company_Name=$_GET['Company_Name'];
	$Employ_ID=$_GET['Employ_ID'];
	$MobileNumber=$_GET['MobileNumber'];
	$Password=$_GET['Password'];
	$Area=$_GET['Area'];	
	$House=$_GET['House'];
	$Flat=$_GET['Flat'];
	$Road=$_GET['Road'];
	
	

//check if user is already existed with the same phone number

	if($db->isUserExisted($MobileNumber)){
        //user already existed
	
$response["error"] = TRUE;
$response["error_msg"] = "User already existed with".$MobileNumber;
echo json_encode($response);
}

else{


//create a new user
$user=$db->storeUser($FirstName,$LastName,$Email,$Local_Guardians_Name,$Guardians_Mobile_No,$Birth_Certificate_No,$National_ID_Card_No,$University_Name,$University_ID,$Company_Name,$Employ_ID,$MobileNumber,$Password,$Area,$House,$Flat,$Road);
if($user){

	$response["error"] = FALSE;
	$response["user_info"]["User_ID"]= $user["User_ID"];
	$response["user_info"]["FirstName"]= $user["FirstName"];
	$response["user_info"]["LastName"]= $user["LastName"];
	$response["user_info"]["Email"]= $user["Email"];
	$response["user_info"]["Local_Guardians_Name"]= $user["Local_Guardians_Name"];
	$response["user_info"]["Guardians_Mobile_No"]= $user["Guardians_Mobile_No"];
	$response["user_info"]["Birth_Certificate_No"]= $user["Birth_Certificate_No"];
	$response["user_info"]["National_ID_Card_No"]= $user["National_ID_Card_No"];
	$response["user_info"]["University_Name"]= $user["University_Name"];
	$response["user_info"]["University_ID"]= $user["University_ID"];
	$response["user_info"]["Company_Name"]= $user["Company_Name"];
	$response["user_info"]["Employ_ID"]= $user["Employ_ID"];
	$response["user_info"]["MobileNumber"]= $user["MobileNumber"];
	$response["user_info"]["Password"]= $user["Password"];
	$response["user_info"]["Area"]= $user["Area"];
	$response["user_info"]["House"]= $user["House"];
	$response["user_info"]["Flat"]= $user["Flat"];
	$response["user_info"]["Road"]=$user["Road"];
	

	
echo json_encode($response);

}
else{
//user failed to store

	$response["error"]=TRUE;
	$response["error_msg"]="Unknown error occured in registration!";
	echo json_encode($response);
    }

    }
  }
else{
	$response["error"]=TRUE;
	$response["error_msg"]="Required parameters(FirstName,LastName,Email,Local_Guardians_Name,Guardians_Mobile_No,Birth_Certificate_No,National_ID_Card_No,MobileNumber,Password,Area,House,Flat,Road)is missing";
	echo json_encode($response);
}


?>