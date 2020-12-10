package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailPassword extends AppCompatActivity {

    Button btnSendPassword;
    EditText etForgetfulUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        btnSendPassword = findViewById(R.id.btnSendPassword);
        etForgetfulUsername = findViewById(R.id.etForgetfulUsername);

        btnSendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String username = etForgetfulUsername.getText().toString();
                String email = data.getString(username+"EMAIL","if email does not exist this text will be returned");
                if (email.equals("if email does not exist this text will be returned")) {
                    Toast.makeText(getApplicationContext(),"User does not exist",Toast.LENGTH_SHORT).show();
                } else {
                    //intent is for emailing password
                    String password = data.getString(username,"non weird default value");
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    String [] recipient = {email};
                    String subject = "Remember your password next time";
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(intent.EXTRA_EMAIL, recipient);
                    intent.putExtra(intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(intent.EXTRA_TEXT, password);
                    startActivity(intent);
                }
            }
        });
    }
}