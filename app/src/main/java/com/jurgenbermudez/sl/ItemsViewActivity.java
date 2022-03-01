package com.jurgenbermudez.sl;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.jurgenbermudez.sl.db.DbItems;
import com.jurgenbermudez.sl.db.DbTable;
import com.jurgenbermudez.sl.objectstouse.Items;
import com.jurgenbermudez.sl.objectstouse.ListAdapterItems;
import com.jurgenbermudez.sl.objectstouse.Table;


import java.util.ArrayList;

public class ItemsViewActivity extends AppCompatActivity {

    Toolbar ToolbarMain;
    TextView txtTotal;
    Button btnAdd;
    int TableId;
    double Total=0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_view);

        ToolbarMain = findViewById(R.id.toolbar);
        txtTotal = findViewById(R.id.txt_Total);
        btnAdd = findViewById(R.id.btn_Add_Item);

        TableId= getIntent().getIntExtra("id_table",-1);

        setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(ToolbarMain);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ToItemActivity = new Intent(v.getContext(), ItemsActivity.class);
                ToItemActivity.putExtra("title","Add Item");
                ToItemActivity.putExtra("id_table",TableId);
                ToItemActivity.putExtra("id",-1);
                v.getContext().startActivity(ToItemActivity);
            }
        });
    }

    //Initialization RecyclerView

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        InitRecyclerViewItem();

        DbTable dbTable = new DbTable(this);
        Table table = dbTable.SelectList(TableId);

        if(!dbTable.EditList(TableId,table.getName(),TotalOfItems(),table.getDate_List())){

            Toast.makeText(this, "Fatal Error to update Total List, Please contact with developer", Toast.LENGTH_SHORT).show();
        }


    }

    @SuppressLint("SetTextI18n")
    public double TotalOfItems (){

        ArrayList<Items> ItemsToTotal = getArrayList();
        Total=0;

        for (int i = 0; i<ItemsToTotal.size(); i++){
            Total+=ItemsToTotal.get(i).Total_Value();
        }

        txtTotal.setText("Total: " + Total);

        return  Total;
    }


    public void InitRecyclerViewItem(){

        ListAdapterItems listAdapterItems = new ListAdapterItems(getArrayList(),this,TableId);
        RecyclerView recyclerView = findViewById(R.id.recycler_View_Items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterItems);

    }

    ArrayList<Items> getArrayList (){

        DbItems dbItems = new DbItems(ItemsViewActivity.this);

        return dbItems.ShowList(getIntent().getIntExtra("id_table",-1));
    }
}