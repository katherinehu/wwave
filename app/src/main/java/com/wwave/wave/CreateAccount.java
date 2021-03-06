package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    EditText et_name;
    EditText et_createdUsername;
    EditText et_createdPassword;
    EditText et_reenteredCreatedPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        et_name = findViewById(R.id.et_email);
        et_createdPassword = findViewById(R.id.et_createdPassword);
        et_reenteredCreatedPassword = findViewById(R.id.et_reenteredCreatedPassword);
        et_createdUsername = findViewById(R.id.et_createdUsername);
        btnSignUp = findViewById(R.id.btnSignUp);

        final SharedPreferences userInformation = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final Intent goWelcome = new Intent(this,Homee.class);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_name.getText().toString();
                String password1 = et_createdPassword.getText().toString();
                String password2 = et_reenteredCreatedPassword.getText().toString();
                String username = et_createdUsername.getText().toString();
                if (userInformation.getString(username,"not found123").equals("not found123")) {
                    if (password1.equals(password2)) {
                        //We create the account
                        SharedPreferences.Editor editor = userInformation.edit();
                        editor.putString(username,password1);
                        editor.putString(username+"EMAIL",email);
                        editor.commit();
                        Toast succ = Toast.makeText(getApplicationContext(),"Account successfully created",Toast.LENGTH_SHORT);
                        succ.show();
                        SystemClock.sleep(1000);
                        startActivity(goWelcome);
                    } else {
                        Toast youBlind = Toast.makeText(getApplicationContext(),"Passwords don't match",Toast.LENGTH_SHORT);
                        youBlind.show();
                    }
                } else {
                    Toast found = Toast.makeText(getApplicationContext(),"Username taken, make another",Toast.LENGTH_SHORT);
                    found.show();
                }
            }
        });
    }
}