package com.jurgenbermudez.sl;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    List<Table> tableList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Initialization RecyclerView

        tableList = new ArrayList<>();

        tableList.add(new Table(1,"Restaurant",1300));
        tableList.add(new Table(2,"Shopping",1100));
        tableList.add(new Table(3,"Disc Shop",1800));
        tableList.add(new Table(4,"Pet Shop",9000));
        tableList.add(new Table(5,"House Shop",3500));
        tableList.add(new Table(6,"Flower Shop",3500));
        tableList.add(new Table(7,"Doris Shop",3500));
        tableList.add(new Table(8,"Jurgen Shop",3500));

        ListAdapterTable listAdapterTable = new ListAdapterTable(tableList,getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapterTable);


        return view;
    }


}