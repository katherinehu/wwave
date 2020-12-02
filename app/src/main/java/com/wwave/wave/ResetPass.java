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

public class ResetPass extends AppCompatActivity {


    EditText et_pass1;
    EditText et_pass2;
    EditText et_user;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        //get from ui
        et_pass1 = findViewById(R.id.et_pass1);
        et_pass2 = findViewById(R.id.et_pass2);
        et_user = findViewById(R.id.et_user);
        btnSubmit = findViewById(R.id.btn_Submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_pass1.getText().toString().equals(et_pass2.getText().toString())){
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = pref.edit();
                    String username = et_user.getText().toString();
                    String password = et_pass1.getText().toString();

                    if (pref.getString(username, "not found123").equals("not found123")) {
                        Toast.makeText(getApplicationContext(), "Username does not exist",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        edit.remove(username);
                        edit.putString(username,password);
                        edit.apply();
                        Toast.makeText(getApplicationContext(),"Password reset successful",Toast.LENGTH_SHORT).show();
                        SystemClock.sleep(1000);
                        startActivity(new Intent(getApplicationContext(),LogIn.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Passwords don't match try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}