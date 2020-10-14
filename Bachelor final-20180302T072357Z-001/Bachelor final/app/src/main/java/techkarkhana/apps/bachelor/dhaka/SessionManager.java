package techkarkhana.apps.bachelor.dhaka;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by puchi on 08-Feb-18.
 */

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context con;
    // private static final String PREF_NAME = "AndroidHivePref";



    public SessionManager(Context con) {
        this.con= con;
        pref = con.getSharedPreferences("myapp",Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean("loggedinmood", isLoggedIn);
        //editor.putString("username",String.valueOf(App.USER.Firstname));
        //editor.putString("mobile",String.valueOf(App.USER.Mobileno));

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean("loggedinmood", false);
    }




}
