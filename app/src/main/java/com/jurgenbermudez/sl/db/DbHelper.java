package com.jurgenbermudez.sl.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    //Constant to use in Database

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shopping.db";
    public static final String TABLE_CHART = "t_chart";
    public static final String TABLE_LIST = "t_item";

    //Constructor

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //Create Tables

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CHART + "(" +
                " Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " Name TEXT NOT NULL, " +
                " Total REAL NOT NULL, " +
                " DateList TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_LIST + "(" +
                " Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " TableId INTEGER NOT NULL, " +
                " Name TEXT NOT NULL, " +
                " Price REAL NOT NULL," +
                " QuantityItem INTEGER NOT NULL, " +
                " Total REAL NOT NULL, "+
                " FOREIGN KEY(TableId) REFERENCES TABLE_CHART(Id)"+
                ")");
    }

    //This method exec when the version change

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_CHART);
        db.execSQL("DROP TABLE " + TABLE_LIST);
        onCreate(db);
    }


}
