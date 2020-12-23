package com.database.tracker.vehicleverify;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

public class privacyActivity extends AppCompatActivity {
    LinearLayout privacyB, termsB;
    Button acceptB;
    String privacyL, termsL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        Paper.init(this);

        privacyB = findViewById(R.id.policyID);
        termsB = findViewById(R.id.termsID);
        acceptB = findViewById(R.id.acceptB);
        Query qd = null;
        FirebaseApp.initializeApp(this);
        qd = FirebaseDatabase.getInstance().getReference();
        //Toast.makeText(SignInActivity.this, "1st Q", Toast.LENGTH_SHORT).show();

//        ValueEventListener eventResListen = new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    privacyL=dataSnapshot.child("links").child("privacy").child("link").getValue(String.class);
//                    termsL=dataSnapshot.child("links").child("terms").child("link").getValue(String.class);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        qd.addListenerForSingleValueEvent(eventResListen);


        privacyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getLink("privacy")));
                startActivity(browserIntent);
            }
        });
        termsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getLink("terms")));
                startActivity(browserIntent);
            }
        });
        acceptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write(Common.terms, "ok");
                Intent intent = new Intent(privacyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(privacyActivity.this)
                .setTitle("Exit")
                .setMessage("You really want to Exit?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing done
                    }
                })
                .setIcon(R.mipmap.ic_launcher_round)
                .show();

    }


    private String getLink(String str) {
        for(int i=0; i<Utils.globalList.size(); i++){
            if(Utils.globalList.get(i).linkName.equals(str)){
                return  Utils.globalList.get(i).getLinkUrl();
            }
        }
        return "";
    }

}
