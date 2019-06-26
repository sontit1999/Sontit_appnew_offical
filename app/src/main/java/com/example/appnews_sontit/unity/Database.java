package com.example.appnews_sontit.unity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // phương thức truy vấn datababase ko có kq trả về: Creat,insert,update,delete;
    public void QueryData(String query){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(query);
    }
    // phương thức truy vấn datababase có kq trả về: select
    public Cursor Getdata(String query){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(query,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
