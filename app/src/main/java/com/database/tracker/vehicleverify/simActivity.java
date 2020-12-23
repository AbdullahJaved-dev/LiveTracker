package com.database.tracker.vehicleverify;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebSettings;
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

public class simActivity extends AppCompatActivity {
    WebView simWeb;
    AdView simAd;
    InterstitialAd ad;
//    ProgressDialog progDailog;
    String privacy, terms, moreApps;
    ImageView drawer,imgHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);

        ad = new InterstitialAd(simActivity.this);
        ad.setAdUnitId(getString(R.string.admob_interestial_ad_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);






        ad.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //Toast.makeText(simActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
                super.onAdLoaded();
                ad.show();
                //Toast.makeText(simActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                //Toast.makeText(simActivity.this, i+"", Toast.LENGTH_SHORT).show();
                super.onAdFailedToLoad(i);
                //Toast.makeText(simActivity.this, i+"", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdOpened() {
                //Toast.makeText(simActivity.this, "opened", Toast.LENGTH_SHORT).show();
                super.onAdOpened();
                //Toast.makeText(simActivity.this, "opened", Toast.LENGTH_SHORT).show();
            }
        });


        imgHome = findViewById(R.id.imgHome);

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(simActivity.this, MainActivity.class));
                finish();
            }
        });





        if (isNetworkAvailable() == true) {
//            progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
//            progDailog.setCancelable(false);
            final ProgressBar pb;
            pb = findViewById(R.id.simActPb);
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
                            simAd = findViewById(R.id.adViewBottomSim);
                            final AdRequest adRequest = new AdRequest.Builder().build();
                            simAd.loadAd(adRequest);
                            simWeb = findViewById(R.id.simWeb);
                            simWeb.getSettings().setJavaScriptEnabled(true);
                            simWeb.loadUrl(dataSnapshot.child("links").child("simDB").child("link").getValue(String.class));
                            simWeb.canGoBack();
                            simWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                            simWeb.canGoForward();
                            simWeb.setWebViewClient(new WebViewClient() {

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {


                                    return false;
                                }

                                @Override
                                public void onPageFinished(WebView view, final String url) {
//                                    progDailog.dismiss();
                                    pb.setAlpha(0);

                                }
                            });
                        } else {
                            Toast.makeText(simActivity.this, "Access Restricted!", Toast.LENGTH_SHORT).show();
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
                            Intent selfI = new Intent(simActivity.this, simActivity.class);
                            startActivity(selfI);
                            finish();
                        }
                    }).setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setIcon(R.mipmap.ic_launcher_round).show();
        }

    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*private class custom extends WebView {
        public custom(Context context) {
            super(context);
        }
    }*/
   private class  numpad extends WebView {
        public numpad(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public numpad(Context context) {
            super(context);
            EditorInfo outAttrs = null;
            if ((outAttrs.inputType & InputType.TYPE_CLASS_PHONE) == InputType.TYPE_CLASS_PHONE)
            {
                outAttrs.inputType |= InputType.TYPE_CLASS_PHONE;
            }

        }
    }


    @Override
    public void onBackPressed() {

        if (simWeb.canGoBack()) {
            simWeb.goBack();
        }
        else {
            super.onBackPressed();
        }

    }


    public void goBack(View view) {
        onBackPressed();
    }
}
