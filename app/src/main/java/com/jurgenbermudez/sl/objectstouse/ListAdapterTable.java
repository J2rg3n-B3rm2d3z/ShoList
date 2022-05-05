package com.jurgenbermudez.sl.objectstouse;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.jurgenbermudez.sl.ItemsViewActivity;
import com.jurgenbermudez.sl.R;
import com.jurgenbermudez.sl.TableActivity;
import com.jurgenbermudez.sl.db.DbItems;
import com.jurgenbermudez.sl.db.DbTable;

import java.util.ArrayList;

//Class to use to adapt the listview to a recyclerview

public class ListAdapterTable extends RecyclerView.Adapter<ListAdapterTable.ViewHolder> {

    private ArrayList<Table> TableList;
    private final LayoutInflater Inflater;
    private final Context Context;

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
        return new ViewHolder(view).LinkAdapter(this);
    }

    //Select a item in the list

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ListAdapterTable.ViewHolder holder, final int position) {
        holder.binData(TableList.get(position));
    }

    //Set List

    public void setTableList(ArrayList<Table> tableList) {
        TableList = tableList;
    }


    //Class to use all function under one specific item on the list

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,total_value,date_list;
        Button btnEdit,btnDelete;
        ListAdapterTable listAdapterTable;


        //Constructor

        public ViewHolder(@NonNull View item_view){

            super(item_view);
            imageView = item_view.findViewById(R.id.iconImageView);
            name = item_view.findViewById(R.id.List_Name);
            total_value = item_view.findViewById(R.id.Total_Value_List);
            date_list = item_view.findViewById(R.id.List_Date);
            btnEdit = item_view.findViewById(R.id.btn_Edit);
            btnDelete = item_view.findViewById(R.id.btn_Delete);

            //Events under item that was selected

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Edit a item

                    Intent ToTableActivity = new Intent(item_view.getContext(), TableActivity.class);
                    ToTableActivity.putExtra("title",item_view.getContext().getString(R.string.Edit_list_tittle));
                    ToTableActivity.putExtra("id",listAdapterTable.TableList.get(
                            getAdapterPosition()).getId());
                    Context.startActivity(ToTableActivity);

                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Emergent windows

                    AlertDialog.Builder builder = new AlertDialog.Builder(Context);
                    builder.setMessage(v.getContext().getString(R.string.UserMessage7))
                            .setPositiveButton(v.getContext().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //Delete a item

                                    DbTable dbTable = new DbTable(Context);
                                    DbItems dbItems = new DbItems(Context);
                                    if(dbItems.DeleteItems(TableList.get(getAdapterPosition()).getId())
                                            && dbTable.DeleteList(TableList.get(getAdapterPosition()).getId())) {

                                        listAdapterTable.TableList.remove(getAdapterPosition());
                                        listAdapterTable.notifyItemRemoved(getAdapterPosition());
                                        Toast.makeText(Context, v.getContext().getString(R.string.UserMessage8),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(Context, v.getContext().getString(R.string.Message10),
                                                Toast.LENGTH_SHORT).show();
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

            //Select the items

            item_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Make a new Activity where do the list
                    Animation animItems = AnimationUtils.loadAnimation(v.getContext(),R.anim.item_animation);
                    v.startAnimation(animItems);

                    animItems.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            Intent ToItemViewActivity = new Intent(item_view.getContext(),
                                    ItemsViewActivity.class);
                            ToItemViewActivity.putExtra("id_table",
                                    listAdapterTable.TableList.get(getAdapterPosition()).getId());
                            ToItemViewActivity.putExtra("title",
                                    listAdapterTable.TableList.get(getAdapterPosition()).getName());
                            Context.startActivity(ToItemViewActivity);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
        }

        //Method to link the the Adapter with this class

        public ViewHolder LinkAdapter (ListAdapterTable lstAdapterTable){

            this.listAdapterTable = lstAdapterTable;
            return this;

        }

        //Set data

        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint("SetTextI18n")
        void binData(final Table item){

            DecimalFormat decimalFormat = new DecimalFormat("0.00");

            imageView.setColorFilter(Color.parseColor("#55AD37"),PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            total_value.setText("Total: " + decimalFormat.format(item.getTotal()));
            date_list.setText(item.getDate_List());
        }

    }

}


