package com.jurgenbermudez.sl;

//Class to use to save table data in a Database

import java.util.Date;

public class Table {

    private int Id;
    private String Name;
    private double Total;
    private String Date_List;


    public Table(){

    }

    public Table(int id, String name, double total, String date_list) {
        if(id>0)
            Id = id;

        Name = name;

        if(total>-1)
            Total = total;

        Date_List = date_list;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        if(id>0)
            Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        if(total>-1)
            Total = total;
    }

    public String getDate_List() {
        return Date_List;
    }

    public void setDate_List(String date_list) {
        Date_List = date_list;
    }
}
