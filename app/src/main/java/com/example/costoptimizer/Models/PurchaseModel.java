package com.example.costoptimizer.Models;

public class PurchaseModel {
    public String id;
    public String name;
    public String quantity;
    public String date;
    public String store;
    public String cost;

    public PurchaseModel(String id, String name, String quantity, String date, String store, String cost) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.store = store;
        this.cost = cost;
    }
}
