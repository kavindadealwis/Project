package com.example.kavinda.farmbuddy3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Message extends AppCompatActivity {

    List list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        list = getIntent().getStringArrayListExtra("alertList");
        for(int i = 0; i < list.size(); i = i+ 2) {
            Log.d("ere", String.valueOf(i));
            LinearLayout vault = (LinearLayout) findViewById(R.id.alerts);
            AlertItem alertItem = new AlertItem(this);
            alertItem.msg.setText(String.valueOf(list.get(i)));
            alertItem.date.setText(String.valueOf(list.get(i+1)));
            vault.addView(alertItem);
        }
    }
}
