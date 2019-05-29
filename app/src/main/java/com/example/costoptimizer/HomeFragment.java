package com.example.costoptimizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.costoptimizer.activities.MyPurchasesActivity;
import com.example.costoptimizer.activities.SettingsActivity;


public class HomeFragment extends Fragment implements View.OnClickListener {
    OnDbOpListener dbOpListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public interface OnDbOpListener {
        public void dbOpPerformed(int method);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        View btnMyPurchases = view.findViewById(R.id.btn_my_purchases);
        btnMyPurchases.setOnClickListener(this);

        View btnDiagram = view.findViewById(R.id.btn_see_diagram);
        btnDiagram.setOnClickListener(this);

        View bnGraph = view.findViewById(R.id.btn_see_graph);
        bnGraph.setOnClickListener(this);

        View btnAdvice = view.findViewById(R.id.btn_advice);
        btnAdvice.setOnClickListener(this);

        View btnSettings = view.findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_my_purchases:
                intent = new Intent(getContext(), MyPurchasesActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_see_diagram:
                dbOpListener.dbOpPerformed(4);
                break;
            case R.id.btn_see_graph:
                dbOpListener.dbOpPerformed(5);
                break;
            case R.id.btn_advice:
                dbOpListener.dbOpPerformed(6);
                break;
            case R.id.btn_settings:
                intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        try {
            dbOpListener = (OnDbOpListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement the interface method...");
        }

    }
}
