package com.example.costoptimizer.dialogs;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.costoptimizer.PurchaseModelDAO;
import com.example.costoptimizer.R;
import com.example.costoptimizer.models.PurchaseCategory;
import com.example.costoptimizer.models.PurchaseModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class PurchasesModal {


    public static void showDialog(FragmentActivity context, List<PurchaseModel> purchases) {
        {

            // Create custom dialog object
            final Dialog dialog = new Dialog(context);
            // Include dialog.xml file
            dialog.setContentView(R.layout.purchases_modal);
            ViewGroup container = dialog.findViewById(R.id.purchases_modal_container);
            TextView modalDate = dialog.findViewById(R.id.modal_date);
            modalDate.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(purchases.get(0).date)  );

            for (PurchaseCategory category : PurchaseCategory.values()) {

                List<PurchaseModel> currentPurchases = PurchaseModelDAO.getPurchasesByCategory(purchases, category);
                if (currentPurchases.size() != 0) {

                    View categoryInfoItem = context.getLayoutInflater().inflate(R.layout.graph_category_item, null);
                    ImageView categoryIcon = categoryInfoItem.findViewById(R.id.graph_category_icon);
                    categoryIcon.setImageDrawable(context.getResources().getDrawable(category.getImage()));

                    TextView categoryPurchaseCount = categoryInfoItem.findViewById(R.id.graph_category_count);
                    categoryPurchaseCount.setText(String.format("покупок: %s", String.valueOf(currentPurchases.size())));

                    TextView categoryPurchaseTotal = categoryInfoItem.findViewById(R.id.graph_category_total);
                    int total = 0;
                    for (PurchaseModel purchase: currentPurchases){
                        total += purchase.getTotal();
                    }
                    categoryPurchaseTotal.setText(String.format("%s сом", String.valueOf(total)));
                    container.addView(categoryInfoItem);
                }

            }


            dialog.show();
        }
    }
}