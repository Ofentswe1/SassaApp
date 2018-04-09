package za.co.itechhub.sassaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

        Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
        idNumber = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(password.getText()) && TextUtils.isEmpty(idNumber.getText())){
                    ToastMessage("All Fields are required!");
                    idNumber.setError("Id Number Required.");
                    password.setError("Password Required.");
                }
                else if(TextUtils.isEmpty(idNumber.getText())){
                    ToastMessage("Id Number Required.");
                    idNumber.setError("Id Number Required.");
                }
                else if(TextUtils.isEmpty(password.getText())){
                    ToastMessage("Password Required.");
                    password.setError("Password Required.");
                }
                else{
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    public void ToastMessage(CharSequence text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }
}
