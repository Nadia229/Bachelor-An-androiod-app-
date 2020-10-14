package techkarkhana.apps.bachelor.dhaka;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class confirmActivity extends AppCompatActivity {

    Context cont;
    private SessionManager session;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        cont = this.getApplicationContext();
        session=new SessionManager(this);
        // pref = this.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        // editor=pref.edit();
    }


    public void onBackPressed()
    {

        final AlertDialog.Builder builder=new AlertDialog.Builder(confirmActivity.this);

        builder.setCancelable(true);
        builder.setMessage("Are You sure to Exit?");

        final AlertDialog alertDialog=builder.create();
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                deleteCache(cont);
                System.exit(1);
                //session.setOrder(true);
                //super.onDestroy();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                //startActivity(new Intent(confirmActivity.this, Order.class));
            }
        });

        alertDialog.show();
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
