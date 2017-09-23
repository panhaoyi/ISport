package com.tcl.isport.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 17-9-1.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="basepedo.db";
    private static final int DATABASE_VERSION=1;

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("CREATE TABLE IF NOT EXISTS stepdata(_id integer primary key autoincrement,step varchar,today varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
