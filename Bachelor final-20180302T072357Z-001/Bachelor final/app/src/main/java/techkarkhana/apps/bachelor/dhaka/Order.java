package techkarkhana.apps.bachelor.dhaka;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
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

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;

public class Order extends AppCompatActivity {
    DrawerLayout drawerlyout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public int Total_Cost;
    public String _total_meal,_to_pay,_meal_rate,_paid_amount,_name,User_ID;
    public int Paid_Amount,To_Pay,Total_Lunch,Total_Dinner,T_M,T_C,T_P;
    public static final int Meal_Rate=40;
    Switch _lunchSwitch,_dinerswitch;
    Button confirm;
    Spinner _lunchspinner,_dinerspinner;
    CheckBox lunchcheckBox,dinercheckBox;
    String lunchspinner,dinerspinner;
    TextView text1,text2,textView1,textView2,textView3,textView4,textView5;
    public int lunchg=0,dinnerg=0;
    public  int Total_Meal,totalmeal;
    int hour,min;
    String AM_PM;
    Button place_order;

    int statusLunch =0;
    int statusDinner =0;


    SharedPreferences pref;
    SharedPreferences.Editor editor;


    long date = System.currentTimeMillis();

    final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        pref = this.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor=pref.edit();


        User_ID = pref.getString("userid", "100");

        totalmeal= pref.getInt("totalmeal", 0);


        // take input of date here
        _lunchSwitch=(Switch)findViewById(R.id.switch1);
        _dinerswitch=(Switch)findViewById(R.id.switch2);
        lunchcheckBox=(CheckBox)findViewById(R.id.checkBox1);
        dinercheckBox=(CheckBox)findViewById(R.id.checkBox2);
        _lunchspinner=(Spinner) findViewById(R.id.spinner1);
        _dinerspinner=(Spinner) findViewById(R.id.spinner2);


        text1 = (TextView) findViewById(R.id.textview1);
        text2=(TextView) findViewById(R.id.textview2);


        textView1=(TextView) findViewById(R.id.name);
        textView2=(TextView) findViewById(R.id.totalmeal);
        textView3=(TextView) findViewById(R.id.mealrate);
        textView4=(TextView) findViewById(R.id.topay);
        textView5=(TextView) findViewById(R.id.paidamount);

        confirm = (Button) findViewById(R.id.btn_confirm);
        place_order = findViewById(R.id.btn_order);



        _lunchspinner.setEnabled(false);
      _dinerspinner.setEnabled(false);




        //calling navigation method
        setup();
        //PaymentShow();

        // ok // Toast.makeText(Order.this, User_ID, Toast.LENGTH_SHORT).show();
        // ok // Toast.makeText(Order.this, _total_meal ,Toast.LENGTH_SHORT).show();

        //Toast.makeText(Order.this, _to_pay, Toast.LENGTH_SHORT).show();
        //* app off hoa jay //Toast.makeText(Order.this, Meal_Rate, Toast.LENGTH_SHORT).show();
        // khali ashe // Toast.makeText(Order.this, _paid_amount, Toast.LENGTH_SHORT).show();

        //confirm = (Button) findViewById(R.id.login);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //order time passsing into databse
                String ghonta = Integer.toString(hour);
                String minit = Integer.toString(min);
                String time1 = ghonta + ":" + minit;


                Boolean lunch = _lunchSwitch.isChecked();
                Boolean dinner = _dinerswitch.isChecked();
                Boolean lunchmeal = lunchcheckBox.isChecked();
                Boolean dinnermeal = dinercheckBox.isChecked();


                //check switch button is closed or on
                if (lunch == true || lunchmeal==true || dinner == true || dinnermeal==true) {


                    OrderConfirm();
                    PaymentShow();
                    startActivity(new Intent(Order.this, confirmActivity.class));
                    //finish();
                }

                else

                {
                    Toast.makeText(getApplicationContext(), "Please on.....",
                            LENGTH_SHORT).show() ;
                }
                _lunchSwitch.setChecked(false);
                _dinerswitch.setChecked(false);

                _lunchspinner.setSelection(0);
                _dinerspinner.setSelection(0);

                lunchcheckBox.setChecked(false);
                dinercheckBox.setChecked(false);


            }
        });



        lunchcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    text1.setTextColor(Color.BLACK);
                    _lunchspinner.setEnabled(true);
                } else {
                    text1.setTextColor(Color.GRAY);
                    _lunchspinner.setEnabled(false);

                }
            }

        });

        dinercheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    text2.setTextColor(Color.BLACK);
                    _dinerspinner.setEnabled(true);
                    //checkBox1.setBackgroundColor(Color.parseColor("#cbff75"));
                } else {
                    text2.setTextColor(Color.GRAY);
                    _dinerspinner.setEnabled(false);

                }
            }

        });



        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,itemList);
        ArrayAdapter  adapter = ArrayAdapter.createFromResource(Order.this, R.array.planets_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        _lunchspinner.setAdapter(adapter);
        _dinerspinner.setAdapter(adapter);

        //lunchspinner = _lunchspinner.getSelectedItem().toString();


        //lunchg = Integer.parseInt(lunchspinner);


        _lunchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


               String country = parent.getItemAtPosition(position).toString();
                lunchspinner = _lunchspinner.getSelectedItem().toString().trim();
                if (position>0) {


                    Toast.makeText(Order.this, country, LENGTH_SHORT).show();

                    try {
                        lunchg = Integer.parseInt(lunchspinner);
                    } catch (NumberFormatException e) {
                        lunchg = 0;
                        e.printStackTrace();
                    }
                }

               // Toast.makeText(Order.this, lunchspinner, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        _dinerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String country = parent.getItemAtPosition(position).toString();
                dinerspinner = _dinerspinner.getSelectedItem().toString().trim();

                if (position>0) {
                    try {


                        // NumberFormat nf = NumberFormat.getInstance(Locale.US);
                        dinnerg = Integer.parseInt(dinerspinner);
                        Toast.makeText(Order.this, "YOUR MESSAGE", LENGTH_SHORT).show();

                    } catch (NumberFormatException e) {
                        dinnerg = 0;
                        e.printStackTrace();
                    }
                }

            }

                 //Toast.makeText(Order.this,country, Toast.LENGTH_SHORT).show();



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ///Current time + check time + unable order


        Calendar c = Calendar.getInstance();

        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        int ds = c.get(Calendar.AM_PM);
        if(ds==1)
            AM_PM="pm";
        else
            AM_PM="am";


        if(((hour>=12&&min>0&&AM_PM.matches("pm")) && (hour<14&&min<59&&AM_PM.matches("pm"))))  {


            _lunchSwitch.setClickable(false);
            lunchcheckBox.setClickable(false);
        }

        else if((hour==11&&min>0&&AM_PM.matches("am")) && (hour==11&&min<59&&AM_PM.matches("am")) ) {
            _lunchSwitch.setClickable(false);
            lunchcheckBox.setClickable(false);
        }

        else if((hour>=9&&min>0&&AM_PM.matches("am")) && (hour<11&&min<59&&AM_PM.matches("am")) ) {
            _lunchSwitch.setClickable(false);
            lunchcheckBox.setClickable(false);
        }

        else if((hour>17&&min>0&&AM_PM.matches("pm")) && (hour<21&&min<59&&AM_PM.matches("pm")) ) {
            _dinerswitch.setClickable(false);
            dinercheckBox.setClickable(false);
        }
        else
        {

            _lunchSwitch.setClickable(true);
            _dinerswitch.setClickable(true);
            lunchcheckBox.setClickable(true);
            dinercheckBox.setClickable(true);

        }



        _lunchSwitch.setChecked(false);

        _lunchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    statusLunch=1; //edit here
                    Toast.makeText(getBaseContext(),"on", LENGTH_SHORT).show();
                }else{
                    statusLunch=0; //edit here
                }
            }
        });
        _dinerswitch.setChecked(false);
        _dinerswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    statusDinner = 1;

                    Toast.makeText(getBaseContext(),"on", LENGTH_SHORT).show();
                }
                else
                {
                    statusDinner = 0;
                }
            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //payment show on order page
                //PaymentShow();


               // _total_meal=pref.getString("totalmeal","nai");
                totalmeal= pref.getInt("totalmeal", 0);
                //passing order date into databse
                try {

                    T_M = (totalmeal) + statusLunch + lunchg + dinnerg + statusDinner;


                }
                catch(NumberFormatException e)
                {
                    e.printStackTrace();
                }

                T_C=T_M*Meal_Rate;
                T_P=T_C-Paid_Amount;

                _total_meal=Integer.toString(T_M);
                textView2.setText(_total_meal);

                _meal_rate=Integer.toString(Meal_Rate);
                textView3.setText(_meal_rate);

                _paid_amount=Integer.toString(Paid_Amount);
                textView5.setText(_paid_amount);

                _to_pay=Integer.toString(T_P);
                textView4.setText(_to_pay);


                _name=pref.getString("username","no name");
                textView1.setText(_name);


            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer_navigation);
        navigationView.setNavigationItemSelectedListener
                (
                        new NavigationView.OnNavigationItemSelectedListener()
                        {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item)
                            {

                                int id = item.getItemId();

                                if (id == R.id.about) {
                                    Intent intent = new Intent(Order.this, About.class);
                                    startActivity(intent);
                                }

                                if (id == R.id.set) {
                                    Intent intent = new Intent(Order.this, Setting.class);
                                    startActivity(intent);
                                }
                                if (id == R.id.faq) {
                                    Intent intent = new Intent(Order.this, Faq.class);
                                    startActivity(intent);
                                }
                                ///when clicking number calling from phone
                                if (id == R.id.number) {
                                    String a="01738145545";
                                    Intent callintent = new Intent(Intent.ACTION_DIAL);
                                    Intent intent = callintent.setData(Uri.parse("tel:"+a));
                                    startActivity(intent);



                                }



                                return false;
                            }
                        });


        View headerLayout  = navigationView.inflateHeaderView(R.layout.navigation_header);
        TextView tx1= headerLayout.findViewById(R.id.tv_nagivation);


        try {
            tx1.setText(pref.getString("mobile","nai"));
            //tx1.setText(pref.getString("mobile",""));
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }



    }


    private void setup()
    {


        drawerlyout=(DrawerLayout)findViewById(R.id.activity_record);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerlyout,toolbar,R.string.open,R.string.close);
        // actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerlyout,toolbar,R.string.open,R.string.close);
        drawerlyout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();





        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        //ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        //ab.setDisplayHomeAsUpEnabled(true);
        //ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)





    }

    private  static final String TAG= "OrderActivity";


    public void OrderConfirm()
    {

        try{
            //_total_meal=pref.getString("totalmeal","nai");
            totalmeal= pref.getInt("totalmeal", 0);
            //passing order date into databse
            try {

                Total_Meal = (totalmeal) + statusLunch + lunchg + dinnerg + statusDinner;


            }
            catch(NumberFormatException e)
            {
                e.printStackTrace();
            }

            Total_Cost=Total_Meal*Meal_Rate;
            To_Pay=Total_Cost-Paid_Amount;
            Total_Lunch=lunchg+statusLunch;
            Total_Dinner=Total_Dinner+statusDinner+dinnerg;
            //User_ID=pref.getString("userid","100");//App.USER.User_id;
            Log.d("uid",User_ID);
            //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String Date= sdf.format(date);
            //order time passsing into databse
            String ghonta = Integer.toString(hour);
            String minit = Integer.toString(min);
            String Time = ghonta + ":" + minit;


            Log.e("tag",User_ID);
            Log.e("tag",User_ID);
            Log.e("tag",User_ID);
            Log.e("tag",User_ID);
            // Israt ensure the link is working
            // Suchi solve the date error

            //String url ="http://192.168.0.103/api/order_register.php?Lunch_Amount="+statusLunch+"&Guest_Lunch_Amount="+lunchg+"&Dinner_Amount="+statusDinner+"&Guest_Dinner_Amount="+dinnerg+"&Date="+Date+"&Total_Meal="+Total_Meal+"&Total_cost="+Total_Cost+"&Total_Lunch="+Total_Lunch+"&Total_Dinner="+Total_Dinner+"&Meal_Rate="+Meal_Rate+"&To_Pay="+To_Pay+"&Paid_Amount="+Paid_Amount+"&Time="+Time+"&User_ID"+App.USER.User_id;

            String url ="http://192.168.0.3/api/order_register.php?Lunch_Amount="+statusLunch+"&Guest_Lunch_Amount="+lunchg+"&Dinner_Amount="+statusDinner+"&Guest_Dinner_Amount="+dinnerg+"&Date="+Date+"&Total_Meal="+Total_Meal+"&Total_cost="+Total_Cost+"&Total_Lunch="+Total_Lunch+"&Total_Dinner="+Total_Dinner+"&Meal_Rate="+Meal_Rate+"&To_Pay="+To_Pay+"&Paid_Amount="+Paid_Amount+"&Time="+Time+"&User_ID="+User_ID;

            url = url.replaceAll(" ", "%20");
            Log.d(TAG, url);
            StringRequest sr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response get",response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("error") == "true") {
                                    onOrderFailed(jsonObject.getString("error_msg").toString());
                                }
                                else{
                                    getuserid();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // onLoginFailed();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    // Log.e("GetData", error.getMessage());
                    Toast.makeText(getApplicationContext(), "Order Failed ", LENGTH_SHORT).show();
                }
            });
            RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
            rq.add(sr);
        }catch (Exception ex)
        {
            Snackbar.make(findViewById(android.R.id.content), "Network Error", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
    }


    public void PaymentShow()
    {
        try {
            // getuserid();
            // Israt ensure the link is working
            Log.d("Usr_id",User_ID);
            Log.d("total meal", String.valueOf(Total_Meal));

            // String url ="http://192.168.0.103/api/order_login.php?Total_Meal="+Total_Meal+"&Total_cost="+Total_Cost+"&Total_Lunch="+Total_Lunch+"&Total_Dinner="+Total_Dinner+"&Meal_Rate="+Meal_Rate+"&To_Pay="+To_Pay+"&Paid_Amount="+Paid_Amount+"&User_ID="+User_ID;

            String url ="http://192.168.0.3/api/order_login.php?Total_Meal="+Total_Meal+"&Total_cost="+Total_Cost+"&Total_Lunch="+Total_Lunch+"&Total_Dinner="+Total_Dinner+"&Meal_Rate="+Meal_Rate+"&To_Pay="+To_Pay+"&Paid_Amount="+Paid_Amount+"&User_ID="+User_ID;

            url=url.replaceAll(" ","20%");
            Log.d(TAG,url);

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


    public void getuserid()
    {
        try {
            // Israt ensure the link is working
            // String url ="http://192.168.0.103/api/paymentfind.php?User_ID="+App.USER.User_id;

            String url ="http://192.168.0.3/api/paymentfind.php?User_ID="+App.USER.User_id;


          //  String url ="http://techkarkhana.com/baachelor/paymentfind.php?User_ID="+User_ID;

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
            editor.putInt("totalmeal", 0);
            editor.commit();


            /*App.initOrder();

            App.ORDERSET.total_meal=userjson.getString("Total_Meal");
           // App.ORDERSET.total_meal=pref.getInt("totalmeal",);
            //editor.putString("totalmeal", App.ORDERSET.total_meal);
           // editor.commit();


            App.ORDERSET.to_pay=userjson.getString("To_Pay");
            //App.ORDERSET.total_meal=pref.getString("topay","kii");
            //editor.putString("topay", App.ORDERSET.to_pay);
            //editor.commit();

           App.ORDERSET.meal_rate=userjson.getString("Meal_Rate");
           // App.ORDERSET.total_meal=pref.getString("totalrate","kiii");
            //editor.putString("mealrate", App.ORDERSET.meal_rate);
            //editor.commit();

           App.ORDERSET.paid_amount=userjson.getString("Paid_Amount");
            //App.ORDERSET.total_meal=pref.getString("paidamount","kiiii");
            //editor.putString("paidamount",  App.ORDERSET.paid_amount);
            //editor.commit();*/

            _total_meal=App.ORDERSET.total_meal;
            textView2.setText(_total_meal);

            _meal_rate=App.ORDERSET.meal_rate;
            textView3.setText(_meal_rate);

            _paid_amount=App.ORDERSET.paid_amount;
            textView5.setText(_paid_amount);

            _to_pay=App.ORDERSET.to_pay;
            textView4.setText(_to_pay);


            _name=pref.getString("username","no name");
            textView1.setText(_name);

            Log.d("mess",App.ORDERSET.paid_amount);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }



    public void onBackPressed()
    {

        final AlertDialog.Builder builder=new AlertDialog.Builder(Order.this);

        builder.setCancelable(true);
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }


}
















