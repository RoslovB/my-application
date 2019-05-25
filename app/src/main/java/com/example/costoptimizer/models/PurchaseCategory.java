package com.example.costoptimizer.models;

public enum PurchaseCategory {
    FOOD("Еда"),
    FUN("Развлечения"),
    CLOTHES("Одежда"),
    SPORT("Спорт"),
    COSMETICS("Косметика"),
    UTILITIES("Коммунальные услуги"),
    INTERNET("Интернет"),
    TRANSPORT("Транспорт"),
    OTHER("Другие");

    private String name;

    PurchaseCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static PurchaseCategory fromString(String text) {
        for (PurchaseCategory b : PurchaseCategory.values()) {
            if (b.name.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}