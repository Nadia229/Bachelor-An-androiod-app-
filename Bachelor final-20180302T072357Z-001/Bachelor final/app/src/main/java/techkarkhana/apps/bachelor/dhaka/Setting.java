package techkarkhana.apps.bachelor.dhaka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class Setting extends AppCompatActivity {
    Toolbar toolbar;
    ImageView back2;
    Spinner spinner;
    String update;


    public int item=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        spinner = findViewById(R.id.spinner);



        ArrayAdapter  adapter = ArrayAdapter.createFromResource(this, R.array.Updete_details, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //aikhane code korte hobe

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String country = parent.getItemAtPosition(position).toString();
                update = spinner.getSelectedItem().toString().trim();


                if (country.equals("FirstName"))
                {
                    Intent intent = new Intent(Setting.this,firstname.class);
                    startActivity(intent);
                }




                // Toast.makeText(Order.this, lunchspinner, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        back2 = (ImageView) findViewById(R.id.back2);
        // toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);



        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Setting.this,Order.class));
                finish();


            }
        });

    }
}
