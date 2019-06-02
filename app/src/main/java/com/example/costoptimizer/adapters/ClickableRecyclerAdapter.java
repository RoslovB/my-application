package com.example.costoptimizer.adapters;

import android.support.v7.widget.RecyclerView;

import com.example.costoptimizer.adapters.viewholders.ClickableViewHolder;
import com.example.costoptimizer.interfaces.ClickableHolderListener;
import com.example.costoptimizer.interfaces.OnAdapterItemClickListener;

import java.util.ArrayList;
import java.util.List;


public abstract class ClickableRecyclerAdapter<ItemType, VH extends ClickableViewHolder> extends RecyclerView.Adapter<VH> implements ClickableHolderListener {
    public List<ItemType> items = new ArrayList<>();
    public OnAdapterItemClickListener<ItemType> listener = new OnAdapterItemClickListener<ItemType>() {
        @Override
        public void onAdapterItemClick(ItemType item) {

        }

        @Override
        public Boolean onAdapterItemLongClick(ItemType item) {
            return null;
        }
    };

    @Override
    public void onItemClick(int position) {
        if (position != RecyclerView.NO_POSITION && items.size() > position) {
            listener.onAdapterItemClick(items.get(position));
        }
    }

    @Override
    public Boolean onItemLongClick(int position) {
        if (position != RecyclerView.NO_POSITION && items.size() > position) {
            return listener.onAdapterItemLongClick(items.get(position));
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
