package com.example.kavinda.farmbuddy3;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Gardensoil extends AppCompatActivity {

    //EditText etMessage;
    //EditText etTelNr;
    int PERMITION_REQUEST = 1;

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardensoil);

        //etMessage = (EditText) findViewById(R.id.etMessage);
        //etTelNr = (EditText) findViewById(R.id.etTelNr);
        getSupportActionBar().setTitle("Garden Soil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(Gardensoil.this, "SMS Sent!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(Gardensoil.this, "Generic Faliure!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(Gardensoil.this, "No Service!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(Gardensoil.this, "Null PUD!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(Gardensoil.this, "Radio OFF!", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){

                    case Activity.RESULT_OK:
                        Toast.makeText(Gardensoil.this, "SMS Delivered", Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(Gardensoil.this, "SMS Not Delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    public void btnSoil1_SEND_SMS_OnClick(View v){

        String message = "Message From Sensor 1";
        String telNr = "+94778777893";

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= getPackageManager().PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},PERMITION_REQUEST);
        }
        else{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(telNr, null, message, sentPI, deliveredPI);
        }
    }

    public void btnSoil2_SEND_SMS_OnClick(View v){

        String message = "Message From Sensor 2";
        String telNr = "+94778777893";

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= getPackageManager().PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},PERMITION_REQUEST);
        }
        else{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(telNr, null, message, sentPI, deliveredPI);
        }
    }

    public void btnSoil3_SEND_SMS_OnClick(View v){

        String message = "Message From Sensor 3";
        String telNr = "+94778777893";

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= getPackageManager().PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},PERMITION_REQUEST);
        }
        else{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(telNr, null, message, sentPI, deliveredPI);
        }
    }

    public void btnSoil4_SEND_SMS_OnClick(View v){

        String message = "Message From Sensor 4";
        String telNr = "+94778777893";

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= getPackageManager().PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},PERMITION_REQUEST);
        }
        else{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(telNr, null, message, sentPI, deliveredPI);
        }
    }
}
