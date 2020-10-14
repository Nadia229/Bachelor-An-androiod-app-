package techkarkhana.apps.bachelor.dhaka;

/**
 * Created by ASUS on 07-Feb-18.
 */

public class OrderSet {

    public OrderSet(){}

    public OrderSet(String total_meal,String to_pay,String meal_rate,String paid_amount)
    {
        this.total_meal=total_meal;
        this.to_pay=to_pay;
        this.meal_rate=meal_rate;
        this.paid_amount=paid_amount;

    }

    public String getTotal_meal() {return total_meal;
    }

    public void setTotal_meal(String total_meal) {
        this.total_meal = total_meal;
    }

    public String getTo_pay() {
        return to_pay;
    }

    public void setTo_pay(String to_pay) {
        this.to_pay = to_pay;
    }


    public String getMeal_rate() {
        return meal_rate;
    }

    public void setMeal_rate(String meal_rate) {
        this.meal_rate = meal_rate;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }


    public String to_pay,meal_rate,paid_amount;
    public String total_meal;
    public int _total_meal;
}
