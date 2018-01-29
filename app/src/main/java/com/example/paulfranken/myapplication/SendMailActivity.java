package com.example.paulfranken.myapplication;

/**
 * Erstellt by timst on 28.01.2018.
 * Bearbeitet von Call-Paul 28.01.2018
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SendMailActivity extends Activity {
    public static Activity fa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        final Button send = (Button) this.findViewById(R.id.send);
fa=this;
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("SendMailActivity", "Send Button Clicked.");

                String fromEmail = "kontaktfromapp@gmail.com";
                String fromPassword = "facharbeit";
                String toEmail = "kontaktfromapp@gmail.com";


                String emailSubject = ((TextView) findViewById(R.id.editText4))
                        .getText().toString();
                String email=((TextView)findViewById(R.id.email)).getText().toString();
                String emailBody = ((TextView) findViewById(R.id.editText5))
                        .getText().toString();
                new SendMailTask(SendMailActivity.this).execute(fromEmail,
                        fromPassword, toEmail, emailSubject, emailBody+"\n"+email);


            }
        });
    }
}