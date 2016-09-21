package com.example.kdvu.timer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final String TAG = "com.example.kdvu.timer";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "times.db"; //Name of the file to be saved to the device

    public static final String TABLE_TIMES = "times"; //Name of table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MIN = "min";
    public static final String COLUMN_SEC = "sec";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TIMES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_HOUR + " INTEGER," +
                COLUMN_MIN + " INTEGER," +
                COLUMN_SEC + " INTEGER" + ")";
        db.execSQL(query);
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMES);
        onCreate(db);
    }

    //Add a new time
    public void addNewTime(Times time){
        //List of values
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUR, time.get_hour());
        values.put(COLUMN_MIN, time.get_min());
        values.put(COLUMN_SEC, time.get_sec());
        SQLiteDatabase db = getWritableDatabase();

        long numRows = DatabaseUtils.queryNumEntries(db, TABLE_TIMES);
        Log.d(TAG, "rows: " + numRows);

        if(numRows == 3){
            db.update(TABLE_TIMES, values, "_id=3", null);
            /*String query = "SELECT * FROM " + TABLE_TIMES + " WHERE hour = 0;";
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            int id = c.getInt(c.getColumnIndex("_id"));
            db.execSQL(query);
            Log.d(TAG, "id=" + id);*/
        } else{
            db.insert(TABLE_TIMES, null, values);
        }
        db.close();
    }

    public void clearTimes(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TIMES);
        db.execSQL("vacuum");
        //db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + TABLE_TIMES + "'");
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_TIMES + "'");

        db.close();
    }

    public void insertTimes(int[][] time){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TIMES + " WHERE 1;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        int MAX = 3;
        int i = 0;

        //time[0][0] = c.getInt(c.getColumnIndex("hour"));
        //Log.d(TAG, "" + time[0][0]);

        boolean ma = false;
        while(!c.isAfterLast() && i<MAX){
            time[i][0] = c.getInt(c.getColumnIndex("hour"));
            time[i][1] = c.getInt(c.getColumnIndex("min"));
            time[i][2] = c.getInt(c.getColumnIndex("sec"));
            i++;
            c.moveToNext();
        }
        db.close();
    }

}
