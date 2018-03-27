package com.example.kavinda.farmbuddy3;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Home extends AppCompatActivity implements View.OnClickListener{

    private CardView GreenCard;
    private CardView GardenCard;
    private Handler handler = new Handler(Looper.getMainLooper());
    private List alertLists = new ArrayList();
    int PERMITION_REQUEST = 1;


    static SharedPreferences settings;
    static SharedPreferences.Editor editor;
    MenuItem alertItem;
    private List lastList = new ArrayList();
    int lastAlertSize;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GreenCard = (CardView) findViewById(R.id.green_card);
        GardenCard = (CardView) findViewById(R.id.garden_card);
        GreenCard = (CardView) findViewById(R.id.green_card);
        GreenCard.setOnClickListener(this);
        GardenCard.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != getPackageManager().PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, PERMITION_REQUEST);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanForErrors();
                    handler.postDelayed(this, 100);
                }
            }, 300);

        }
    }

    private void scanForErrors(){
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, "date desc");


        while (cur.moveToNext()) {
            if (cur.getString(2).equals("+94768924514") || cur.getString(2).equals("+94778866891")) {
                if (cur.getString(12).toLowerCase().contains("error".toLowerCase())) {
                    //Date date = new Date(cur.getString(4));
                    Date date = new Date(Long.parseLong(cur.getString(4)));
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy h:mm a", Locale.ENGLISH);
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    formattedDate = sdf.format(date);
                    //String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
                    alertLists.add(cur.getString(12));
                    alertLists.add(formattedDate);
                    //Log.d("sms", cur.getString(12));
                }
            }
        }
        try {
            if (Integer.parseInt(settings.getString("alertCount", "default")) != alertLists.size()) {
                alertItem.setIcon(ContextCompat.getDrawable(this, R.drawable.alert_tr));
                    lastList.clear();
                    for (int i = 0; i < alertLists.size(); i=i+2) {
                        lastList.add(alertLists.get(i));
                        lastList.add(alertLists.get(i+1));
                    }
            } else{
                alertItem.setIcon(ContextCompat.getDrawable(this, R.drawable.alert));

            }
        } catch (NullPointerException ne){
            settings = this.getPreferences(MODE_PRIVATE);
            editor = settings.edit();
            editor.putString("alertCount", String.valueOf(alertLists.size()));
            editor.commit();
        } finally {
            if(lastList.isEmpty()) {
                for (int i = 0; i < alertLists.size(); i=i+2) {
                    lastList.add(alertLists.get(i));
                    lastList.add(alertLists.get(i+1));
                }
            }
            lastAlertSize = alertLists.size();
            alertLists.clear();
        }

    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()){

            case R.id.green_card: intent = new Intent(this,Green.class);
                startActivity(intent);
                break;

            case R.id.garden_card: intent = new Intent(this,Garden.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        alertItem = menu.findItem(R.id.msg2);
        alertItem.setIcon(ContextCompat.getDrawable(this, R.drawable.alert));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.msg2:
                        Intent intent;
                        intent = new Intent(Home.this,Message .class);
                        intent.putStringArrayListExtra("alertList", (ArrayList<String>) lastList);
                        startActivity(intent);
                        settings = Home.this.getPreferences(MODE_PRIVATE);
                        editor = settings.edit();
                        editor.putString("alertCount", String.valueOf(lastAlertSize));
                        editor.commit();
                lastList.clear();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scanForErrors();
                handler.postDelayed(this, 100);
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
