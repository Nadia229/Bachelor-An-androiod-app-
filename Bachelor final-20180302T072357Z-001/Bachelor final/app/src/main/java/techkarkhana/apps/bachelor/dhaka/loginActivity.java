package techkarkhana.apps.bachelor.dhaka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class loginActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    TextView text1,text2,sign1,sign2;
    EditText _MobilenoText;
    EditText _passwordText;
    Button _loginButton;

    //session
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text1 = (TextView) findViewById(R.id.tx1);
        text1.setPaintFlags(text1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        sign1 = (TextView) findViewById(R.id.sign_up1);
        sign1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(),signupActivity.class);
                startActivity(intent);
            }
        });

        _MobilenoText=(EditText)findViewById(R.id.numberLogin);
        _passwordText=(EditText)findViewById(R.id.passwordLogin);

        _loginButton = (Button) findViewById(R.id.login);


        pref = this.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = pref.edit();



        //here controlling login status
        session=new SessionManager(this);

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to order activity
            Intent intent = new Intent(loginActivity.this, Order.class);
            startActivity(intent);
            //finish();
        }


        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();


            }
        });

    }

    // login check

    public void login()
    {
        Log.d(TAG,"Login");
        if(!validate())
        {
            onLoginFailed("Validate All");
            return;
        }

        String Mobileno=_MobilenoText.getText().toString();
        String password=_passwordText.getText().toString();

        try {
            //String url = "http://192.168.0.103/api/login.php?MobileNumber="+Mobileno+"&Password="+password;

            String url = "http://192.168.0.3/api/login.php?MobileNumber="+Mobileno+"&Password="+password;

            url=url.replaceAll(" ","20%");
            Log.e(TAG,url);

            StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    Log.d("Response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("error") == "true") {
                            onLoginFailed(jsonObject.getString("error_msg").toString());
                        } else {
                            onLoginSuccess(response);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onLoginFailed("Network error");
                }
            });
            RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
            rq.add(sr);
        }catch (Exception ex)
        {
            Snackbar.make(findViewById(android.R.id.content),"Network error", Snackbar.LENGTH_LONG).setActionTextColor(Color.RED).show();
        }

    }

    // empty check

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==REQUEST_SIGNUP)
        {
            if (resultCode==RESULT_OK)
            {
                Snackbar.make(findViewById(android.R.id.content),"Please Login", Snackbar.LENGTH_LONG).setActionTextColor(Color.RED).show();
            }
        }
    }

    //back stress make true

    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    // login pass check

    public void onLoginSuccess(String response)
    {
        try{
            JSONObject jsonObject = new JSONObject(response);
            Log.e("response",response);
            JSONObject userjson = new JSONObject(jsonObject.getString("user_info"));
            //http://192.168.0.102/android/insert.php
            Log.e("response",response);
            App.initUser();

            App.USER.Mobileno=userjson.getString("MobileNumber");
            //pref = getSharedPreferences("mobile",String.valueOf(App.USER.Mobileno))
            App.USER.Firstname=userjson.getString("FirstName");
            Log.e("MobileNumber",App.USER.Mobileno);
            App.USER.Password=userjson.getString("Password");
            App.USER.User_id=userjson.getString("User_ID");
            Log.e("User_id",App.USER.User_id);
            Log.d("mess",App.USER.Mobileno+App.USER.Password);
            //getuserid();
            if(App.USER.Mobileno.equals(_MobilenoText.getText().toString()))
            {
                editor.putString("mobile", App.USER.Mobileno);
                editor.putString("username", App.USER.Firstname);
                editor.putString("userid", App.USER.User_id);
                editor.commit();
                session.setLogin(true);
                startActivity(new Intent(getApplicationContext(),Order.class));

            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    //log in failarity checking

  /* public void getuserid()
    {
        try {
            // Israt ensure the link is working
           // String url ="http://192.168.0.103/api/paymentfind.php?User_ID="+App.USER.User_id;

            String url ="http://techkarkhana.com/baachelor/paymentfind.php?User_ID="+App.USER.User_id;


            url=url.replaceAll(" ","20%");
            Log.e(TAG,url);

            StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    Log.d("Response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("error") == "true") {
                            onOrderFailed(jsonObject.getString("error_msg").toString());
                        } else {
                            onPaymentSuccess(response);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onOrderFailed("Network error");
                }
            });
            RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
            rq.add(sr);
        }catch (Exception ex)
        {
            Snackbar.make(findViewById(android.R.id.content),"Network error", Snackbar.LENGTH_LONG).setActionTextColor(Color.RED).show();
        }


    }
    public void onOrderFailed(String error)
    {
        Toast.makeText(getBaseContext(),error,Toast.LENGTH_LONG);

    }
    public void onPaymentSuccess(String response)
    {
        try{
            JSONObject jsonObject = new JSONObject(response);
            Log.e("response",response);
            JSONObject userjson = new JSONObject(jsonObject.getString("b_order"));
            Log.e("response",response);
            App.initOrder();

            App.ORDERSET.total_meal=userjson.getString("Total_Meal");
            //Log.d("total meal",App.ORDERSET.total_meal);
            App.ORDERSET.to_pay=userjson.getString("To_Pay");
            //Log.d("to pay",App.ORDERSET.to_pay);
            App.ORDERSET.meal_rate=userjson.getString("Meal_Rate");
            //Log.d("meal rate",App.ORDERSET.total_meal);
            App.ORDERSET.paid_amount=userjson.getString("Paid_Amount");
            editor.putString("totalmeal", App.ORDERSET.total_meal);
            editor.commit();


        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }*/

    public void onLoginFailed(String error)
    {
        Toast.makeText(getBaseContext(),error,Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    //check mobileno and passwoed vaildity

    public boolean validate()
    {
        boolean valid =true;
        String mobileno=_MobilenoText.getText().toString();
        String password=_passwordText.getText().toString();

        if(mobileno.isEmpty())
        {
            valid=false;
        }
        else
        {
            _MobilenoText.setError(null);
        }
        if(password.isEmpty() || password.length()<4 || password.length()>13)
        {
            valid=false;
        }
        else
        {
            _passwordText.setError(null);
        }
        return valid;
    }


}
