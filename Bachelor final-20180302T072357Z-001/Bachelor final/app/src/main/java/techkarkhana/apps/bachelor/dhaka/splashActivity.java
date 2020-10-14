package techkarkhana.apps.bachelor.dhaka;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;


public class splashActivity extends AppCompatActivity {

    private final int splash_display_length = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                //Intent mainIntent = new Intent(splashActivity.this,loginActivity.class);
                //splashActivity.this.startActivity(mainIntent);
                //splashActivity.this.finish();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String ft = preferences.getString("firstTime", "yes");
                if (ft.equals("No")) {
                    startActivity(new Intent(splashActivity.this, Order.class));
                    // Intent mainIntent = new Intent(splashActivity.this,Order.class);
                    //splashActivity.this.startActivity(mainIntent);
                    splashActivity.this.finish();
                } else {
                    startActivity(new Intent(splashActivity.this, loginActivity.class));
                    //Intent mainIntent = new Intent(splashActivity.this,loginActivity.class);
                    //splashActivity.this.startActivity(mainIntent);
                    splashActivity.this.finish();
                }

            }
        }, splash_display_length);
    }

}


