package com.example.costoptimizer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.costoptimizer.CacheHelper;
import com.example.costoptimizer.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText monthlyLimitET, dailyLimitET;
    int monthlyLimit, dailyLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        monthlyLimitET = findViewById(R.id.monthly_limit);
        monthlyLimit = CacheHelper.getMonthlyLimit(this);
        monthlyLimitET.setText(String.valueOf(monthlyLimit));
        dailyLimitET = findViewById(R.id.daily_limit);
        dailyLimit = CacheHelper.getDailyLimit(this);
        dailyLimitET.setText(String.valueOf(dailyLimit));
        findViewById(R.id.btn_save_settings).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String incomeTxt = monthlyLimitET.getText().toString();
        CacheHelper.saveMonthlyLimit(this, incomeTxt);
        String dailyLimitTxt = dailyLimitET.getText().toString();
        CacheHelper.saveDailyLimit(this, dailyLimitTxt);
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
