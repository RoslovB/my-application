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


/**
 * A simple {@link Fragment} subclass.
 */
public class DeletePurchaseFragment extends Fragment {
    private EditText Id;
    private Button Button_Delete;

    public DeletePurchaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_purchase, container, false);

        Id = view.findViewById(R.id.txt_delete_id);
        Button_Delete = view.findViewById(R.id.delete_purchase_bn);

        Button_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             deletePurchase();
            }
        });

        return view;
    }

    private void deletePurchase()
    {
        ContactDbHelper contactDbHelper = new ContactDbHelper(getActivity());
        SQLiteDatabase database = contactDbHelper.getWritableDatabase();

        int id = Integer.parseInt(Id.getText().toString());

        contactDbHelper.deleteContact(id,database);
        contactDbHelper.close();
        Id.setText("");
        Toast.makeText(getActivity(),"Покупка удалена...",Toast.LENGTH_SHORT).show();
    }

}
