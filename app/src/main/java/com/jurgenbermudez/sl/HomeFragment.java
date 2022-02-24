package com.jurgenbermudez.sl;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    ArrayList<Table> tableList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Initialization RecyclerView

        //Play with the format of the date
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd/MM/yyyy");
        String str_date = simpleDateFormat.format(date);

        tableList = new ArrayList<>();

        tableList.add(new Table(1,"Restaurant",1300,str_date));
        tableList.add(new Table(2,"Shopping",1100,str_date));
        tableList.add(new Table(3,"Disc Shop",1800,str_date));
        tableList.add(new Table(4,"Pet Shop",9000,str_date));
        tableList.add(new Table(5,"House Shop",3500,str_date));
        tableList.add(new Table(6,"Flower Shop",2500,str_date));
        tableList.add(new Table(7,"Doris Shop",4100,str_date));
        tableList.add(new Table(8,"Jurgen Shop",2500,str_date));

        ListAdapterTable listAdapterTable = new ListAdapterTable(tableList,getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapterTable);

        return view;
    }


}