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
    Button signUp;
    Button btn_reset;
    Button btn_forgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Declare UI elements
        et_password = findViewById(R.id.et_pass2);
        et_username = findViewById(R.id.et_pass1);
        bt_logIn = findViewById(R.id.btn_Submit);
        signUp = findViewById(R.id.btnSignUp);
        btn_reset = findViewById(R.id.btn_reset);
        btn_forgot = findViewById(R.id.btn_forgot);



        //Allow users to store their own data
        final SharedPreferences userInformation = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //Collect information the login

        final Intent acceptLogin = new Intent(this, Homee.class);
        bt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentName = et_username.getText().toString();
                String currentPassword = et_password.getText().toString();
                String password = userInformation.getString(currentName,"this username does not exist1223323723465217513645346714");
                if (!password.equals("this username does not exist1223323723465217513645346714")) {
                    if (password.equals(currentPassword)) {
                        String personsName = userInformation.getString(currentName + "NAME","if you ever see this, you went wrong");
                        SharedPreferences.Editor editor = userInformation.edit();
                        editor.putString("nameOfUser",personsName);
                        editor.commit();
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

        final Intent signup = new Intent(this,CreateAccount.class);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signup);
            }
        });


        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EmailPassword.class));
            }
        });

        final Intent forgotI = new Intent (this, ResetPass.class);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(forgotI);
            }
        });



    }
}