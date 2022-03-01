package com.jurgenbermudez.sl.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.jurgenbermudez.sl.objectstouse.Table;

import java.util.ArrayList;

//Class to adapt Table t_chart

public class DbTable extends DbHelper {

    Context context;

    //Constructor

    public DbTable(@Nullable Context context) {
        super(context);
        this.context = context;

    }

    //Add in the table

    public long InsertList(String name, double total, String date_list){

        long id = -1;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("Name", name);
            values.put("Total", total);
            values.put("DateList", date_list);

            id = db.insert(TABLE_CHART, null, values);

            db.close();

        }catch (Exception ignored) {


        }

        return id;
    }

    //Insert ShowTable in List

    @SuppressLint("Recycle")
    public ArrayList<Table> ShowList(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Table> listTable = new ArrayList<>();
        Table table;
        Cursor cursorTable;

        cursorTable = db.rawQuery("SELECT * FROM " + TABLE_CHART, null);

        if(cursorTable.moveToFirst()){
            do{
                table = new Table();
                table.setId(cursorTable.getInt(0));
                table.setName(cursorTable.getString(1));
                table.setTotal(cursorTable.getDouble(2));
                table.setDate_List(cursorTable.getString(3));

                listTable.add(table);

            }while(cursorTable.moveToNext());

        }

        cursorTable.close();
        db.close();

        return listTable;
    }

    //Select a List in the table

    public Table SelectList(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Table table = null;
        Cursor cursorTable;

        cursorTable = db.rawQuery("SELECT * FROM " + TABLE_CHART + " WHERE id = " + id + " LIMIT 1",
                null);

        if(cursorTable.moveToFirst()){
            table = new Table();
            table.setId(cursorTable.getInt(0));
            table.setName(cursorTable.getString(1));
            table.setTotal(cursorTable.getDouble(2));
            table.setDate_List(cursorTable.getString(3));
        }

        cursorTable.close();
        db.close();

        return table;
    }

    //Edit a List in the table

    public boolean EditList(int id,String name, double total, String date_list){

        boolean Correct = false;

        try {

            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("Name", name);
            values.put("Total", total);
            values.put("DateList", date_list);

            db.update(TABLE_CHART,values," Id = ? ",new String[] {String.valueOf(id)});

            db.close();

            Correct = true;

        } catch (Exception ignored) {

        }

        return Correct;

    }

    //Delete a list in the table

    public boolean DeleteList(int id){

        boolean Correct = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CHART + " WHERE Id = "+ id);

            Correct = true;

        } catch (Exception ignored) {

        }

        db.close();

        return Correct;
    }
}
