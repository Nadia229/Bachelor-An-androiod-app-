package techkarkhana.apps.bachelor.dhaka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class About extends AppCompatActivity {
    Toolbar toolbar;
    ImageView back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);



          back1 = (ImageView) findViewById(R.id.back1);
       // toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);



        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(About.this,Order.class));
                finish();


            }
        });



    }

}
