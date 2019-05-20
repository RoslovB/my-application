package com.example.costoptimizer.adapters.viewholders;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.costoptimizer.interfaces.ClickableHolderListener;


public abstract class ClickableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private ClickableHolderListener listener;

    public ClickableViewHolder(@NonNull View itemView, ClickableHolderListener listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onItemLongClick(getAdapterPosition());
        return false;
    }
}
