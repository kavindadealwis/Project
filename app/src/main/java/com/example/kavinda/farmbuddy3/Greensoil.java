package com.example.kavinda.farmbuddy3;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Greensoil extends AppCompatActivity {

    int PERMITION_REQUEST = 1;
    private TextView Podda;

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greensoil);

        getSupportActionBar().setTitle("Green Soil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)!= getPackageManager().PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},PERMITION_REQUEST);
        }else {

            //TextView view = new TextView(this);
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    syncGreenSoilState();
                    handler.postDelayed(this, 100);
                }
            }, 500);
            //setContentView(view);
            //view.setText(sms);
            //setContentView(view);
        }
    }

    private void syncGreenSoilState(){
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, "date desc");

        String sms = "";

        while (cur.moveToNext()) {
            if (cur.getString(2).equals("+94768924514")|| cur.getString(2).equals("+9478866891")) {
                if (cur.getString(12).contains("Moisture") && !cur.getString(12).toLowerCase().contains("error")) {
                    // Date date = new Date(cur.getString(4));
                    //String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
                    sms += cur.getString(12) + "\n";
                    Log.d("sms", cur.getString(12));

                }
            }
        }
        Podda = (TextView) findViewById(R.id.textView2);
        final String finalSms = sms;
        Podda.post(new Runnable() {
            @Override
            public void run() {
                Podda.setText(finalSms);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                syncGreenSoilState();
                handler.postDelayed(this, 100);
            }
        }, 100);
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(Greensoil.this, "SMS Sent!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(Greensoil.this, "Generic Faliure!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(Greensoil.this, "No Service!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(Greensoil.this, "Null PUD!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(Greensoil.this, "Radio OFF!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){

                    case Activity.RESULT_OK:
                        Toast.makeText(Greensoil.this, "SMS Delivered", Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(Greensoil.this, "SMS Not Delivered", Toast.LENGTH_SHORT).show();
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
        handler.removeCallbacksAndMessages(null);
        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    public void btn2_SEND_SMS_OnClick(View v){

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
}
