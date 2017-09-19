package com.tcl.isport.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tcl.isport.Bean.StepData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-8-25.
 */
public class DbUtil {
    private static DBOpenHelper dbOpenHelper;
    private static SQLiteDatabase db;

    public static void createDb(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
    }

    public static void closeDb() {
        db.close();
        dbOpenHelper.close();
    }

    public static List<StepData> getQueryByDate(String today) {
        List<StepData> list = new ArrayList<StepData>();
        Cursor cursor = db.query("stepdata", null, "today=?", new String[]{today}, null, null, null);
        while (cursor.moveToNext()) {
            StepData stepData = new StepData();
            stepData.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            stepData.setStep(cursor.getString(cursor.getColumnIndex("step")));
            stepData.setToday(cursor.getString(cursor.getColumnIndex("today")));
            list.add(stepData);
        }
        return list;
    }

    public static void insert(StepData data) {
        ContentValues values = new ContentValues();
        values.put("step", data.getStep());
        values.put("today", data.getToday());
        db.insert("stepdata", "_id", values);
    }

    public static void update(StepData data) {
        ContentValues values = new ContentValues();
        values.put("step", data.getStep());
        db.update("stepdata", values, "_id=?", new String[]{data.get_id().toString()});
    }
}
