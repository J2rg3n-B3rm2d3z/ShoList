package com.jurgenbermudez.sl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.jurgenbermudez.sl.objectstouse.Table;

import java.util.ArrayList;

public class ItemsViewActivity extends AppCompatActivity {

    Toolbar ToolbarMain;

    ArrayList<Table> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_view);

        ToolbarMain = findViewById(R.id.toolbar);

        setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(ToolbarMain);




    }
}