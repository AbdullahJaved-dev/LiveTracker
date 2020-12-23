package com.database.tracker.vehicleverify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "livetrackerDB";

    public static final String TABLE_NAME = "notificaions";
    public static final String TABLE1_NAME = "notificaionsCount";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "title";
    public static final String COL_3 = "body";
    public static final String COL_4 = "link";
    public static final String COL_5 = "time";
    public static final String COL_6 = "date";

    public static final String T1COL_1 = "count";
    public static final String T1COL_2 = "id";


    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID Integer , title TEXT, body TEXT, link TEXT, time TEXT, date TEXT)");
        db.execSQL("create table " + TABLE1_NAME + " (count Integer,id Integer)");


        ContentValues contentValues = new ContentValues();

        contentValues.put(T1COL_1, 0);
        contentValues.put(T1COL_2, 1);

        db.insert(TABLE1_NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);

        onCreate(db);
    }

//    public boolean insertData(String JavaScriptEnabled, Integer id, String homepage, String images,String searchEngine, String webMode) {

//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,JavaScriptEnabled);
//        contentValues.put(COL_2,id);
//        contentValues.put(COL_3,homepage);
//        contentValues.put(COL_4,images);
//        contentValues.put(COL_5,searchEngine);
//        contentValues.put(COL_6,webMode);
//
//        long result = db.insert(TABLE_NAME,null ,contentValues);
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }

    public boolean insertNotification(Integer id,String title, String body, String link, String time, String date) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, body);
        contentValues.put(COL_4, link);
        contentValues.put(COL_5, time);
        contentValues.put(COL_6, date);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public void deleteNotification(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_1 + " = ?", new String[]{Id});
    }
    public Cursor getCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE1_NAME,null);
        return res;
    }

    public boolean updateCount(Integer count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1COL_1,count);
        db.update(TABLE1_NAME, contentValues, "id = ?",new String[] { "1" });
        return true;
    }



}