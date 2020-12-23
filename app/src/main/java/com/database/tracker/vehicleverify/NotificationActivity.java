package com.database.tracker.vehicleverify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.notifRecyclerId);
        back=findViewById(R.id.backNotifId);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        dbHelper db = new dbHelper(this);

        List<notifData> datalist = new ArrayList<>();
        Cursor cursor = db.getAllData();

        cursor.moveToFirst();
        do {
            if(cursor.getCount()==0){
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            }
            else {
                notifData data = new notifData(cursor.getInt(cursor.getColumnIndex("ID")) + "", cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("body")), "(" + cursor.getString(cursor.getColumnIndex("time")) + ") " + cursor.getString(cursor.getColumnIndex("date")), cursor.getString(cursor.getColumnIndex("link")));


                cursor.getString(cursor.getColumnIndex("title"));
                cursor.getString(cursor.getColumnIndex("body"));
                cursor.getString(cursor.getColumnIndex("link"));
                cursor.getString(cursor.getColumnIndex("time"));
                cursor.getString(cursor.getColumnIndex("date"));

                datalist.add(data);
            }
        } while (cursor.moveToNext());


        RecyclerAdapter adapter = new RecyclerAdapter(datalist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
}

    @Override
    protected void onResume() {
        super.onResume();

        dbHelper db=new dbHelper(this);

        Cursor c=db.getAllData();

        int newCount=c.getCount();

        db.updateCount(newCount);
    }
}