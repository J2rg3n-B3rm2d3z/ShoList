package com.jurgenbermudez.sl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//Class to use to adapt the listview to a recyclerview

public class ListAdapterTable extends RecyclerView.Adapter<ListAdapterTable.ViewHolder> {

    private ArrayList<Table> TableList;
    private final LayoutInflater Inflater;
    private Context Context;

    //Constructor

    public ListAdapterTable(ArrayList<Table> itemList, Context context){
        this.Inflater = LayoutInflater.from(context);
        this.Context = context;
        this.TableList = itemList;
    }

    //Get total item in the list

    @Override
    public int getItemCount() {
        return TableList.size();
    }

    //Create the view

    @NonNull
    @Override
    public ListAdapterTable.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = Inflater.inflate(R.layout.list_item_table,null);
        return new ViewHolder(view);
    }

    //Select a item in the list

    @Override
    public void onBindViewHolder(@NonNull ListAdapterTable.ViewHolder holder, final int position) {
        holder.binData(TableList.get(position));
    }

    //Set List

    public void setTableList(ArrayList<Table> tableList) {
        TableList = tableList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,total_value,date_list;
        Button btnEdit,btnDelete;

        //Constructor

        ViewHolder(@NonNull View item_view){
            super(item_view);
            imageView = item_view.findViewById(R.id.iconImageView);
            name = item_view.findViewById(R.id.List_Name);
            total_value = item_view.findViewById(R.id.Total_Value_List);
            date_list = item_view.findViewById(R.id.List_Date);
            btnEdit = item_view.findViewById(R.id.btn_Edit);
            btnDelete = item_view.findViewById(R.id.btn_Delete);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(item_view.getContext(),
                            "This is a edit button to list with id: " + TableList.get(getAdapterPosition()).getId(),
                            Toast.LENGTH_SHORT).show();
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(item_view.getContext(),
                            "This is a delete button to list with id: " + TableList.get(getAdapterPosition()).getId(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        }

        //Set data

        @SuppressLint("SetTextI18n")
        void binData(final Table item){
            name.setText(item.getName());
            total_value.setText("Total: " + item.getTotal());
            date_list.setText(item.getDate_List());
        }

    }

}


