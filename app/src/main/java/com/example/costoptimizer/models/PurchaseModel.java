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
    public PurchaseCategory category;

    @DatabaseField
    public int cost;

    @DatabaseField
    public int count;

    @DatabaseField
    public Date date;

    @DatabaseField
    public int importance;


    public PurchaseModel() {

    }

    public PurchaseModel(String name, PurchaseCategory category, int cost, int count, Date date, int importance) {
        this.name = name;
        this.category = category;
        this.cost = cost;
        this.count = count;
        this.date = date;
        this.importance = importance;
    }

    public void setData(String name, PurchaseCategory category, int cost, int count, Date date, int importance) {
        this.name = name;
        this.category = category;
        this.cost = cost;
        this.count = count;
        this.date = date;
        this.importance = importance;
    }

    public int getTotal() {
        return this.cost * this.count;
    }
}
