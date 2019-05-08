package com.example.costoptimizer;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 */
public class ReadPurchaseFragment extends Fragment {

    private TextView Txt_Display;
    public ReadPurchaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read_purchase, container, false);
        Txt_Display = view.findViewById(R.id.txt_display);
        readContacts();
        return view;
    }

    private void readContacts()
    {
        ContactDbHelper contactDbHelper = new ContactDbHelper(getActivity());
        SQLiteDatabase database = contactDbHelper.getReadableDatabase();

        Cursor cursor = contactDbHelper.readPurchase(database);
        String info = "";
        while (cursor.moveToNext())
        {
            String id = Integer.toString(cursor.getInt(cursor.getColumnIndex(ContactContract.ContactEntry.PRODUCT_ID)));
            String name = cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.NAME));
            String quantity  = Integer.toString(cursor.getInt(cursor.getColumnIndex(ContactContract.ContactEntry.QUANTITY)));
            String date = cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.DATE));
            String store = cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.STORE));
            String cost  = Integer.toString(cursor.getInt(cursor.getColumnIndex(ContactContract.ContactEntry.COST)));
            info = info+"Id : "+id+
                    "\nТовар : "+name+
                    "\nКолличество : "+quantity+
                    "\nДата : "+date+
                    "\nМесто приобритения : "+store+
                    "\nСтоимость : "+cost+
                    "\n____________________________________\n\n";
        }

        Txt_Display.setText(info);

        contactDbHelper.close();

    }

}
