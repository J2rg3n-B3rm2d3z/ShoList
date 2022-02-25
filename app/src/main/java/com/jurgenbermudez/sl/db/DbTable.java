package com.jurgenbermudez.sl.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.jurgenbermudez.sl.objectstouse.Table;

import java.util.ArrayList;

public class DbTable extends DbHelper {

    Context context;

    public DbTable(@Nullable Context context) {
        super(context);
        this.context = context;

    }

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

    @SuppressLint("Recycle")
    public ArrayList<Table> ShowList(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Table> listTable = new ArrayList<>();
        Table table = null;
        Cursor cursorTable = null;

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


    public Table SelectList(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Table table = null;
        Cursor cursorTable = null;

        cursorTable = db.rawQuery("SELECT * FROM " + TABLE_CHART + " WHERE id = " + id + " LIMIT 1", null);

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

    public boolean EditList(int id,String name, double total, String date_list){

        boolean Correct = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CHART + " SET Name = '" + name + "', Total = '" + total
                    + "', DateList = '" + date_list + "' WHERE id = " + id + " ");

            Correct = true;

        } catch (Exception ignored) {

        }

        db.close();

        return Correct;

    }


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
