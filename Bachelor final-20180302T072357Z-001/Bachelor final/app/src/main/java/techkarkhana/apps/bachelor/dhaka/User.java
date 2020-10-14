package techkarkhana.apps.bachelor.dhaka;

/**
 * Created by ASUS on 01-Feb-18.
 */

public class User {


    public User() {
    }

    public User(String Mobileno, String Password, String user_id, String Firstname) {


        this.Mobileno = Mobileno;
        this.Password = Password;
        this.User_id = user_id;
        this.Firstname=Firstname;


    }


    public String getName() {
        return Mobileno;
    }

    public void setName(String name) {
        this.Mobileno = name;
    }

    public String getmUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        this.User_id = user_id;
    }


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String User_id, Password, Mobileno,Firstname;


}