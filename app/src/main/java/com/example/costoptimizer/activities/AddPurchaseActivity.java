package com.example.costoptimizer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.costoptimizer.DatabaseHelper;
import com.example.costoptimizer.R;
import com.example.costoptimizer.models.PurchaseModel;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.costoptimizer.Constants.PURCHASE_KEY;
import static com.example.costoptimizer.Utils.getDateFromDatePicker;

public class AddPurchaseActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper dbHelper;
    EditText nameET, costET, countET, storeET;
    DatePicker dateDP;
    Button addBtn;
    PurchaseModel purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);
        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        initUI();
    }

    private void initUI() {
        nameET = findViewById(R.id.add_purchase_name);
        costET = findViewById(R.id.add_purchase_cost);
        countET = findViewById(R.id.add_purchase_count);
        storeET = findViewById(R.id.add_purchase_store);
        dateDP = findViewById(R.id.add_purchase_date);
        addBtn = findViewById(R.id.add_purchase_btn);
        addBtn.setOnClickListener(this);

        try {
            purchase = (PurchaseModel) getIntent().getExtras().get(PURCHASE_KEY);
            nameET.setText(purchase.name);
            storeET.setText(purchase.store);
            costET.setText(String.valueOf(purchase.cost));
            countET.setText(String.valueOf(purchase.count));
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(purchase.date);
            dateDP.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        } catch (Exception e) {
            purchase = new PurchaseModel();
        }
    }


    @Override
    public void onClick(View v) {
        String name = nameET.getText().toString();
        String store = storeET.getText().toString();
        int cost = Integer.parseInt(costET.getText().toString());
        int count = Integer.parseInt(countET.getText().toString());
        Date date = getDateFromDatePicker(dateDP);

        purchase.setData(name, cost, count, store, date);

        try {
            dbHelper.getPurchaseModelDao().createOrUpdate(purchase);
            Toast.makeText(this, getString(purchase.id != null ? R.string.purchase_edited : R.string.purchase_added), Toast.LENGTH_SHORT).show();
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
