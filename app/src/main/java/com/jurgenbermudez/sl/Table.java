package com.jurgenbermudez.sl;

//Class to use to save table data in a Database

public class Table {

    private int Id;
    private String Name;
    private double Total;


    public Table(){

    }

    public Table(int id, String name, double total) {
        if(Id>0)
            Id = id;

        Name = name;

        if(total>-1)
            Total = total;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        if(Id>0)
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
}
