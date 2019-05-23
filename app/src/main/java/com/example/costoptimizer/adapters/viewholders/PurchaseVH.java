package com.example.costoptimizer.adapters.viewholders;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.costoptimizer.interfaces.ClickableHolderListener;
import com.example.costoptimizer.models.PurchaseModel;
import com.example.costoptimizer.R;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class PurchaseVH extends ClickableViewHolder {
    private TextView nameTextView, countTextView, costTextView, storeTW, dateTW;

    public PurchaseVH(@NonNull View itemView, ClickableHolderListener listener) {
        super(itemView, listener);
        nameTextView = itemView.findViewById(R.id.purchase_name);
        countTextView = itemView.findViewById(R.id.purchase_count);
        costTextView = itemView.findViewById(R.id.purchase_cost);
        storeTW = itemView.findViewById(R.id.purchase_store);
        dateTW = itemView.findViewById(R.id.purchase_date);
    }

    @SuppressLint("SimpleDateFormat")
    public void bind(PurchaseModel purchase) {
        nameTextView.setText(purchase.name);
        storeTW.setText(purchase.store);
        countTextView.setText(String.valueOf(purchase.count));
        costTextView.setText(String.valueOf(purchase.cost));
        dateTW.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(purchase.date));
    }
}
