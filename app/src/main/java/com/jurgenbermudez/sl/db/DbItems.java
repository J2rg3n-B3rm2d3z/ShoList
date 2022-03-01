package com.jurgenbermudez.sl.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.jurgenbermudez.sl.objectstouse.Items;
import com.jurgenbermudez.sl.objectstouse.Table;

import java.util.ArrayList;

//Class to adapt Table t_item

public class DbItems extends DbHelper {

    Context context;

    //Constructor

    public DbItems(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Add in the table

    public long InsertList(int tableId, String name, double price,int quantityItem,  double total){

        long id = -1;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("TableId", tableId);
            values.put("Name", name);
            values.put("Price", price);
            values.put("QuantityItem", quantityItem);
            values.put("Total", total);

            id = db.insert(TABLE_LIST, null, values);

            db.close();

        }catch (Exception ignored) {


        }

        return id;
    }

    //Insert ShowTable in List

    @SuppressLint("Recycle")
    public ArrayList<Items> ShowList(int tableId){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Items>listItems = new ArrayList<>();
        Items items = null;
        Cursor cursorTable = null;


        cursorTable = db.rawQuery("SELECT * FROM " + TABLE_LIST + " WHERE TableId = " + tableId, null);

        if(cursorTable.moveToFirst()){
            do{
                items = new Items();
                items.setId(cursorTable.getInt(0));
                items.setTable_Id(cursorTable.getInt(1));
                items.setName(cursorTable.getString(2));
                items.setPrice(cursorTable.getDouble(3));
                items.setQuantity_Item(cursorTable.getInt(4));

                listItems.add(items);

            }while(cursorTable.moveToNext());

        }

        cursorTable.close();
        db.close();

        return listItems;
    }

    //Select a Item in the List

    public Items SelectItem(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Items items = null;
        Cursor cursorTable = null;

        cursorTable = db.rawQuery("SELECT * FROM " + TABLE_LIST + " WHERE id = " + id + " LIMIT 1", null);

        if(cursorTable.moveToFirst()){
            items = new Items();
            items.setId(cursorTable.getInt(0));
            items.setTable_Id(cursorTable.getInt(1));
            items.setName(cursorTable.getString(2));
            items.setPrice(cursorTable.getDouble(3));
            items.setQuantity_Item(cursorTable.getInt(4));
        }

        cursorTable.close();
        db.close();

        return items;
    }

    //Edit a Item in the List

    public boolean EditItem(int id, int tableId, String name, double price,int quantityItem,  double total){

        boolean Correct = false;

        try {

            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("TableId", tableId);
            values.put("Name", name);
            values.put("Price", price);
            values.put("QuantityItem", quantityItem);
            values.put("Total", total);

            db.update(TABLE_LIST,values," Id = ? ",new String[] {String.valueOf(id)});

            /*
            db.execSQL("UPDATE " + TABLE_CHART + " SET Name = '" + name + "', Total = '" + total
                    + "', DateList = '" + date_list + "' WHERE id = " + id + " ");
                    */

            db.close();

            Correct = true;

        } catch (Exception ignored) {

        }

        return Correct;

    }

    //Delete a list in the table

    public boolean DeleteItem(int id){

        boolean Correct = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_LIST + " WHERE Id = "+ id);

            Correct = true;

        } catch (Exception ignored) {

        }

        db.close();

        return Correct;

    }

    public boolean DeleteItems(int idTable){

        boolean Correct = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_LIST + " WHERE TableId = "+ idTable);

            Correct = true;

        } catch (Exception ignored) {

        }

        db.close();

        return Correct;

    }

}
