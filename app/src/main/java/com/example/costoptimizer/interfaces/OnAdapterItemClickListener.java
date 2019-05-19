package com.example.costoptimizer.interfaces;

public interface OnAdapterItemClickListener<T> {
    void onAdapterItemClick(T item);

    Boolean onAdapterItemLongClick(T item);
}
