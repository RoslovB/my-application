package com.example.costoptimizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class ContactDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Purchase_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "+ ContactContract.ContactEntry.TABLE_NAME+
            "("+ ContactContract.ContactEntry.PRODUCT_ID+" number,"+
            ContactContract.ContactEntry.NAME+" text,"+
            ContactContract.ContactEntry.QUANTITY+" text,"+
            ContactContract.ContactEntry.DATE+" date,"+
            ContactContract.ContactEntry.STORE+" text,"+
            ContactContract.ContactEntry.COST+" number);";
    public static final String DROP_TABLE = "drop table if exists "+ ContactContract.ContactEntry.TABLE_NAME;

    public ContactDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        //Log.d("Database Operations","Database created...");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("Database Operations","Table created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addPurchase(int id, String name, String qantity, String date, String store, int cost, SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactContract.ContactEntry.PRODUCT_ID,id);
        contentValues.put(ContactContract.ContactEntry.NAME,name);
        contentValues.put(ContactContract.ContactEntry.QUANTITY,qantity);
        contentValues.put(ContactContract.ContactEntry.DATE,date);
        contentValues.put(ContactContract.ContactEntry.STORE,store);
        contentValues.put(ContactContract.ContactEntry.COST,cost);

        database.insert(ContactContract.ContactEntry.TABLE_NAME,null,contentValues);
        Log.d("Database Operations","One Raw inserted...");
    }

    public Cursor readPurchase(SQLiteDatabase database)
    {
        String[] projections = {ContactContract.ContactEntry.PRODUCT_ID,
                ContactContract.ContactEntry.NAME,
                ContactContract.ContactEntry.QUANTITY,
                ContactContract.ContactEntry.DATE,
                ContactContract.ContactEntry.STORE,
                ContactContract.ContactEntry.COST};

        Cursor cursor = database.query(ContactContract.ContactEntry.TABLE_NAME,
                projections, null, null, null, null, null);

        return cursor;
    }

    public void updateContact(int id, String name, int quantity, String date, String store, int cost,SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactContract.ContactEntry.NAME,name);
        contentValues.put(ContactContract.ContactEntry.QUANTITY,quantity);
        contentValues.put(ContactContract.ContactEntry.DATE,date);
        contentValues.put(ContactContract.ContactEntry.STORE,store);
        contentValues.put(ContactContract.ContactEntry.COST,cost);

        String selecetion = ContactContract.ContactEntry.PRODUCT_ID+ " = "+id;

        database.update(ContactContract.ContactEntry.TABLE_NAME,contentValues,selecetion,null);

    }

    public void deleteContact(int id,SQLiteDatabase database)
    {

        String selection = ContactContract.ContactEntry.PRODUCT_ID+" = "+id;
        database.delete(ContactContract.ContactEntry.TABLE_NAME,selection,null);

    }



}
