package com.jurgenbermudez.sl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jurgenbermudez.sl.db.DbTable;
import com.jurgenbermudez.sl.objectstouse.Table;

import java.util.Date;

public class TableActivity extends AppCompatActivity {

    //Interface variables

    DrawerLayout DrawerLayoutMain;
    NavigationView NavigationViewMain;
    Toolbar ToolbarMain;
    EditText txtListName;
    Button btnAction;

    //onCreated Method

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //UI

        DrawerLayoutMain = findViewById(R.id.drawer_layout);
        NavigationViewMain = findViewById(R.id.nav_view);
        ToolbarMain = findViewById(R.id.toolbar);

        txtListName = findViewById(R.id.txtNameList);
        btnAction = findViewById(R.id.btn_Action_list);

        //Setup Toolbar

        setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(ToolbarMain);
        ToolbarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Add information

        if(getIntent().getStringExtra("title").equals(getString(R.string.Add_list_tittle))) {

            btnAction.setText(getString(R.string.btn_add));

            btnAction.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {

                    //Add data

                    if (txtListName.getText().toString().isEmpty())
                        Toast.makeText(TableActivity.this,
                                getString(R.string.UserMessage1),
                                Toast.LENGTH_LONG).show();
                    else {

                        Date date = new Date();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                                new SimpleDateFormat("E dd/MM/yyyy");

                        DbTable dbTable = new DbTable(TableActivity.this);
                        long id = dbTable.InsertList(txtListName.getText().toString(), 0,
                                simpleDateFormat.format(date));

                        if (id > 0)
                            Toast.makeText(TableActivity.this, getString(R.string.UserMessage2),
                                    Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(TableActivity.this,
                                    getString(R.string.Message3),
                                    Toast.LENGTH_LONG).show();

                        finish();
                    }
                }
            });
        }

        //Edit information

        else if (getIntent().getStringExtra("title").equals(getString(R.string.Edit_list_tittle))){

            if(getIntent().getIntExtra("id",-1) == -1) {

                Toast.makeText(this,
                        getString(R.string.Message4),
                        Toast.LENGTH_LONG).show();
                finish();
            }
            else {

                //Edit data

                DbTable dbTable = new DbTable(this);
                Table table = dbTable.SelectList(getIntent().getIntExtra("id", -1));
                txtListName.setText(table.getName());
                btnAction.setText(getString(R.string.btn_edit));

                btnAction.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {

                        if (txtListName.getText().toString().isEmpty())
                            Toast.makeText(TableActivity.this, getString(R.string.UserMessage1),
                                    Toast.LENGTH_LONG).show();
                        else {

                            DbTable dbTable = new DbTable(TableActivity.this);

                            if (dbTable.EditList(table.getId(), txtListName.getText().toString(),
                                    table.getTotal(), table.getDate_List()))
                                Toast.makeText(TableActivity.this,
                                        getString(R.string.UserMessage3), Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(TableActivity.this,
                                        getString(R.string.Message7), Toast.LENGTH_LONG).show();

                            finish();
                        }
                    }
                });
            }
        }
        else{
            Toast.makeText(this,getString(R.string.Message5), Toast.LENGTH_LONG).show();
            finish();
        }
    }


}