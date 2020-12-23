package com.database.tracker.vehicleverify;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class islamActivity extends AppCompatActivity {
WebView islweb;
    ImageView drawer,imgHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islam);

        final ProgressBar pb;
        pb = findViewById(R.id.islamActPb);
        imgHome = findViewById(R.id.imgHome);
        pb.setAlpha(1);
        islweb=findViewById(R.id.islamabadWeb);
        islweb.getSettings().setJavaScriptEnabled(true);
        islweb.loadUrl("http://islamabadexcise.gov.pk/");
        islweb.canGoBack();
//        String newUA = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36";
//        islweb.getSettings().setUserAgentString(newUA);
        islweb.canGoForward();

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(islamActivity.this, MainActivity.class));
                finish();
            }
        });


        islweb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                if(url.contains("vehform.php")){
                    islweb.scrollBy(0,800);
                }
            }


            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        islweb.findAllAsync("Online Check Vehicle Detail");
                        islweb.findAllAsync("Vehicle Detail");
            pb.setAlpha(0);



// Dispatch touch event to view

                    }
                },1000);

            }

        });


    }


    @Override
    public void onBackPressed() {

        if (islweb.canGoBack()) {
            islweb.goBack();
        }
        else {
            super.onBackPressed();
        }
    }


    public void goBack(View view) {
        onBackPressed();
    }

}
