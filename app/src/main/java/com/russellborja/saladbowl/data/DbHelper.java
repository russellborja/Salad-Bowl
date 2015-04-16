package com.russellborja.saladbowl.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by russellborja on 15-04-10.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "saladbowl.db";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SALAD_TABLE = "CREATE TABLE " + Contract.NameEntry.TABLE_NAME + " (" +
                Contract.NameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.NameEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract.NameEntry.COLUMN_TEAM + " TEXT NOT NULL, " +
                Contract.NameEntry.COLUMN_WORDS + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_SALAD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.NameEntry.TABLE_NAME);
        onCreate(db);
    }
}
