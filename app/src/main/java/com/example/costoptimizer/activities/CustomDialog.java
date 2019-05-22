package com.example.costoptimizer.activities;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.costoptimizer.R;


public class CustomDialog {


    public static void showDialog(Context context, final View.OnClickListener onEdit, final View.OnClickListener onDelete) {
        {

            // Create custom dialog object
            final Dialog dialog = new Dialog(context);
            // Include dialog.xml file
            dialog.setContentView(R.layout.edit_delete_dialog);

            // set values for custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.textDialog);
            text.setText("Выберите действие");

            dialog.show();

            View edit = dialog.findViewById(R.id.edit_button);
            // if decline button is clicked, close the custom dialog
            edit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEdit.onClick(v);
                    dialog.dismiss();
                }
            });
            View deleteButton = dialog.findViewById(R.id.delete_button);
            // if decline button is clicked, close the custom dialog
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDelete.onClick(v);
                    dialog.dismiss();
                }
            });


        }
    }
}