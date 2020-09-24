package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    EditText et_username;
    EditText et_password;
    Button bt_logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Declare UI elements
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);
        bt_logIn = findViewById(R.id.btn_signIn);

        //Allow users to store their own data
        final SharedPreferences userInformation = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //Collect information the login

        final Intent acceptLogin = new Intent(this, Welcome.class);
        bt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentName = et_username.getText().toString();
                String currentPassword = et_password.getText().toString();
                String password = userInformation.getString(currentName,"this username does not exist1223323723465217513645346714");
                if (!password.equals("this username does not exist1223323723465217513645346714")) {
                    if (password.equals(currentPassword)) {
                        String personsName = userInformation.getString(currentName + "NAME","if you ever see this, you went wrong");
                        acceptLogin.putExtra("name",personsName);
                        startActivity(acceptLogin);
                    } else {
                        //tell the person to go away
                        Toast toast = Toast.makeText(getApplicationContext(), "Invalid Password",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    //Invalid username
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid Username",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });



    }
}