package com.example.costoptimizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnDbOpListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment).commit();
        }
    }

    @Override
    public void dbOpPerformed(int method) {

        switch (method) {
            case 0:
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChartFragment()).addToBackStack(null).commit();
                break;
            case 5:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GraphFragment()).addToBackStack(null).commit();
                break;
            case 6:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdviceFragment()).addToBackStack(null).commit();
                break;
        }

    }
}
