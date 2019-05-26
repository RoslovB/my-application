package com.example.costoptimizer;


import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.costoptimizer.models.PurchaseModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GraphFragment extends Fragment {

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
        dbHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);

        mChart.setData(getLineData());
        return view;

    }

    private LineData getLineData() {
        Date referenceDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(referenceDate);
        c.add(Calendar.MONTH, -1);
        List<PurchaseModel> purchases = dbHelper.getPurchaseModelRuntimeExceptionDao().queryForAll();

        ArrayList<Entry> yValue = new ArrayList<>();
        Date current = c.getTime();
        while (current.before(referenceDate)) {
            int yGraph = 0;
            for (PurchaseModel purchase : purchases) {
                if (purchase.date.getDate() == current.getDate())
                    yGraph += purchase.cost * purchase.count;
            }
            yValue.add(new Entry(current.getDate(), yGraph));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 1);
            current = calendar.getTime();
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

        return new LineData(dataSets);
    }
}
