package com.jurgenbermudez.sl;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Build;
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
    Button btnAdd,btnGetTotal;
    int TableId;
    double Total=0;

    //onCreate activity
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_view);

        ToolbarMain = findViewById(R.id.toolbar);
        txtTotal = findViewById(R.id.txt_Total);
        btnAdd = findViewById(R.id.btn_Add_Item);
        btnGetTotal = findViewById(R.id.btn_get_total);

        TableId= getIntent().getIntExtra("id_table",-1);

        setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(ToolbarMain);

        //Get Total of all items from table

        DbTable dbTable = new DbTable(ItemsViewActivity.this);
        Table table = dbTable.SelectList(TableId);

        if(!dbTable.EditList(TableId,table.getName(),TotalOfItems(),table.getDate_List())){

            Toast.makeText(ItemsViewActivity.this,
                    "Fatal Error to update Total List, Please contact with developer",
                    Toast.LENGTH_SHORT).show();

        }

        //go to activity action

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

        //Get total of item button

        btnGetTotal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                DbTable dbTable = new DbTable(ItemsViewActivity.this);
                Table table = dbTable.SelectList(TableId);

                if(!dbTable.EditList(TableId,table.getName(),TotalOfItems(),table.getDate_List())){

                    Toast.makeText(ItemsViewActivity.this,
                            "Fatal Error to update Total List, Please contact with developer",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Initialization RecyclerView

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        InitRecyclerViewItem();
    }

    //Update total in table when everything finished

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPause() {
        super.onPause();

        DbTable dbTable = new DbTable(this);
        Table table = dbTable.SelectList(TableId);

        if(!dbTable.EditList(TableId,table.getName(),TotalOfItems(),table.getDate_List())){

            Toast.makeText(this,
                    "Fatal Error to update Total List, Please contact with developer",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Method to get the total

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    public double TotalOfItems (){

        ArrayList<Items> ItemsToTotal = getArrayList();
        Total=0;

        for (int i = 0; i<ItemsToTotal.size(); i++){
            Total+=ItemsToTotal.get(i).Total_Value();
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        txtTotal.setText("Total: " + decimalFormat.format(Total));

        return  Total;
    }

    //Initialization of recyclerView

    public void InitRecyclerViewItem(){

        ListAdapterItems listAdapterItems = new ListAdapterItems(getArrayList(),this,TableId);
        RecyclerView recyclerView = findViewById(R.id.recycler_View_Items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterItems);

    }

    //Get ArrayList from table

    ArrayList<Items> getArrayList (){

        DbItems dbItems = new DbItems(ItemsViewActivity.this);

        return dbItems.ShowList(TableId);
    }
}