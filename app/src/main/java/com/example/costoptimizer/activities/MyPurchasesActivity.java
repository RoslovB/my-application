package com.example.costoptimizer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.costoptimizer.DatabaseHelper;
import com.example.costoptimizer.models.PurchaseModel;
import com.example.costoptimizer.R;
import com.example.costoptimizer.interfaces.OnAdapterItemClickListener;
import com.example.costoptimizer.adapters.PurchasesAdapter;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;



public class MyPurchasesActivity extends AppCompatActivity implements OnAdapterItemClickListener<PurchaseModel> {
    RecyclerView recyclerView;
    PurchasesAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);
        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        initUI();

    }

    private void initUI() {
        recyclerView = findViewById(R.id.purchases_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PurchasesAdapter();
        adapter.listener = this;
        try {
            adapter.items = dbHelper.getPurchaseModelDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onAdapterItemClick(PurchaseModel item) {
        Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Boolean onAdapterItemLongClick(PurchaseModel item) {
        return null;
    }
}
