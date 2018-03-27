package com.example.kavinda.farmbuddy3;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

public class Gardenwell extends AppCompatActivity {

    int PERMITION_REQUEST = 1;

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardenwell);

        getSupportActionBar().setTitle("Garden Well");
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

                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(Gardenwell.this, "SMS Sent!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(Gardenwell.this, "Generic Faliure!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(Gardenwell.this, "No Service!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(Gardenwell.this, "Null PUD!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(Gardenwell.this, "Radio OFF!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){

                    case Activity.RESULT_OK:
                        Toast.makeText(Gardenwell.this, "SMS Delivered", Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(Gardenwell.this, "SMS Not Delivered", Toast.LENGTH_SHORT).show();
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

    public void btnWell_SEND_SMS_OnClick(View v){

        String message = "Message From Well";
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
