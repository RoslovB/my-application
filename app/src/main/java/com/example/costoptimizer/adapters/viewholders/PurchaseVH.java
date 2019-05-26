package com.example.costoptimizer.adapters.viewholders;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.costoptimizer.R;
import com.example.costoptimizer.interfaces.ClickableHolderListener;
import com.example.costoptimizer.models.PurchaseModel;


public class PurchaseVH extends ClickableViewHolder {
    private TextView nameTextView, costTextView;
    private ImageView iconIW;

    public PurchaseVH(@NonNull View itemView, ClickableHolderListener listener) {
        super(itemView, listener);
        nameTextView = itemView.findViewById(R.id.purchase_name);
        costTextView = itemView.findViewById(R.id.purchase_cost);
        iconIW = itemView.findViewById(R.id.purchase_icon);
    }

    @SuppressLint("SimpleDateFormat")
    public void bind(PurchaseModel purchase) {
        nameTextView.setText(purchase.name);
        costTextView.setText("-" + String.valueOf(purchase.cost * purchase.count));
        iconIW.setImageDrawable(this.itemView.getContext().getResources().getDrawable(purchase.category.getImage()));
    }
}
