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

import za.co.itechhub.sassaapp.models.User;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText idNumber, password;
    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.email_sign_in_button);
        idNumber = findViewById(R.id.email);
        password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    System.out.println("__________1111111111111____");
                    new JSONTask().execute("http://192.168.43.75:8000/app/api/"+
                            idNumber.getText().toString()+"/"+ password.getText().toString());
            }
        });
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
                String finalObject = "{" + '"' + "users" + '"' + ":" + jsonObject + "}";
                System.out.println("---------------------------");
                System.out.println(finalObject);

                JSONObject jsonParent = new JSONObject(finalObject);
                JSONArray jsonArray = jsonParent.getJSONArray("users");
                System.out.println("----------222222222222222222222222222222-------");
                System.out.println(jsonArray.toString());

                JSONObject object = jsonArray.getJSONObject(0).getJSONObject("fields");
                System.out.println(object.toString());
                String name = object.getString("first_name");
                String surname = object.getString("last_name");
                String address = object.getString("email");
                String username = object.getString("username");
                String password = object.getString("password");
                System.out.println("_____________33333333333333333333333________________");
                System.out.println("name: "+ name+", Surname: "+surname+", address: " + address
                        +", username: "+username + ", password: "+password);

                users.add(new User(name, surname, address, username,password));
                //onPostExecute(jsonObject);

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
            System.out.println("________________44444444444444444444444444444________________");
            if (validateInput()) {
                System.out.println("________________555555555555555555555555555555555________________");
                if (!users.isEmpty()) {
                    System.out.println("________________66666666666666666666666666666________________");
                    User user = users.get(0);
                        System.out.println("________________77777777777777777777777777________________");
                        Toast.makeText(getBaseContext(),"Access granted",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                }else{
                    //TODO validate wrong credentials

                }
            }
        }

        public boolean validateInput() {
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
