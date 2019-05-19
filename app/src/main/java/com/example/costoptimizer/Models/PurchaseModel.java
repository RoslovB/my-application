package com.example.costoptimizer.Models;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;


public class PurchaseModel {

    @DatabaseField(generatedId = true)
    public Integer id;

    @DatabaseField
    public String name;

    @DatabaseField
    public String count;

    @DatabaseField
    public Date date;

    @DatabaseField
    public String store;

    @DatabaseField
    public String cost;

    public PurchaseModel(){

    }

    public PurchaseModel(String name, String count, Date date, String store, String cost) {
        super();
        this.name = name;
        this.count = count;
        this.date = date;
        this.store = store;
        this.cost = cost;
    }
}
