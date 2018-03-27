package com.example.kavinda.farmbuddy3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Green extends AppCompatActivity implements View.OnClickListener{

    private CardView GreenHumidity;
    private CardView GreenSoil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);

        getSupportActionBar().setTitle("Green");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GreenHumidity = (CardView) findViewById(R.id.humidity_card);
        GreenSoil = (CardView) findViewById(R.id.greensoil_card);

        GreenHumidity.setOnClickListener(this);
        GreenSoil.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()){

            case R.id.humidity_card: intent = new Intent(this,Greenhumidity.class);
                startActivity(intent);
                break;

            case R.id.greensoil_card: intent = new Intent(this,Greensoil.class);
                startActivity(intent);
                break;
        }
    }
}
