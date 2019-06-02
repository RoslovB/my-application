package com.example.costoptimizer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.costoptimizer.DatabaseHelper;
import com.example.costoptimizer.R;
import com.example.costoptimizer.models.PurchaseCategory;
import com.example.costoptimizer.models.PurchaseModel;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.example.costoptimizer.Constants.PURCHASE_KEY;
import static com.example.costoptimizer.Utils.getDateFromDatePicker;

public class AddPurchaseActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener, TextWatcher {
    DatabaseHelper dbHelper;
    PurchaseModel purchase;
    EditText nameET, costET, countET;
    DatePicker dateDP;
    Button addBtn;
    TextView importanceValue;
    SeekBar importanceSB;
    Spinner categoryS;
    boolean isEditing, isButtonActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);
        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        initUI();
    }

    private void initUI() {
        nameET = findViewById(R.id.add_purchase_name);
        nameET.addTextChangedListener(this);
        costET = findViewById(R.id.add_purchase_cost);
        costET.addTextChangedListener(this);
        countET = findViewById(R.id.add_purchase_count);
        countET.addTextChangedListener(this);
        dateDP = findViewById(R.id.add_purchase_date);
        addBtn = findViewById(R.id.add_purchase_btn);
        categoryS = findViewById(R.id.add_purchase_category);
        importanceSB = findViewById(R.id.add_purchase_importance);
        importanceSB.setOnSeekBarChangeListener(this);
        importanceValue = findViewById(R.id.add_purchase_importance_value);
        addBtn.setOnClickListener(this);

        ArrayAdapter<PurchaseCategory> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, PurchaseCategory.values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryS.setAdapter(dataAdapter);
        categoryS.setOnItemSelectedListener(this);

        try {
            purchase = (PurchaseModel) getIntent().getExtras().get(PURCHASE_KEY);
            nameET.setText(purchase.name);
            costET.setText(String.valueOf(purchase.cost));
            countET.setText(String.valueOf(purchase.count));
            importanceSB.setProgress(purchase.importance);
            ArrayAdapter myAdap = (ArrayAdapter) categoryS.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = myAdap.getPosition(purchase.category);

            categoryS.setSelection(spinnerPosition);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(purchase.date);
            dateDP.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
            isEditing = true;
            importanceSB.setProgress(purchase.importance);
        } catch (Exception e) {
            purchase = new PurchaseModel();
            isEditing = false;
        }
    }


    @Override
    public void onClick(View v) {
        purchase.name = nameET.getText().toString();
        purchase.cost = Integer.parseInt(costET.getText().toString());
        purchase.count = Integer.parseInt(countET.getText().toString());
        purchase.date = getDateFromDatePicker(dateDP);


        try {
            dbHelper.getPurchaseModelDao().createOrUpdate(purchase);
            Toast.makeText(this, getString(isEditing ? R.string.purchase_edited : R.string.purchase_added), Toast.LENGTH_SHORT).show();
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        purchase.importance = progress;
        importanceValue.setText(String.valueOf(progress));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        purchase.category = PurchaseCategory.fromString(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        addBtn.setEnabled(isFormValid());
    }

    private Boolean isFormValid() {
        return !nameET.getText().toString().isEmpty()
                && !costET.getText().toString().isEmpty()
                && !countET.getText().toString().isEmpty();
    }
}
