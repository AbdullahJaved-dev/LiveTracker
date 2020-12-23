package com.database.tracker.vehicleverify;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class converterActivity extends AppCompatActivity {
    WebView currencyWeb;
    AdView conAd;
    InterstitialAd ad;
//    ProgressDialog progDailog;
    ImageView drawer,imgHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);


        imgHome = findViewById(R.id.imgHome);
        ad = new InterstitialAd(converterActivity.this);
        ad.setAdUnitId(getString(R.string.admob_interestial_ad_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);
        ad.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                ad.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

            }
        });


        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(converterActivity.this, MainActivity.class));
                finish();
            }
        });


        if (isNetworkAvailable() == true) {
//        progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
//        progDailog.setCancelable(false);
            final ProgressBar pb;
            pb = findViewById(R.id.conActPb);
            pb.setAlpha(1);
            Query qd = null;
            FirebaseApp.initializeApp(this);
            qd = FirebaseDatabase.getInstance().getReference();
            //Toast.makeText(SignInActivity.this, "1st Q", Toast.LENGTH_SHORT).show();

            ValueEventListener eventResListen = new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String run = dataSnapshot.child("ver").getValue(String.class);
                        if (run.equalsIgnoreCase("han")) {
                            conAd = findViewById(R.id.adViewBottomConverter);
                            AdRequest adRequest = new AdRequest.Builder().build();
                            conAd.loadAd(adRequest);

                            currencyWeb = findViewById(R.id.currencyWeb);
                            currencyWeb.getSettings().setJavaScriptEnabled(true);
                            currencyWeb.loadUrl(dataSnapshot.child("links").child("currencyCon").child("link").getValue(String.class));
                            currencyWeb.canGoBack();
                            currencyWeb.canGoForward();
                            currencyWeb.setWebViewClient(new WebViewClient() {

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                                    return false;
                                }

                                @Override
                                public void onPageFinished(WebView view, final String url) {
//                progDailog.dismiss();
                                    pb.setAlpha(0);
                                }

                            });
                        } else {
                            Toast.makeText(converterActivity.this, "Access Restricted!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            qd.addListenerForSingleValueEvent(eventResListen);

        } else {
            new AlertDialog.Builder(this).setTitle("Network Problem!").setMessage("Device not connected. Please try again!")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent selfI = new Intent(converterActivity.this, sindhActivity.class);
                            startActivity(selfI);
                            finish();
                        }
                    }).setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setCancelable(false).setIcon(R.mipmap.ic_launcher_round).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {

        if (currencyWeb.canGoBack()) {
            currencyWeb.goBack();
        }
        else {
            super.onBackPressed();
        }

    }

    public void goBack(View view) {
        onBackPressed();
    }
}

