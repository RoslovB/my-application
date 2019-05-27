package com.example.costoptimizer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.costoptimizer.models.PurchaseModel;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;


public class AdviceFragment extends Fragment {
    DatabaseHelper dbHelper;
    TextView adviceTW, descriptionTW;

    public AdviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advice, container, false);
        dbHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        adviceTW = view.findViewById(R.id.advice_advice);
        descriptionTW = view.findViewById(R.id.advice_description);
        getAdvice();
        // Inflate the layout for this fragment
        return view;
    }

    private void getAdvice() {
        List<PurchaseModel> purchases = dbHelper.getPurchaseModelRuntimeExceptionDao().queryForAll();
        PurchaseModel minImportancePurchase = getMinImportancePurchase(purchases);
        String advice = String.format("Стоит задуматься, нужно ли вам было покупать \"%1$s\"?", minImportancePurchase.name);
        String adviceDescription = String.format("Товар, с самой низкой важностью для вас является \"%s\",\n" +
                "и его важность составляет %d, вы могли бы сэкономить %d сом", minImportancePurchase.name, minImportancePurchase.importance, (minImportancePurchase.cost * minImportancePurchase.count));
        adviceTW.setText(advice);
        descriptionTW.setText(adviceDescription);
    }

    private PurchaseModel getMinImportancePurchase(List<PurchaseModel> purchases) {
        PurchaseModel minimalImportancePurchase = purchases.get(0);
        for (PurchaseModel purchase : purchases) {
            if (purchase.importance < minimalImportancePurchase.importance)
                minimalImportancePurchase = purchase;
            else if (purchase.importance == minimalImportancePurchase.importance) {
                if ((purchase.cost * purchase.count) > (minimalImportancePurchase.cost * minimalImportancePurchase.count))
                    minimalImportancePurchase = purchase;
            }
        }
        return minimalImportancePurchase;
    }
}
