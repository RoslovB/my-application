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
import java.util.Date;

import static com.example.costoptimizer.Utils.getDateFromDatePicker;

public class AddPurchaseActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper dbHelper;
    EditText nameET, costET, countET, storeET;
    DatePicker dateDP;
    Button addBtn;

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
    }


    @Override
    public void onClick(View v) {
        String name = nameET.getText().toString();
        String store = storeET.getText().toString();
        int cost = Integer.parseInt(costET.getText().toString());
        int count = Integer.parseInt(countET.getText().toString());
        Date date = getDateFromDatePicker(dateDP);

        try {
            dbHelper.getPurchaseModelDao().create(new PurchaseModel(name, cost, count, store, date));
            Toast.makeText(this, getString(R.string.purchase_added), Toast.LENGTH_SHORT).show();
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
