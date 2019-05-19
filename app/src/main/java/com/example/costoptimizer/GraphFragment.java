package com.example.costoptimizer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class GraphFragment extends Fragment {

    private static final String TAG = "MainActivity";

    private LineChart mChart;

    public GraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        mChart = (LineChart) view.findViewById(R.id.linechart);

       /* mChart.setOnChartGestureListener(GraphFragment.this);
        mChart.setOnChartValueSelectedListener(GraphFragment.this);*/

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValue = new ArrayList<>();

        yValue.add(new Entry(0, 60f));
        yValue.add(new Entry(1, 50f));
        yValue.add(new Entry(2, 70f));
        yValue.add(new Entry(3, 30f));
        yValue.add(new Entry(4, 50f));
        yValue.add(new Entry(5, 60f));
        yValue.add(new Entry(6, 65f));

        LineDataSet set1 = new LineDataSet(yValue, "Data Set 1");

        set1.setFillAlpha(110);

        set1.setColor(Color.RED);
        set1.setLineWidth(3f);
        set1.setValueTextSize(9f);
        set1.setValueTextColor(Color.BLUE);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);

        return view;

    }

}
