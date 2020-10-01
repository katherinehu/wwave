package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmailActivity extends AppCompatActivity {

    EditText subjectLine;
    EditText message;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        subjectLine = findViewById(R.id.editEmailSubject);
        message = findViewById(R.id.editEmailMessage);
        btnSend = findViewById(R.id.btnSendEmail);


        final Intent intent = new Intent(Intent.ACTION_SENDTO);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipientList = "katherine.z.hu@vanderbilt.edu," +
                        "patrick.d.meng@vanderbilt.edu";
                String [] recipients = recipientList.split(",");
                String subject = subjectLine.getText().toString();
                String messageToSend = message.getText().toString();
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(intent.EXTRA_EMAIL, recipients);
                intent.putExtra(intent.EXTRA_SUBJECT, subject);
                intent.putExtra(intent.EXTRA_TEXT, messageToSend);
                startActivity(intent);
            }
        });


    }
}