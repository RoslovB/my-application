package com.example.costoptimizer.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.example.costoptimizer.interfaces.ClickableHolderListener;
import com.example.costoptimizer.models.PurchaseModel;
import com.example.costoptimizer.R;

import androidx.annotation.NonNull;

public class PurchaseVH extends ClickableViewHolder {
    private TextView nameTextView, countTextView;

    public PurchaseVH(@NonNull View itemView, ClickableHolderListener listener) {
        super(itemView, listener);
        nameTextView = itemView.findViewById(R.id.purchase_name);
        countTextView = itemView.findViewById(R.id.purchase_count);
    }

    public void bind(PurchaseModel purchase){
        nameTextView.setText(purchase.name);
        countTextView.setText(purchase.count);
    }
}
