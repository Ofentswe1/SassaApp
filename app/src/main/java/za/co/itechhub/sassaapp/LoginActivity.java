package za.co.itechhub.sassaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity{


    // UI references.
    private EditText idNumber, password;

    // Toast context and duration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
        idNumber = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idNumber.toString() == "" && password.toString() == ""){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "Username and Password cannotbe Empty!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "Login Successful!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
//                else if(idNumber.toString() !="01234" && password.toString() != "admin"){
//                    Context context = getApplicationContext();
//                    int duration = Toast.LENGTH_SHORT;
//                    CharSequence text = "Wrong Details provided!";
//                    Toast toast = Toast.makeText(context, text, duration);
//                    toast.show();
//                }

            }
        });
    }

}
