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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Greenhumidity extends AppCompatActivity {

    int PERMITION_REQUEST = 1;
    private TextView Podda;

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greenhumidity);

        getSupportActionBar().setTitle("Green Humidity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)!= getPackageManager().PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},PERMITION_REQUEST);
        }else {
            TextView view = new TextView(this);
            Uri uriSMSURI = Uri.parse("content://sms/inbox");
            Cursor cur = getContentResolver().query(uriSMSURI, null, null, null,"date desc");

            String sms = "";

            while (cur.moveToNext()) {
                if (cur.getString(2).equals("+94778866891")){
                    if (cur.getString(12).contains("Temperature")){
                        // Date date = new Date(cur.getString(4));
                        //String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
                        sms += cur.getString(12) + "\n";
                    }}}
            Podda = (TextView) findViewById(R.id.textView2);
            Podda.setText(sms);
            //setContentView(view);

            //view.setText(sms);
            //setContentView(view);



        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(Greenhumidity.this, "SMS Sent!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(Greenhumidity.this, "Generic Faliure!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(Greenhumidity.this, "No Service!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(Greenhumidity.this, "Null PUD!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(Greenhumidity.this, "Radio OFF!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){

                    case Activity.RESULT_OK:
                        Toast.makeText(Greenhumidity.this, "SMS Delivered", Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(Greenhumidity.this, "SMS Not Delivered", Toast.LENGTH_SHORT).show();
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

    public void btn1_SEND_SMS_OnClick(View v){

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
