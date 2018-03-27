package com.example.kavinda.farmbuddy3;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Garden extends AppCompatActivity implements View.OnClickListener{

    private CardView GardenWell;
    private CardView GardenTank;
    private CardView GardenSoil;
    private CardView GardenRain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);

        getSupportActionBar().setTitle("Garden Plantation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GardenWell = (CardView) findViewById(R.id.well_card);
        GardenTank = (CardView) findViewById(R.id.tank_card);
        GardenSoil = (CardView) findViewById(R.id.gardensoil_card);
        GardenRain = (CardView) findViewById(R.id.rain_card);

        GardenWell.setOnClickListener(this);
        GardenTank.setOnClickListener(this);
        GardenSoil.setOnClickListener(this);
        GardenRain.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()){

            case R.id.well_card:intent = new Intent(this,Gardenwell.class);
                startActivity(intent);
                break;

            case R.id.tank_card: intent = new Intent(this,Gardentank.class);
                startActivity(intent);
                break;

            case R.id.gardensoil_card: intent = new Intent(this,Gardensoil.class);
                startActivity(intent);
                break;

            case R.id.rain_card: intent = new Intent(this,Gardenrain.class);
                startActivity(intent);
                break;
        }


    }
}
