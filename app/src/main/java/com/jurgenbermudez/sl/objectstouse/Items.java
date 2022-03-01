package com.jurgenbermudez.sl.objectstouse;


//Class to use to save to items data in a DataBase

public class Items {

    private int Id;
    private int Table_Id;
    private String Name;
    private double Price;
    private int Quantity_Item;

    //Constructors

    public Items(){

    }

    public Items(int id, int table_Id, String name, double price, int quantity_Item) {

            Id = id;

        if(table_Id>0)
            Table_Id = table_Id;

        Name = name;

        if(price>0)
            Price = price;

        if(quantity_Item>-1)
            Quantity_Item = quantity_Item;
    }

    //Getter n setter

    public void setId(int id) {
        if(id>0)
            Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setTable_Id(int table_Id) {
        if(table_Id>0)
            Table_Id = table_Id;
    }

    public int getTable_Id() {
        return Table_Id;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setPrice(double price) {
        if(price>0)
            Price = price;
    }

    public double getPrice() {
        return Price;
    }

    public void setQuantity_Item(int quantity_Item) {
        if(quantity_Item>-1)
            Quantity_Item = quantity_Item;
    }

    public int getQuantity_Item() {
        return Quantity_Item;
    }

     public double Total_Value(){
        return getPrice() * getQuantity_Item();
     }
}
