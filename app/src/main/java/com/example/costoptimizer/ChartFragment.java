package com.example.costoptimizer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.costoptimizer.models.PurchaseCategory;
import com.example.costoptimizer.models.PurchaseModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ChartFragment extends Fragment {
    PieChart pieChart;
    DatabaseHelper dbHelper;

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        pieChart = view.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        List<PurchaseModel> purchases = new ArrayList<>();
        dbHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        try {
            purchases = dbHelper.getPurchaseModelDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<PieEntry> yValues = new ArrayList<>();

        for (PurchaseCategory category : PurchaseCategory.values()) {
            int count = 0;
            for (PurchaseModel purchaseModel : purchases) {
                if (purchaseModel.category == category) {
                    count++;
                }
            }
            if (count > 0)
                yValues.add(new PieEntry(count, category.toString()));
        }
        pieChart.animateY(1500, Easing.EaseOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

        return view;
    }


}
