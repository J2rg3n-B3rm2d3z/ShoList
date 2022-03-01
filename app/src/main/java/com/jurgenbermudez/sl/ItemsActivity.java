package com.jurgenbermudez.sl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jurgenbermudez.sl.db.DbItems;
import com.jurgenbermudez.sl.db.DbTable;
import com.jurgenbermudez.sl.objectstouse.Items;
import com.jurgenbermudez.sl.objectstouse.Table;

import java.util.Date;

public class ItemsActivity extends AppCompatActivity {

    Toolbar ToolbarMain;
    Button btnAction;
    EditText txtName,txtPrice,txtCount;

    //onCreate code

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        ToolbarMain = findViewById(R.id.toolbar);
        btnAction = findViewById(R.id.btn_Action_item);

        txtName = findViewById(R.id.txt_Name_Item);
        txtCount = findViewById(R.id.txt_Count_Item);
        txtPrice = findViewById(R.id.txt_Price_Item);


        int TableId= getIntent().getIntExtra("id_table",-1);
        int Id = getIntent().getIntExtra("id",-1);

        setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(ToolbarMain);

        //Add Item

        if(getIntent().getStringExtra("title").equals("Add Item")) {

            btnAction.setText("Add");

            btnAction.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {

                    //Validation

                    if(txtName.getText().toString().isEmpty() ||
                            txtCount.getText().toString().isEmpty() ||
                            txtPrice.getText().toString().isEmpty()){

                        Toast.makeText(ItemsActivity.this,"A field is empty.",
                                Toast.LENGTH_LONG).show();

                    }
                    else if(TableId == -1){

                        Toast.makeText(ItemsActivity.this,
                                "Fatal error IdTable = -1, Please contact with developer",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{

                        boolean doubleValid = false;

                        try {

                            Double.parseDouble(txtPrice.getText().toString());
                            doubleValid = true;
                        }
                        catch (Exception ignored){

                        }

                        //if everything is okay

                        if(doubleValid) {

                            Items items = new Items(-1, TableId, txtName.getText().toString(),
                                    Double.parseDouble(txtPrice.getText().toString())
                                    , Integer.parseInt(txtCount.getText().toString()));


                            DbItems dbItems = new DbItems(ItemsActivity.this);

                            long id = dbItems.InsertList(TableId,
                                    items.getName(), items.getPrice(),
                                    items.getQuantity_Item(),
                                    items.Total_Value());

                            if (id != -1) {
                                Date date = new Date();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                                        new SimpleDateFormat("E dd/MM/yyyy");

                                DbTable dbTable = new DbTable(ItemsActivity.this);

                                Table table = dbTable.SelectList(TableId);

                                if (dbTable.EditList(table.getId(), table.getName(),
                                        table.getTotal(), simpleDateFormat.format(date))) {

                                    Toast.makeText(ItemsActivity.this,
                                            "Item was added", Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(ItemsActivity.this,
                                            "Fatal Error Table Doesn't update, Please contact with developer",
                                            Toast.LENGTH_SHORT).show();

                                }
                            } else {

                                Toast.makeText(ItemsActivity.this,
                                        "Fatal Error id = -1, Please contact with developer",
                                        Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                        else{

                            Toast.makeText(ItemsActivity.this,"Invalid price.",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                }
            });
        }

        //Edit item

        else if(getIntent().getStringExtra("title").equals("Edit Item")){

            //Validation

            if(Id == -1) {

                Toast.makeText(ItemsActivity.this,
                        "Fatal error Id = -1, Please contact with developer",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(TableId == -1)
            {
                Toast.makeText(ItemsActivity.this,
                        "Fatal error Id = -1, Please contact with developer",
                        Toast.LENGTH_SHORT).show();
                finish();

            }
            else{

                //If everything is okay

                DbItems dbItems = new DbItems(ItemsActivity.this);
                Items items = dbItems.SelectItem(Id);

                txtName.setText(items.getName());
                txtCount.setText(String.valueOf(items.getQuantity_Item()));
                txtPrice.setText(String.valueOf(items.getPrice()));

                btnAction.setText("Edit");

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {

                        //Validation field text

                        if(txtName.getText().toString().isEmpty() ||
                                txtCount.getText().toString().isEmpty() ||
                                txtPrice.getText().toString().isEmpty()){

                            Toast.makeText(ItemsActivity.this,"A field is empty.",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            boolean doubleValid = false;

                            try {

                                Double.parseDouble(txtPrice.getText().toString());
                                doubleValid = true;
                            }
                            catch (Exception ignored){

                            }

                            //if everything is okay

                            if(doubleValid) {

                                items.setQuantity_Item(Integer.parseInt(txtCount.getText().toString()));
                                items.setPrice(Double.parseDouble(txtPrice.getText().toString()));
                                items.setName(txtName.getText().toString());

                                if (dbItems.EditItem(Id, TableId, items.getName(),
                                        items.getPrice(), items.getQuantity_Item(),
                                        items.Total_Value())) {

                                    Date date = new Date();
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                                            new SimpleDateFormat("E dd/MM/yyyy");

                                    DbTable dbTable = new DbTable(ItemsActivity.this);

                                    Table table = dbTable.SelectList(TableId);

                                    if (dbTable.EditList(table.getId(), table.getName(),
                                            table.getTotal(), simpleDateFormat.format(date))) {

                                        Toast.makeText(ItemsActivity.this,
                                                "Item was updated", Toast.LENGTH_SHORT).show();

                                    } else {

                                        Toast.makeText(ItemsActivity.this,
                                                "Fatal Error Table Doesn't update, Please contact with developer",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                } else {

                                    Toast.makeText(ItemsActivity.this,
                                            "Error update data, Please contact with developer",
                                            Toast.LENGTH_SHORT).show();

                                }
                                finish();
                            }
                            else{

                                Toast.makeText(ItemsActivity.this,"Invalid price.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        }
    }
}