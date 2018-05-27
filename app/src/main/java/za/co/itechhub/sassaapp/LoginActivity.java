package za.co.itechhub.sassaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A login screen that offers login via email/password.
 */
@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity {

    private EditText idNumber, password;
    private ArrayList<User> users = new ArrayList<>();
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
                //new JSONTask().execute("http://192.168.8.106:8000/app/personal/ofentswe/");
                if (TextUtils.isEmpty(password.getText()) && TextUtils.isEmpty(idNumber.getText())) {

                    ToastMessage("All Fields are required!");
                    idNumber.setError("Id Number Required.");
                    password.setError("Password Required.");

                } else if (TextUtils.isEmpty(idNumber.getText())) {

                    ToastMessage("Id Number Required.");
                    idNumber.setError("Id Number Required.");

                } else if (TextUtils.isEmpty(password.getText())) {

                    ToastMessage("Password Required.");
                    password.setError("Password Required.");

                } else {
                    System.out.println("______________________________________________________");
                    new JSONTask().execute("http://192.168.8.101:8000/app/api/" + idNumber.getText().toString()
                            + "/" + password.getText().toString() + "/");
//                    User user = new User("mlugisi","shokkl", "ghhgh jhgjhgjh",
//                            "jhhjkhkhkhkhkh");
//                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                    intent.putExtra("user",user);
//                    startActivity(intent);
//                    finish();

                }
            }
        });
    }

    public void ToastMessage(CharSequence text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    private class JSONTask extends AsyncTask<String, String, String> implements AdapterView.OnItemClickListener {

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String jsonObject = buffer.toString();
                String finalObject = "{" + '"' + "users" + '"' + ": " + jsonObject + "}";

                JSONObject jsonParent = new JSONObject(finalObject);
                JSONArray jsonArray = jsonParent.getJSONArray("users");


                JSONObject object = jsonArray.getJSONObject(0).getJSONObject("fields");
                String name = object.getString("first_name");
                String surname = object.getString("last_name");
                String address = object.getString("email");
                String username = object.getString("username");
                System.out.println("_________________________________________________________");
                System.out.println("name: "+ name+", Surname: "+surname+", address: " + address
                        +", username: "+username);

                users.add(new User(name, surname, address, username));

                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (validateInpuut()) {
                for (User user : users) {
                    if(user.getUsername().equals(idNumber.getText().toString())&&
                            user.getSurname().equals(password.getText().toString())){
                        Toast.makeText(getBaseContext(),"Access granted",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("_user",user);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }

        public boolean validateInpuut() {
            if (TextUtils.isEmpty(idNumber.getText())) {
                idNumber.setError("Required.");
                return false;
            } else {
                idNumber.setError(null);
            }
            if (TextUtils.isEmpty(password.getText())) {
                password.setError("Required.");
                return false;
            } else {
                password.setError(null);
            }
            return true;
        }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }
}
