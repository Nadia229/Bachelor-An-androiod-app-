<?php
if(isset($_POST['search']))
{
	
	$Search = $_POST['Search'];
	$query ="SELECT * FROM `user_info` WHERE CONCAT(`Name`, `Area`, `Road`, `House`, `Flat`, `ID`)LIKE '%".$Search."%'";
	$result = filterTable($query);
}
else{
	
	$query = "SELECT * FROM `user_info`";
	$result = filterTable($query);
	
}

if(isset($_POST['search']))
{
	
	$Search = $_POST['Search'];
	$query ="SELECT * FROM `b_order` WHERE CONCAT (`Lunch_Amount`, `Guest_Lunch_Amount`, `Dinner_amount`, `Guest_Dinner_Amount`, `Total_Lunch`, `Total_Dinner`, `Total_Meal`, `Total_cost`, `Meal_Rate`, `To_Pay`, `Paid_Amount`, `Date`, `Order_ID`)LIKE '%".$Search."%'";
	$result = filterTable($query);
}
else{
	
	$query = "SELECT * FROM `b_order`";
	$result = filterTable($query);
	
}

function filterTable($query){
	$conn = mysqli_connect("localhost","root","","bachelor");
	$result = mysqli_query($conn, $query);
	return $result;
	
}


?>


<style>
table,tr,th,td
{
	
	border: solid black;
}

</style>

<form action = "admin.php" method="POST">

<input type="text" name="Search" placeholder="Search"><br><br>
<input type="submit" name="search" value= "Filter"><br><br>


	<table>
		<tr>
			<th>User_ID</th>
			<th>Name</th>
			<th>Area</th>
			<th>Road</th>
			<th>House</th>
			<th>Flat</th>
			
			<th>Lunch_Amount</th>
			<th>Guest_Lunch_Amount</th>
			<th>Dinner_amount</th>
			<th>Guest_Dinner_Amount</th>
			<th>Total_Lunch</th>
			<th>Total_Dinner</th>
			<th>Total_Meal</th>
			<th>Total_cost</th>
			<th>Meal_Rate</th>
			<th>To_Pay</th>
			<th>Paid_Amount</th>
			<th>Date</th>
			<th>Order_ID</th>
			
			</tr>
		
		
		<?php while($row= mysqli_fetch_array($result)):?>
	<tr>
		
		<td><?php echo $row['User_ID'];?></td>
		<td><?php echo $row['Name'];?></td>
		<td><?php echo $row['Area'];?></td>
		<td><?php echo $row['Road'];?></td>
		<td><?php echo $row['House'];?></td>
		<td><?php echo $row['Flat'];?></td>
		
		
		<td><?php echo $row['Lunch Amount'];?></td>
		<td><?php echo $row['Guest Lunch'];?></td>
		<td><?php echo $row['Dinner Amount'];?></td>
		<td><?php echo $row['Guest Dinner'];?></td>
		<td><?php echo $row['Total Lunch'];?></td>
		<td><?php echo $row['Total Dinner'];?></td>
		<td><?php echo $row['Total Meal'];?></td>
		<td><?php echo $row['Total Cost'];?></td>
		<td><?php echo $row['Meal Rate'];?></td>
		<td><?php echo $row['To Pay'];?></td>
		<td><?php echo $row['Paid'];?></td>
		<td><?php echo $row['Date'];?></td>
		<td><?php echo $row['Order_ID'];?></td>
		
	</tr>
	
	<?php endwhile;?>
	</table>
</form>




