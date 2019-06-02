package com.example.costoptimizer.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.costoptimizer.R;
import com.example.costoptimizer.adapters.viewholders.PurchaseVH;
import com.example.costoptimizer.models.PurchaseModel;


public class PurchasesAdapter extends ClickableRecyclerAdapter<PurchaseModel, PurchaseVH> {

    @NonNull
    @Override
    public PurchaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase, parent, false);
        return new PurchaseVH(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseVH holder, int position) {
        holder.bind(items.get(position));
    }
}
