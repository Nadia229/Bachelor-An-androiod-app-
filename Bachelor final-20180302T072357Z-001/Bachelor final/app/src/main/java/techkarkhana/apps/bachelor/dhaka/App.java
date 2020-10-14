package techkarkhana.apps.bachelor.dhaka;

/**
 * Created by ASUS on 01-Feb-18.
 */


public class App {
    public  static String NAME="USER_INFO_MOBILE_NO";
    public  static String Mobile_No="USER_INFO_PASSWORD";
    public  static String User_id="USER_INFO_User_ID";
    public  static String Firtstname="USER_INFO_FIRST_NAME";
    public static String Total_Meal="B_ORDER_TOTAL_MEAL";
    public static String To_Pay="B_ORDER_TO_PAY";
    public static String Meal_Rate="B_ORDER_MEAL_RATE";
    public static String Paid_Amount="B_ORDER_PAID_AMOUNT";

    public static OrderSet ORDERSET;

    public  static User USER;



    public static void  initUser()
    {
        USER=new User();
    }


    public static void initOrder() {
        ORDERSET = new OrderSet();
    }


}
