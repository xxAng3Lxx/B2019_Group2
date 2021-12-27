package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText name,password,address;
    Button insert,update,delete;
    private static final String REGISTER_URL = "https://cheek-waves.000webhostapp.com/TestDataApp/insertdata.php";
    private static final String DELETE_URL = "https://cheek-waves.000webhostapp.com/TestDataApp/delete.php";
    private static final String UPDATE_URL = "https://cheek-waves.000webhostapp.com/TestDataApp/update.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText)findViewById(R.id.editTextname);
        password=  (EditText)findViewById(R.id.editTextpassword);
        address = (EditText)findViewById(R.id.editTextaddress);

        insert = (Button)findViewById(R.id.buttoninsert);
        update = (Button)findViewById(R.id.buttonupdate);
        delete = (Button)findViewById(R.id.buttondelete);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteUser();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUser();
            }
        });


    }
    private void registerUser() {

        String Name = name.getText().toString().trim().toLowerCase();
        String Passcode = password.getText().toString().trim().toLowerCase();
        String Address = address.getText().toString().trim().toLowerCase();


        register(Name,Passcode,Address);
    }


    private void register(String name, String passcode, String address) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("name", params[0]);
                data.put("password", params[1]);
                data.put("address", params[2]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(name,passcode,address);
    }

    private void DeleteUser() {

        String Name = name.getText().toString().trim().toLowerCase();


        register(Name);
    }


    private void register(String name) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("name", params[0]);

                String result = ruc.sendPostRequest(DELETE_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(name);
    }
    private void UpdateUser() {

        String Name = name.getText().toString().trim().toLowerCase();
        String Password = password.getText().toString().trim().toLowerCase();
        String Address = address.getText().toString().trim().toLowerCase();


        registers(Name,Password,Address);
    }


    private void registers(String name, String password, String address) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("name", params[0]);
                data.put("password", params[1]);
                data.put("address", params[2]);

                String result = ruc.sendPostRequest(UPDATE_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(name,password,address);
    }

}