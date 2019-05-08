package com.example.costoptimizer;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.security.PrivateKey;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {

    private EditText Update_id,Update_name,Update_quantity,Update_date,Update_store,Update_cost;
    private Button Update_bn;
    public UpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        Update_id = view.findViewById(R.id.txt_update_id);
        Update_name = view.findViewById(R.id.txt_update_name);
        Update_quantity = view.findViewById(R.id.txt_update_quantity);
        Update_date = view.findViewById(R.id.txt_update_date);
        Update_store = view.findViewById(R.id.txt_update_store);
        Update_cost = view.findViewById(R.id.txt_update_cost);

        Update_bn = view.findViewById(R.id.bn_update_save);

        Update_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             updatePurchase();
            }
        });


        return view;
    }

    private void updatePurchase()
    {
        int id = Integer.parseInt(Update_id.getText().toString());
        String name = Update_name.getText().toString();
        int qantity = Integer.parseInt(Update_quantity.getText().toString());
        String date = Update_date.getText().toString();
        String store = Update_store.getText().toString();
        int cost = Integer.parseInt(Update_cost.getText().toString());

        ContactDbHelper contactDbHelper = new ContactDbHelper(getActivity());
        SQLiteDatabase database = contactDbHelper.getWritableDatabase();

        contactDbHelper.updateContact(id,name,qantity,date,store,cost,database);
        contactDbHelper.close();

        Toast.makeText(getActivity(),"Данные изменены...",Toast.LENGTH_SHORT).show();

        Update_id.setText("");
        Update_name.setText("");
        Update_quantity.setText("");
        Update_date.setText("");
        Update_store.setText("");
        Update_cost.setText("");
    }


}
