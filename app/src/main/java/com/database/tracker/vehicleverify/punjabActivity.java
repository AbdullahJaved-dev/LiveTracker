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
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import androidx.appcompat.app.AppCompatActivity;

public class punjabActivity extends AppCompatActivity {
    WebView punjabWeb;
    AdView punjabAd;
    InterstitialAd ad;
    ImageView drawer,imgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punjab);

        imgHome = findViewById(R.id.imgHome);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(punjabActivity.this, MainActivity.class));
                finish();
            }
        });

        ad = new InterstitialAd(punjabActivity.this);
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

        if (isNetworkAvailable() == true) {
//            progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
//            progDailog.setCancelable(false);

            final ProgressBar pb;
            pb = findViewById(R.id.punActPb);
            pb.setAlpha(1);
                punjabAd = findViewById(R.id.adViewBottomPunjab);
                AdRequest adRequests = new AdRequest.Builder().build();
                punjabAd.loadAd(adRequests);

                punjabWeb = findViewById(R.id.punjabWeb);
                punjabWeb.getSettings().setJavaScriptEnabled(true);
                punjabWeb.loadUrl(getLink("punjabTr"));
                punjabWeb.canGoBack();
                punjabWeb.canGoForward();
                punjabWeb.setWebViewClient(new WebViewClient() {

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
                            Intent selfI = new Intent(punjabActivity.this, sindhActivity.class);
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

    private String getLink(String str) {
        for(int i=0; i<Utils.globalList.size(); i++){
            if(Utils.globalList.get(i).linkName.equals(str)){
                return  Utils.globalList.get(i).getLinkUrl();
            }
        }
        return "";
    }


    @Override
    public void onBackPressed() {

        if (punjabWeb.canGoBack()) {
            punjabWeb.goBack();
        }
        else {
            super.onBackPressed();
        }

    }

    public void goBack(View view) {
        onBackPressed();
    }


}
