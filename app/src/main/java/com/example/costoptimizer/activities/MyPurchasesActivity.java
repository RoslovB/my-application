package com.example.costoptimizer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    protected void onResume() {
        try {
            adapter.items = dbHelper.getPurchaseModelDao().queryForAll();
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onAdapterItemClick(PurchaseModel item) {
        Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Boolean onAdapterItemLongClick(PurchaseModel item) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.purchase_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.add_purchase_option) {
            Intent intent = new Intent(this, AddPurchaseActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
