package com.example.costoptimizer.models;

import com.example.costoptimizer.R;

public enum PurchaseCategory {
    FOOD("Еда", R.drawable.ic_pizza),
    FUN("Развлечения", R.drawable.ic_popcorn),
    CLOTHES("Одежда", R.drawable.ic_tie),
    SPORT("Спорт", R.drawable.ic_exercise),
    COSMETICS("Косметика", R.drawable.ic_cosmetics),
    UTILITIES("Коммунальные услуги", R.drawable.ic_house2),
    INTERNET("Интернет", R.drawable.ic_wifi),
    TRANSPORT("Транспорт", R.drawable.ic_transport),
    OTHER("Другие", R.drawable.ic_hands_and_gestures);

    private String name;
    private int image;

    PurchaseCategory(String name, int image) {
        this.name = name; this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getImage() {
        return image;
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