package com.example.costoptimizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnDbOpListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_container)!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }

            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,homeFragment).commit();
        }
    }

    @Override
    public void dbOpPerformed(int method) {

        switch (method)
        {
            case 0:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddPurchaseFragment()).addToBackStack(null).commit();
                break;

            case 1:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ReadPurchaseFragment()).addToBackStack(null).commit();
                break;

            case 2:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UpdateFragment()).addToBackStack(null).commit();
                break;

            case 3:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DeletePurchaseFragment()).addToBackStack(null).commit();
                break;

            case 4:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChartFragment()).addToBackStack(null).commit();
                break;

            case 5:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GraphFragment()).addToBackStack(null).commit();
                break;

            case 6:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AdviceFragment()).addToBackStack(null).commit();
                break;
        }

    }
}
