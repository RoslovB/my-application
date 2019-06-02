package com.example.costoptimizer;


import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.costoptimizer.dialogs.PurchasesModal;
import com.example.costoptimizer.models.PurchaseModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GraphFragment extends Fragment implements OnChartValueSelectedListener {

    private static final String TAG = "MainActivity";

    private LineChart mChart;
    private DatabaseHelper dbHelper;

    public GraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        mChart = view.findViewById(R.id.linechart);
        mChart.setPinchZoom(true);
        mChart.setTouchEnabled(true);
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setOnChartValueSelectedListener(this);
        dbHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);

        try {
            mChart.setData(getLineData());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }

    private LineData getLineData() throws SQLException {
        List<PurchaseModel> purchases = dbHelper.getPurchaseModelDao().getPurchasesForMonth();
        Date referenceDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(referenceDate);
        c.add(Calendar.MONTH, -1);
        ArrayList<Entry> yValue = new ArrayList<>();
        Date current = c.getTime();
        c.setTime(referenceDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        referenceDate = c.getTime();
        int x = 1;
        while (current.before(referenceDate)) {
            int yGraph = 0;
            List<PurchaseModel> entryPurchasesList = new ArrayList<>();
            for (PurchaseModel purchase : purchases) {
                if (purchase.date.getDate() == current.getDate() &&
                        purchase.date.getMonth() == current.getMonth()
                ) {
                    entryPurchasesList.add(purchase);
                    yGraph += purchase.cost * purchase.count;
                }
            }
            Entry entry = new Entry(x, yGraph);
            entry.setData(entryPurchasesList);
            yValue.add(entry);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 1);
            current = calendar.getTime();
            x++;
        }


        LineDataSet set1 = new LineDataSet(yValue, "Расходы за 30 дней");
        set1.setDrawIcons(false);
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.DKGRAY);
        set1.setCircleColor(Color.DKGRAY);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(getLimitData(set1.getXMax()));
        return new LineData(dataSets);
    }

    private LineDataSet getLimitData(float maxX) {

        int dailyLimit = CacheHelper.getDailyLimit(getContext());

        ArrayList<Entry> yValue = new ArrayList<>();
        if (dailyLimit > 0) {
            yValue.add(new Entry(1, dailyLimit));
            yValue.add(new Entry(maxX, dailyLimit));
        }

        LineDataSet set1 = new LineDataSet(yValue, "Дневной лимит");
        set1.setDrawIcons(false);
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormSize(15.f);
        return set1;
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        List<PurchaseModel> purchasesForEntry = (List<PurchaseModel>) e.getData();
        if (purchasesForEntry != null)
            if (purchasesForEntry.size() > 0)
                PurchasesModal.showDialog(getActivity(), purchasesForEntry);
    }

    @Override
    public void onNothingSelected() {

    }
}
