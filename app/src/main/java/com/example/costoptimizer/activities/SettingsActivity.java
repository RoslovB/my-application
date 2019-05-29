package com.example.costoptimizer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.costoptimizer.CacheHelper;
import com.example.costoptimizer.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText incomeET;
    int income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        incomeET = findViewById(R.id.your_income);
        income = CacheHelper.getMoneyLimit(this);
        incomeET.setHint(String.valueOf(income));
        findViewById(R.id.btn_save_settings).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String incomeTxt = incomeET.getText().toString();
        CacheHelper.saveMoneyLimit(this, incomeTxt);
        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
