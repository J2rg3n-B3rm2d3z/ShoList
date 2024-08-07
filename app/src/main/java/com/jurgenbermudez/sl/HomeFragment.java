package com.jurgenbermudez.sl;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jurgenbermudez.sl.db.DbTable;
import com.jurgenbermudez.sl.objectstouse.ListAdapterTable;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    View view;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Button action

        Button btn_add = view.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Go to the Activity Action

                Intent ToTableActivity = new Intent(getContext(),TableActivity.class);
                ToTableActivity.putExtra("title",getString(R.string.Add_list_tittle));
                ToTableActivity.putExtra("id",-1);
                startActivity(ToTableActivity);
            }
        });

        return view;
    }

    //Initialization RecyclerView

    @Override
    public void onResume() {
        super.onResume();
        InitRecyclerView(view);
    }

    public void InitRecyclerView(View view){

        //Create the recycler view

        DbTable dbTable = new DbTable(getContext());

        ListAdapterTable listAdapterTable = new ListAdapterTable(dbTable.ShowList(),getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapterTable);

    }


}