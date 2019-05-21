package com.example.costoptimizer.models;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.Date;


public class PurchaseModel implements Serializable {

    @DatabaseField(generatedId = true)
    public Integer id;

    @DatabaseField
    public String name;

    @DatabaseField
    public int count;

    @DatabaseField
    public Date date;

    @DatabaseField
    public String store;

    @DatabaseField
    public int cost;

    public PurchaseModel() {

    }

    public PurchaseModel(String name, int cost, int count, String store, Date date) {
        this.name = name;
        this.count = count;
        this.date = date;
        this.store = store;
        this.cost = cost;
    }
}
