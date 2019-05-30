package com.example.costoptimizer.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.costoptimizer.CacheHelper;
import com.example.costoptimizer.DatabaseHelper;
import com.example.costoptimizer.R;
import com.example.costoptimizer.adapters.PurchasesAdapter;
import com.example.costoptimizer.interfaces.OnAdapterItemClickListener;
import com.example.costoptimizer.models.PurchaseModel;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.example.costoptimizer.Constants.PURCHASE_KEY;


public class MyPurchasesActivity extends AppCompatActivity implements OnAdapterItemClickListener<PurchaseModel> {
    RecyclerView recyclerView;
    PurchasesAdapter adapter;
    DatabaseHelper dbHelper;
    ViewGroup container;
    TextView warningMessage;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);
        getSupportActionBar().setTitle(R.string.my_purchases);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        initUI();
        try {
            inflateRecyclers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initUI() {
        container = findViewById(R.id.purchases_container);
        warningMessage = findViewById(R.id.limit_warning_message);
        try {
            int moneyLimit = CacheHelper.getMonthlyLimit(this);
            int moneySpendForMonth = dbHelper.getPurchaseModelDao().getMoneySpentForMonth();
            if (moneyLimit - moneySpendForMonth <= 0 && moneyLimit != 0) {
                warningMessage.setText(String.format("Вы превышаете лимит на %d сом", Math.abs(moneyLimit - moneySpendForMonth)));
                warningMessage.setVisibility(View.VISIBLE);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void inflateRecyclers() throws SQLException {
        container.removeAllViews();
        Date referenceDate = new Date();
        Calendar c = Calendar.getInstance();
        Calendar monthAgo = Calendar.getInstance();
        monthAgo.add(Calendar.MONTH, -12);
        c.setTime(referenceDate);
        ArrayList<PurchaseModel> purchases = (ArrayList<PurchaseModel>) dbHelper.getPurchaseModelDao().getAllPurchases();
        Date current = c.getTime();

        while (current.after(monthAgo.getTime())) {
            final Date finalCurrent = current;
            List<PurchaseModel> currentPurchases = purchases.stream()
                    .filter(purchase ->
                            purchase.date.getDate() == finalCurrent.getDate()
                                    && purchase.date.getMonth() == finalCurrent.getMonth())
                    .collect(Collectors.toList());
            if (!currentPurchases.isEmpty()) {
                View recyclerItem = getLayoutInflater().inflate(R.layout.purchase_recycler_horizontal, null);
                container.addView(recyclerItem);
                TextView dateText = recyclerItem.findViewById(R.id.purchases_date);
                dateText.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(current));
                recyclerView = recyclerItem.findViewById(R.id.purchases_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                adapter = new PurchasesAdapter();
                adapter.listener = this;
                adapter.items = currentPurchases;
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, -1);
            current = calendar.getTime();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        try {
            inflateRecyclers();
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
    public Boolean onAdapterItemLongClick(final PurchaseModel item) {
        CustomDialog.showDialog(this,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyPurchasesActivity.this, AddPurchaseActivity.class);
                        intent.putExtra(PURCHASE_KEY, item);
                        startActivity(intent);
                    }
                },
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        try {
                            dbHelper.getPurchaseModelDao().delete(item);
                            inflateRecyclers();
                            Toast.makeText(MyPurchasesActivity.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return true;
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
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
