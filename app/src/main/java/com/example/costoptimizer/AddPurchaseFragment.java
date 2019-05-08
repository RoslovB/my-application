package com.example.costoptimizer;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.IDN;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPurchaseFragment extends Fragment {

    private Button bnSave;
    EditText Id,Name,Quantity,Date,Store,Cost;

    public AddPurchaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_purchase, container, false);

        bnSave = view.findViewById(R.id.bn_save);
        Id  = view.findViewById(R.id.txt_purchase_id);
        Name = view.findViewById(R.id.txt_purchase_name);
        Quantity = view.findViewById(R.id.txt_quantity);
        Date = view.findViewById(R.id.txt_date);
        Store = view.findViewById(R.id.txt_store);
        Cost = view.findViewById(R.id.txt_cost);

        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = Id.getText().toString();
                String name = Name.getText().toString();
                String quantity = Quantity.getText().toString();
                String date = Date.getText().toString();
                String store = Store.getText().toString();
                String cost = Cost.getText().toString();

                ContactDbHelper contactDbHelper = new ContactDbHelper(getActivity());
                    SQLiteDatabase database = contactDbHelper.getWritableDatabase();
                contactDbHelper.addPurchase(Integer.parseInt(id),name,quantity,date,store,Integer.parseInt(cost),database);
                contactDbHelper.close();
                Id.setText("");
                Name.setText("");
                Quantity.setText("");
                Date.setText("");
                Store.setText("");
                Cost.setText("");
                Toast.makeText(getActivity(),"Ваша запись сохранена...", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

}

