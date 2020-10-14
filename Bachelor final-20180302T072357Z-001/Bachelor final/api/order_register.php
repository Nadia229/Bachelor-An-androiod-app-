<?php
require_once  __DIR__.'\DB_Functions.php';
$db = new DB_Functions();

//json response array
$response=array("error" => FALSE);
 

if(isset($_GET['Lunch_Amount']) && isset($_GET['Guest_Lunch_Amount']) && isset($_GET['Dinner_Amount']) && isset($_GET['Guest_Dinner_Amount']) && isset($_GET['Total_Meal']) && isset($_GET['Total_cost']) && isset($_GET['Total_Lunch']) && isset($_GET['Total_Dinner'])&& isset($_GET['Meal_Rate'])&& isset($_GET['To_Pay']) && isset($_GET['Paid_Amount']) && isset($_GET['Date']) && isset($_GET['Time']) && isset($_GET['User_ID']) ){
   
//receiving   the post params
	$Lunch_Amount=$_GET['Lunch_Amount'];
	$Guest_Lunch_Amount=$_GET['Guest_Lunch_Amount'];
	$Dinner_Amount=$_GET['Dinner_Amount'];
	$Guest_Dinner_Amount=$_GET['Guest_Dinner_Amount'];
	$Total_Meal=$_GET['Total_Meal'];
	$Total_cost=$_GET['Total_cost'];
	$Total_Lunch=$_GET['Total_Lunch'];
	$Total_Dinner=$_GET['Total_Dinner'];
	$Meal_Rate=$_GET['Meal_Rate'];
	$To_Pay=$_GET['To_Pay'];
	$Paid_Amount=$_GET['Paid_Amount'];
	$Date=$_GET['Date'];
	$Time=$_GET['Time'];
	$User_ID=$_GET['User_ID'];
	



$order=$db->storeOrder($Lunch_Amount,$Guest_Lunch_Amount,$Dinner_Amount,$Guest_Dinner_Amount,$Total_Lunch,$Total_Dinner,$Total_Meal,$Total_cost,$Meal_Rate,$To_Pay,$Paid_Amount,$Date,$Time,$User_ID);
if($order){

	$response["error"] = FALSE;
	$response["b_order"]["Order_ID"]=$order["Order_ID"];
	$response["b_order"]["Lunch_Amount"]= $order["Lunch_Amount"];
	$response["b_order"]["Guest_Lunch_Amount"]= $order["Guest_Lunch_Amount"];
	$response["b_order"]["Dinner_Amount"]= $order["Dinner_Amount"];
	$response["b_order"]["Guest_Dinner_Amount"]= $order["Guest_Dinner_Amount"];
	$response["b_order"]["Total_Lunch"]=$order["Total_Lunch"];
	$response["b_order"]["Total_Dinner"]= $order["Total_Dinner"];
	$response["b_order"]["Total_Meal"]=$order["Total_Meal"];
	$response["b_order"]["Total_cost"]= $order["Total_cost"];
	
	$response["b_order"]["Meal_Rate"]= $order["Meal_Rate"];
	$response["b_order"]["To_Pay"]= $order["To_Pay"];
	$response["b_order"]["Paid_Amount"]= $order["Paid_Amount"];
	$response["b_order"]["Date"]= $order["Date"];
	$response["b_order"]["Time"]=$order["Time"];
	$response["b_order"]["User_ID"]=$order["User_ID"];
	//$response["user_info"]["User_ID"]= $order["User_ID"];
	
	
	
	
echo json_encode($response);

}
else{
//user failed to store

	$response["error"]=TRUE;
	$response["error_msg"]="Unknown error occured in order!";
	echo json_encode($response);
    }

    
  }
else{
	$response["error"]=TRUE;
	$response["error_msg"]="Required parameters(Lunch_Amount,Guest_Lunch_Amount,Dinner_Amount,Guest_Dinner_Amount,Date,Time,User_ID)is missing";
	echo json_encode($response);

}


?>