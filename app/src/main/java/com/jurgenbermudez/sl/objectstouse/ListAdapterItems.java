package com.jurgenbermudez.sl.objectstouse;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.jurgenbermudez.sl.ItemsActivity;
import com.jurgenbermudez.sl.ItemsViewActivity;
import com.jurgenbermudez.sl.R;
import com.jurgenbermudez.sl.db.DbItems;
import com.jurgenbermudez.sl.db.DbTable;

import java.util.ArrayList;
import java.util.Date;

public class ListAdapterItems extends RecyclerView.Adapter<ListAdapterItems.ViewHolder>   {


    private ArrayList<Items> ItemsList;
    private final LayoutInflater Inflater;
    private final Context Context;
    private final int IdTable;

    //Constructor

    public ListAdapterItems(ArrayList<Items> itemsList, Context context, int idTable) {
        Inflater = LayoutInflater.from(context) ;
        ItemsList = itemsList;
        Context = context;
        IdTable = idTable;
    }

    //Get total item in the list

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    //Create the view

    @NonNull
    @Override
    public ListAdapterItems.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = Inflater.inflate(R.layout.list_item,null);
        return new ListAdapterItems.ViewHolder(view).LinkAdapter(this);
    }

    //Select a item in the list

    @Override
    public void onBindViewHolder(@NonNull ListAdapterItems.ViewHolder holder, final int position) {
        holder.binData(ItemsList.get(position));
    }

    //Set List

    public void setItemsList(ArrayList<Items> itemsList) {

        ItemsList = itemsList;
    }


    //Class to use all function under one specific item on the list

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, count_value, price_value,total_value;
        Button btnEditItem, btnDeleteItem;
        ListAdapterItems listAdapterItems;

        //Constructor

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.Image_Icon_view_Item);
            name = itemView.findViewById(R.id.Text_view_item);
            count_value = itemView.findViewById(R.id.Count_value);
            price_value = itemView.findViewById(R.id.Price_value);
            total_value = itemView.findViewById(R.id.Total_value);
            btnEditItem = itemView.findViewById(R.id.btn_Edit_Item);
            btnDeleteItem = itemView.findViewById(R.id.btn_Delete_Item);

            //Events under item that was selected

            btnEditItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Edit a item

                    Intent ToItemActivity = new Intent(v.getContext(), ItemsActivity.class);
                    ToItemActivity.putExtra("title","Edit Item");
                    ToItemActivity.putExtra("id_table",IdTable);
                    ToItemActivity.putExtra("id",listAdapterItems.ItemsList.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(ToItemActivity);

                }
            });

            btnDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Emergent windows
                    AlertDialog.Builder builder = new AlertDialog.Builder(Context);
                    builder.setMessage("Delete this Item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //Delete a item

                                    DbItems dbItems = new DbItems(Context);
                                    if(dbItems.DeleteItem(ItemsList.get(getAdapterPosition()).getId())) {

                                        listAdapterItems.ItemsList.remove(getAdapterPosition());
                                        listAdapterItems.notifyItemRemoved(getAdapterPosition());
                                        Toast.makeText(Context, "Item was delete.", Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                        Toast.makeText(Context, "Error to delete, Please contact with developer", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Should not do nothing

                                }
                            }).show();

                }
            });

        }

        //Method to link the the Adapter with this class

        public ListAdapterItems.ViewHolder LinkAdapter (ListAdapterItems lstAdapterItems){

            this.listAdapterItems = lstAdapterItems;
            return this;

        }

        //Set data

        @SuppressLint("SetTextI18n")
        void binData(final Items item){

            imageView.setColorFilter(Color.parseColor("#55AD37"), PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            price_value.setText(String.valueOf(item.getPrice()));
            count_value.setText(String.valueOf(item.getQuantity_Item()));
            total_value.setText(String.valueOf(item.Total_Value()));

        }


    }
}
