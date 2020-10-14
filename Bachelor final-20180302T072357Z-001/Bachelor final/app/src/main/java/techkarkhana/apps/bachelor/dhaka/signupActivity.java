package techkarkhana.apps.bachelor.dhaka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
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


public class signupActivity extends AppCompatActivity {
    private static final String TAG = "signupActivity";
    EditText _FirstNameText;
    EditText _LastNameText;
    EditText _RePasswordText;
    EditText _EmailText;
    EditText _LocalGuardiansNameText;
    EditText _GuardiansMobileNoText;
    EditText _NationalIDCardNoText;
    EditText _UniversityNameText;
    EditText _UniversityIDText;
    EditText _CompanyNameText;
    EditText _EmployIDText;
    EditText _MobileNumberText;
    EditText _PasswordText;
    EditText _AreaText;
    EditText _HouseText;
    EditText _FlatText;
    EditText _RoadText;
    EditText _BirthCertificateNoText;
    CheckBox agree;
    Button _confirm;
    Toolbar toolbar;
    TextView _linklogin;
    TextView textView_firstname,textView_lastname,textView_email,textView_mobile,textView_guardian,textView_guardianmobile,
             textView_password,textView_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        toolbar=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        _confirm=(Button)findViewById(R.id.btn_confirm);

        _FirstNameText= (EditText) findViewById(R.id.FirstName);
        _LastNameText= (EditText) findViewById(R.id.LastName);
        _EmailText= (EditText) findViewById(R.id.Email);
        _MobileNumberText= (EditText) findViewById(R.id.MobileNumber);
        _LocalGuardiansNameText= (EditText) findViewById(R.id.GuardiansName);
        _GuardiansMobileNoText= (EditText) findViewById(R.id.GuardiansPhone);
        _PasswordText= (EditText) findViewById(R.id.Password);
        _RePasswordText= (EditText) findViewById(R.id.RePassword);
        _FlatText= (EditText) findViewById(R.id.Flatfloor);
        _HouseText= (EditText) findViewById(R.id.house);
        _AreaText= (EditText) findViewById(R.id.area);
        _NationalIDCardNoText= (EditText) findViewById(R.id.NationaIDCard);
        _UniversityNameText= (EditText) findViewById(R.id.University);
        _UniversityIDText= (EditText) findViewById(R.id.Universityid);
        _CompanyNameText= (EditText) findViewById(R.id.Company);
        _EmployIDText= (EditText) findViewById(R.id.Employid);
        _RoadText =(EditText) findViewById(R.id.Road);
        _BirthCertificateNoText=(EditText)findViewById(R.id.BirthCertificate);

        _confirm =  findViewById(R.id.btn_confirm);

        textView_firstname = findViewById(R.id.tx_firstname);
        textView_lastname = findViewById(R.id.tx_lastname);
        textView_email = findViewById(R.id.tx_email);
        textView_mobile = findViewById(R.id.tx_mobile);
        textView_password = findViewById(R.id.tx_password);
        textView_guardian = findViewById(R.id.tx_guardian);
        textView_guardianmobile = findViewById(R.id.tx_guardianmobile);
        textView_address = findViewById(R.id.tx_address);

        textView_firstname.setText(Html.fromHtml("<font color='#000000'>First Name</font> <font color='#ff4040'>*</font>"));
        textView_lastname.setText(Html.fromHtml("<font color='#000000'>Last Name</font> <font color='#ff4040'>*</font>"));
        textView_email.setText(Html.fromHtml("<font color='#000000'>Email</font> <font color='#ff4040'>*</font>"));
        textView_mobile.setText(Html.fromHtml("<font color='#000000'>Mobile Number</font> <font color='#ff4040'>*</font>"));
        textView_password.setText(Html.fromHtml("<font color='#000000'>Password</font> <font color='#ff4040'>*</font> <font color='#bbbbbb'>(can't be less than 5 characters)</font>"));
       // textView_guardian.setText(Html.fromHtml("<font color='#000000'>Local Guardian Name</font> <font color='#ff4040'>*</font>"));
        //textView_guardianmobile.setText(Html.fromHtml("<font color='#000000'>Guardian's Phone No.</font> <font color='#ff4040'>*</font>"));
        textView_address.setText(Html.fromHtml("<font color='#000000'>Address</font> <font color='#ff4040'>*</font>"));



        agree =  (CheckBox) findViewById(R.id.checkbox_agree);
        _linklogin = findViewById(R.id.link_login);

        _confirm.setOnClickListener(new View.OnClickListener() {
            //  @Override
            public void onClick(View v) {

                if(agree.isChecked()) {
                    confirm();
                    //startActivity(new Intent(signupActivity.this, loginActivity.class));
                }
                else
                {
                    Toast.makeText(signupActivity.this, "Please click the checkbox", Toast.LENGTH_SHORT).show();
                }

                //  Intent intent =new Intent(getApplicationContext(),loginActivity.class);
                //startActivity(intent);
                // finish();
                // overridePendingTransition(R.anim.class, R.anim.);


            }
        });

        _linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(signupActivity.this, loginActivity.class));

            }
        });





        ImageView back3 = (ImageView) findViewById(R.id.back3);
        // toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signupActivity.this,loginActivity.class));

            }
        });

    }


    public void confirm() {
        Log.d(TAG, "Confirm");

        if (!validate()) {
            onSignupFailed("Please validate the field ");
            return;
        }

        _confirm.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(signupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String FirstName = _FirstNameText.getText().toString();
        String LastName =_LastNameText.getText().toString();
        String Email =_EmailText.getText().toString();
        String MobileNumber =_MobileNumberText.getText().toString();
        String LocalGuardiansName =_LocalGuardiansNameText.getText().toString();
        String GuardiansMobileNo =_GuardiansMobileNoText.getText().toString();
        String Password =_PasswordText.getText().toString();
        String RePassword =_RePasswordText.getText().toString();
        String Flat =_FlatText.getText().toString();
        String House =_HouseText.getText().toString();
        String Area =_AreaText.getText().toString();
        String NationalIDCardNo =_NationalIDCardNoText.getText().toString();
        String UniversityName =_UniversityNameText.getText().toString();
        String UniversityID =_UniversityIDText.getText().toString();
        String CompanyName =_CompanyNameText.getText().toString();
        String EmployID =_EmployIDText.getText().toString();
        String Road =_RoadText.getText().toString();
        String BirthCertificateNo=_BirthCertificateNoText.getText().toString();



        if(Password==RePassword)
        {
            progressDialog.dismiss();
            Snackbar.make(findViewById(android.R.id.content), "Password are not same. ", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
            return;

        }


        else {
            try{
                String url ="http://192.168.0.3/api/register.php?FirstName="+FirstName+"&LastName="+LastName+"&Email="+Email+"&Local_Guardians_Name="+LocalGuardiansName+"&Guardians_Mobile_No="+GuardiansMobileNo+"&Birth_Certificate_No="+BirthCertificateNo+"&National_ID_Card_No="+NationalIDCardNo+"&University_Name="+UniversityName+"&University_ID="+UniversityID+"&Company_Name="+CompanyName+"&Employ_ID="+EmployID+"&Password="+Password+"&Area="+Area+"&House="+House+"&Flat="+Flat+"&Road="+Road+"&MobileNumber="+MobileNumber;
                //String url ="http://192.168.0.103/api/register.php?FirstName="+FirstName+"&LastName="+LastName+"&Email="+Email+"&Local_Guardians_Name="+LocalGuardiansName+"&Guardians_Mobile_No="+GuardiansMobileNo+"&Birth_Certificate_No="+BirthCertificateNo+"&National_ID_Card_No="+NationalIDCardNo+"&University_Name="+UniversityName+"&University_ID="+UniversityID+"&Company_Name="+CompanyName+"&Employ_ID="+EmployID+"&Password="+Password+"&Area="+Area+"&House="+House+"&Flat="+Flat+"&Road="+Road+"&MobileNumber="+MobileNumber;
               //String url ="http://bdcoderspro.com/bachelor/register.php?FirstName="+FirstName+"&LastName="+LastName+"&Email="+Email+"&Local_Guardians_Name="+LocalGuardiansName+"&Guardians_Mobile_No="+GuardiansMobileNo+"&Birth_Certificate_No="+BirthCertificateNo+"&National_ID_Card_No="+NationalIDCardNo+"&University_Name="+UniversityName+"&University_ID="+UniversityID+"&Company_Name="+CompanyName+"&Employ_ID="+EmployID+"&Password="+Password+"&Area="+Area+"&House="+House+"&Flat="+Flat+"&Road="+Road+"&MobileNumber="+MobileNumber;

                url = url.replaceAll(" ", "%20");
                Log.e(TAG, url);
                StringRequest sr = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("error") == "true") {
                                        onSignupFailed(jsonObject.getString("error_msg").toString());
                                    } else onSignupSuccess();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Log.e("GetData", error.getMessage());
                        Toast.makeText(getApplicationContext(), "Registration Failed ", Toast.LENGTH_SHORT).show();
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

        startActivity(new Intent(signupActivity.this, loginActivity.class));
    }


    public void onSignupSuccess() {
        _confirm.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed(String error) {
        Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();


        _confirm.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String FirstName = _FirstNameText.getText().toString();
        String LastName = _LastNameText.getText().toString();
        String Email = _EmailText.getText().toString();
        String MobileNumber = _MobileNumberText.getText().toString();
        String LocalGuardiansName = _LocalGuardiansNameText.getText().toString();
        String GuardiansMobileNo = _GuardiansMobileNoText.getText().toString();
        String Password = _PasswordText.getText().toString();
        String RePassword = _RePasswordText.getText().toString();
        String Flat = _FlatText.getText().toString();
        String House = _HouseText.getText().toString();
        String Area = _AreaText.getText().toString();
        String NationalIDCardNo = _NationalIDCardNoText.getText().toString();
        String UniversityName = _UniversityNameText.getText().toString();
        String UniversityID = _UniversityIDText.getText().toString();
        String CompanyName = _CompanyNameText.getText().toString();
        String EmployID = _EmployIDText.getText().toString();
        String Road=_RoadText.getText().toString();
        String BirthCertificateNo=_BirthCertificateNoText.getText().toString();



      /*  //if (FirstName.isEmpty() || FirstName.length() < 100 ||  FirstName.length() >2) {
        if(FirstName.isEmpty()){
            _FirstNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _FirstNameText.setError(null);
        }

        // if (LastName.isEmpty() || LastName.length() < 100 ||  LastName.length() >2) {
        if(LastName.isEmpty()){
            _LastNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _LastNameText.setError(null);
        }
        if (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            _EmailText.setError("enter a valid email address");
            valid = false;
        } else {
            _EmailText.setError(null);
        }



        // if (Password.isEmpty() || Password.length() < 13 || Password.length() > 2) {
        if(Password.isEmpty()){
            _PasswordText.setError("between 2 and 13 alphanumeric characters");
            valid = false;

        } else {
            _PasswordText.setError(null);

        }
        //if (RePassword.isEmpty() || RePassword.length() < 13 || RePassword.length() > 4 || !(RePassword.equals(Password))) {
        if(RePassword.isEmpty()){
            _RePasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _RePasswordText.setError(null);
        }

        // if (MobileNumber.isEmpty() || MobileNumber.length() < 15 || MobileNumber.length() >11) {
        if(MobileNumber.isEmpty()) {
            _MobileNumberText.setError("between 11 and 15 numerical characters");
            valid = false;
        } else {
            _MobileNumberText.setError(null);
        }

        //if (LocalGuardiansName.isEmpty() || LocalGuardiansName.length() < 100 || LocalGuardiansName.length() >2) {
        if(LocalGuardiansName.isEmpty()){
            _LocalGuardiansNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _LocalGuardiansNameText.setError(null);
        }

        // if (GuardiansMobileNo.isEmpty() || GuardiansMobileNo.length() < 15 ||  GuardiansMobileNo.length() >11) {
        if(GuardiansMobileNo.isEmpty()){
            _GuardiansMobileNoText.setError("between 11 and 15 numerical characters");
            valid = false;
        } else {
            _GuardiansMobileNoText.setError(null);
        }

        //if (Flat.isEmpty() || Flat.length() < 10|| Flat.length() >1 ) {
        if(Flat.isEmpty())
        {_FlatText.setError("between 1 and 10 numerical characters");
            valid = false;
        } else {
            _FlatText.setError(null);
        }
        //if (House.isEmpty() || House.length() < 100 || House.length()>1) {
        if(House.isEmpty()){
            _HouseText.setError("at least 3 characters");
            valid = false;
        } else {
            _HouseText.setError(null);
        }

        //if (Area.isEmpty() || Area.length() <  100|| Area.length()>1) {
        if(Area.isEmpty())
        { _AreaText.setError("at least 3 characters");
            valid = false;
        } else {
            _AreaText.setError(null);
        }

        // if (UniversityName.isEmpty() || UniversityName.length() < 100|| UniversityName.length()>6) {
        if(UniversityName.isEmpty()){
            _UniversityNameText.setError("between 6 and 100 numerical characters");
            valid = false;
        } else {
            _UniversityNameText.setError(null);
        }

        //if (UniversityID.isEmpty() || UniversityID.length() <20||UniversityID.length()>4) {
        if(UniversityID.isEmpty()){
            _UniversityIDText.setError("between 4 and 20 numerical characters");
            valid = false;
        } else {
            _UniversityIDText.setError(null);
        }

        //if (CompanyName.isEmpty() || CompanyName.length() < 100|| UniversityName.length()>4) {
        if(CompanyName.isEmpty()){
            _CompanyNameText.setError("between 4 and 100 numerical characters");
            valid = false;
        } else {
            _CompanyNameText.setError(null);
        }

        // if (EmployID.isEmpty() || UniversityID.length() <20||UniversityID.length()>4 ){
        if(EmployID.isEmpty()){
            _EmployIDText.setError("between 4 and 100 numerical characters");
            valid = false;
        } else {
            _EmployIDText.setError(null);
        }


        // if (NationalIDCardNo.isEmpty() || NationalIDCardNo.length() < 100 ||  NationalIDCardNo.length() >6) {
        if(NationalIDCardNo.isEmpty()){
            _NationalIDCardNoText.setError("at least 7 characters");
            valid = false;
        } else {
            _NationalIDCardNoText.setError(null);
        }


        //if (BirthCertificateNo.isEmpty() || BirthCertificateNo.length() < 100 ||  BirthCertificateNo.length() >6) {

        if(BirthCertificateNo.isEmpty()){
            _BirthCertificateNoText.setError("at least 7 characters");
            valid = false;
        } else {
            _BirthCertificateNoText.setError(null);
        }

        //if (Road.isEmpty() || Road.length() < 100 ||  Road.length() >1) {
        if(Road.isEmpty()){
            _RoadText.setError("at least 2 characters");
            valid = false;
        } else {
            _RoadText.setError(null);
        }

        return valid;
    }

} */
        //if (FirstName.isEmpty() || FirstName.length() < 100 ||  FirstName.length() >2) {
        if(FirstName.isEmpty()){
            _FirstNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _FirstNameText.setError(null);
        }

        // if (LastName.isEmpty() || LastName.length() < 100 ||  LastName.length() >2) {
        if(LastName.isEmpty()){
            _LastNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _LastNameText.setError(null);
        }
        if (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            _EmailText.setError("enter a valid email address");
            valid = false;
        } else {
            _EmailText.setError(null);
        }



        if(Password.isEmpty()){
            _PasswordText.setError("between 2 and 13 alphanumeric characters");
            valid = false;

        } else {
            _PasswordText.setError(null);

        }
        if (Password.length() > 13 || Password.length() < 2) {

            _PasswordText.setError("between 2 and 13 alphanumeric characters");
            valid = false;


        }
        //if (RePassword.isEmpty() || RePassword.length() < 13 || RePassword.length() > 4 || !(RePassword.equals(Password))) {
        if(RePassword.isEmpty()){
            _RePasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _RePasswordText.setError(null);
        }

        // if (MobileNumber.isEmpty() || MobileNumber.length() < 15 || MobileNumber.length() >11) {
        if(MobileNumber.isEmpty()) {
            _MobileNumberText.setError("between 11 and 15 numerical characters");
            valid = false;
        } else {
            _MobileNumberText.setError(null);
        }

        //if (LocalGuardiansName.isEmpty() || LocalGuardiansName.length() < 100 || LocalGuardiansName.length() >2) {
       /* if(LocalGuardiansName.isEmpty()){
            _LocalGuardiansNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _LocalGuardiansNameText.setError(null);
        }

        // if (GuardiansMobileNo.isEmpty() || GuardiansMobileNo.length() < 15 ||  GuardiansMobileNo.length() >11) {
        if(GuardiansMobileNo.isEmpty()){
            _GuardiansMobileNoText.setError("between 11 and 15 numerical characters");
            valid = false;
        } else {
            _GuardiansMobileNoText.setError(null);
        }*/
        //if (Flat.isEmpty() || Flat.length() < 10|| Flat.length() >1 ) {
        if(Flat.isEmpty())
        {_FlatText.setError("between 1 and 10 numerical characters");
            valid = false;
        } else {
            _FlatText.setError(null);
        }
        //if (House.isEmpty() || House.length() < 100 || House.length()>1) {
        if(House.isEmpty()){
            _HouseText.setError("at least 3 characters");
            valid = false;
        } else {
            _HouseText.setError(null);
        }

        //if (Area.isEmpty() || Area.length() <  100|| Area.length()>1) {
        if(Area.isEmpty())
        { _AreaText.setError("at least 3 characters");
            valid = false;
        } else {
            _AreaText.setError(null);
        }

        // if (UniversityName.isEmpty() || UniversityName.length() < 100|| UniversityName.length()>6) {
        /*if(UniversityName.isEmpty()){
            _UniversityNameText.setError("between 6 and 100 numerical characters");
            valid = false;
        } else {
            _UniversityNameText.setError(null);
        }

        //if (UniversityID.isEmpty() || UniversityID.length() <20||UniversityID.length()>4) {
        if(UniversityID.isEmpty()){
            _UniversityIDText.setError("between 4 and 20 numerical characters");
            valid = false;
        } else {
            _UniversityIDText.setError(null);
        }

        //if (CompanyName.isEmpty() || CompanyName.length() < 100|| UniversityName.length()>4) {
        if(CompanyName.isEmpty()){
            _CompanyNameText.setError("between 4 and 100 numerical characters");
            valid = false;
        } else {
            _CompanyNameText.setError(null);
        }

        // if (EmployID.isEmpty() || UniversityID.length() <20||UniversityID.length()>4 ){
        if(EmployID.isEmpty()){
            _EmployIDText.setError("between 4 and 100 numerical characters");
            valid = false;
        } else {
            _EmployIDText.setError(null);
        }


        // if (NationalIDCardNo.isEmpty() || NationalIDCardNo.length() < 100 ||  NationalIDCardNo.length() >6) {
        if(NationalIDCardNo.isEmpty()){
            _NationalIDCardNoText.setError("at least 7 characters");
            valid = false;
        } else {
            _NationalIDCardNoText.setError(null);
        }


        //if (BirthCertificateNo.isEmpty() || BirthCertificateNo.length() < 100 ||  BirthCertificateNo.length() >6) {

        if(BirthCertificateNo.isEmpty()){
            _BirthCertificateNoText.setError("at least 7 characters");
            valid = false;
        } else {
            _BirthCertificateNoText.setError(null);
        }*/

        //if (Road.isEmpty() || Road.length() < 100 ||  Road.length() >1) {
        if(Road.isEmpty()){
            _RoadText.setError("at least 2 characters");
            valid = false;
        } else {
            _RoadText.setError(null);
        }

        return valid;
    }

}



