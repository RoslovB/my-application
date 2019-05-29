package com.example.costoptimizer;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.costoptimizer.models.Advice;
import com.example.costoptimizer.models.PurchaseModel;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class AdviceFragment extends Fragment {
    DatabaseHelper dbHelper;
    TextView adviceTW, descriptionTW;
    List<PurchaseModel> purchases;
    List<Advice> advices = new ArrayList<>();

    public AdviceFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advice, container, false);
        dbHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        adviceTW = view.findViewById(R.id.advice_advice);
        descriptionTW = view.findViewById(R.id.advice_description);
        try {
            getAdvice();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAdvice() throws SQLException {
        purchases = dbHelper.getPurchaseModelDao().getPurchasesForMonth();
        if (!purchases.isEmpty()) {
            PurchaseModel maxCostPurchase = getMaxCostPurchase(purchases);

            advices.add(new Advice(String.format("Самой дорогой покупкой для вас является \"%1$s\"", maxCostPurchase.name), String.format("Самой дорогостоющей покупкой для вас является \"%s\",\n" +
                    "вы купили %d по %d сом. Готовы ли вы были потратить на это %d сом?", maxCostPurchase.name, maxCostPurchase.count, maxCostPurchase.cost, maxCostPurchase.getTotal())));

            PurchaseModel minImportancePurchase = getMinImportancePurchase(purchases);
            advices.add(new Advice(String.format("Стоит задуматься, нужно ли вам было покупать \"%1$s\"?", minImportancePurchase.name), String.format("Товар, с самой низкой важностью для вас является \"%s\",\n" +
                    "и его важность составляет %d, вы могли бы сэкономить %d сом", minImportancePurchase.name, minImportancePurchase.importance, minImportancePurchase.getTotal())));


            int dailyLimit = CacheHelper.getDailyLimit(getContext());
            int spentToday = dbHelper.getPurchaseModelDao().getMoneySpentToday();
            if (dailyLimit > 0 && spentToday > dailyLimit){
                advices.add(new Advice("Постарайтесь уменьшить дневные расходы, сегодня вы превысили свой лимит", String.format("Вы потратили сегодня %d сом, при установленном вами ежедневном лимите в %d сом, лимит превышен на %d сом", spentToday, dailyLimit, Math.abs(spentToday - dailyLimit))));
            }

            setRandomAdvice();
        } else {
            adviceTW.setText(getContext().getString(R.string.you_have_not_purchases));
        }
    }

    private PurchaseModel getMinImportancePurchase(List<PurchaseModel> purchases) {
        PurchaseModel minimalImportancePurchase = purchases.get(0);
        for (PurchaseModel purchase : purchases) {
            if (purchase.importance < minimalImportancePurchase.importance)
                minimalImportancePurchase = purchase;
            else if (purchase.importance == minimalImportancePurchase.importance) {
                if ((purchase.getTotal()) > (minimalImportancePurchase.cost * minimalImportancePurchase.count))
                    minimalImportancePurchase = purchase;
            }
        }
        return minimalImportancePurchase;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private PurchaseModel getMaxCostPurchase(List<PurchaseModel> purchases) {
        Collections.sort(purchases, Comparator.comparing(PurchaseModel::getTotal));
        PurchaseModel maxCostPurchase = purchases.get(purchases.size() - 1);
        return maxCostPurchase;
    }

    private void setRandomAdvice() {
        Random rand = new Random();
        Advice randomAdvice = advices.get(rand.nextInt(advices.size()));
        adviceTW.setText(randomAdvice.advice);
        descriptionTW.setText(randomAdvice.description);
    }


}
