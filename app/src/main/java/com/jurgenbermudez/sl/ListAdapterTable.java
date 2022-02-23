package com.jurgenbermudez.sl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

//Class to use to adapt the listview to a recyclerview

public class ListAdapterTable extends RecyclerView.Adapter<ListAdapterTable.ViewHolder> {

    private List<Table> TableList;
    private final LayoutInflater Inflater;
    private Context Context;

    //Constructor

    public ListAdapterTable(List<Table> itemList, Context context){
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

    public void setTableList(List<Table> tableList) {
        TableList = tableList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,total_value;

        //Constructor

        ViewHolder(@NonNull View item_view){
            super(item_view);
            imageView = item_view.findViewById(R.id.iconImageView);
            name = item_view.findViewById(R.id.List_Name);
            total_value = item_view.findViewById(R.id.Total_Value_List);
        }

        //Set data

        void binData(final Table item){
            name.setText(item.getName());
            total_value.setText(String.valueOf(item.getTotal()));
        }

    }

}


