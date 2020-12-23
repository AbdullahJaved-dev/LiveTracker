package com.database.tracker.vehicleverify;

import android.content.Intent;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import androidx.appcompat.app.AppCompatActivity;

public class vehicleActivity extends AppCompatActivity {
    RelativeLayout punjabBut,sindhBut,kpkBut,islBut;
    AdView vehicaleAd;
    ImageView imgBack;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        punjabBut=findViewById(R.id.punjabLay);
        sindhBut=findViewById(R.id.sindhLay);
        kpkBut=findViewById(R.id.kpkLay);
        islBut=findViewById(R.id.islamLay);
        imgBack = findViewById(R.id.imgBack);

        

        vehicaleAd = findViewById(R.id.adViewBottomVehicle);
        AdRequest adRequest = new AdRequest.Builder().build();
        vehicaleAd.loadAd(adRequest);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        punjabBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent punjI=new Intent(vehicleActivity.this,punjabActivity.class);
                startActivity(punjI);
            }
        });
        sindhBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sindhI=new Intent(vehicleActivity.this,sindhActivity.class);
                startActivity(sindhI);
            }
        });
        islBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent islI=new Intent(vehicleActivity.this,islamActivity.class);
                startActivity(islI);
            }
        });
        kpkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kpkI=new Intent(vehicleActivity.this,kpkActivity.class);
                startActivity(kpkI);
            }
        });
    }


    public void goBack(View view) {
        onBackPressed();
    }
}
