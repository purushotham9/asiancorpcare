package nnk.com.asiancorpcare;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    Button save_button;
    EditText email_et,password_et;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save_button= (Button)findViewById(R.id.login_btn);
        email_et = (EditText)findViewById(R.id.email_et);

        email_et.setText("mahabooda580@gmail.com");
        password_et = (EditText)findViewById(R.id.pass_et);
        password_et.setText("1234");
        SaveLoginDatabase saveLoginDatabase = new SaveLoginDatabase(this);
        sqLiteDatabase = saveLoginDatabase.getWritableDatabase();

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("info >> ", "onClick: >> emailid " + email_et.getText().toString());
                Log.e("info >> ", "onClick: >> password " + password_et.getText().toString());
                Intent intent = null;
                String validCredentials = new SaveLoginDatabase(getApplicationContext()).getUserInfo(sqLiteDatabase, email_et.getText().toString(), password_et.getText().toString());
                if (validCredentials.equalsIgnoreCase("nullnull")) {
                    new SaveLoginDatabase(getApplicationContext()).addUser(sqLiteDatabase, email_et.getText().toString(), password_et.getText().toString());
                    Toast.makeText(MainActivity.this, "user data saved successfully ", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, "user data saved successfully ", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, DashBoard.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "user login exists ", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, DashBoard.class);
                    startActivity(intent);
                }
//                if (intent != null)
//                    startActivity(intent);
            }
        });

    }
    //    public static boolean isEmailValid(String email) {
//        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}

