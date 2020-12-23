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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.appcompat.app.AppCompatActivity;

public class kpkActivity extends AppCompatActivity {
    WebView kpkweb;
    InterstitialAd ad;
//    ProgressDialog progDailog;
ImageView drawer,imgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kpk);


        ad = new InterstitialAd(kpkActivity.this);
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

        imgHome = findViewById(R.id.imgHome);

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(kpkActivity.this, MainActivity.class));
                finish();
            }
        });


        final ProgressBar pb;
        pb = findViewById(R.id.kpkActPb);
        pb.setAlpha(1);
        if (isNetworkAvailable() == true) {

            Query qd = null;
            FirebaseApp.initializeApp(this);
            qd = FirebaseDatabase.getInstance().getReference();
            //Toast.makeText(SignInActivity.this, "1st Q", Toast.LENGTH_SHORT).show();

//                            progDailog = ProgressDialog.show(kpkActivity.this, "Loading", "Please wait...", true);
//                            progDailog.setCancelable(false);

                            kpkweb = findViewById(R.id.kpkWeb);
                            kpkweb.getSettings().setJavaScriptEnabled(true);
                            kpkweb.loadUrl(getLink("kpkTr"));
                            kpkweb.canGoBack();
                            kpkweb.canGoForward();
                            kpkweb.setWebViewClient(new WebViewClient() {

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
            new AlertDialog.Builder(this).setTitle("Network Problem!").setMessage("Device not connected. Please try again!")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent selfI = new Intent(kpkActivity.this, sindhActivity.class);
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

    private String getLink(String str) {
        for(int i=0; i<Utils.globalList.size(); i++){
            if(Utils.globalList.get(i).linkName.equals(str)){
                return  Utils.globalList.get(i).getLinkUrl();
            }
        }
        return "";
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {

        if (kpkweb.canGoBack()) {
            kpkweb.goBack();
        }
        else {
            super.onBackPressed();
        }
    }


    public void goBack(View view) {
        onBackPressed();
    }

}
