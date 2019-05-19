package com.example.costoptimizer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.costoptimizer.Models.PurchaseModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "cost_optimizer.db";
    public static final int DATABASE_VERSION = 1;
    private Dao<PurchaseModel, Integer> purchaseModelDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PurchaseModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer) {
        try {
            TableUtils.dropTable(connectionSource, PurchaseModel.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //синглтон для GoalDAO
    public Dao<PurchaseModel, Integer> getPurchaseModelDao() throws SQLException {
        if (purchaseModelDao == null) {
            purchaseModelDao = getDao(PurchaseModel.class);
        }
        return purchaseModelDao;
    }


    //выполняется при закрытии приложения
    @Override
    public void close() {
        super.close();
        purchaseModelDao = null;
    }
}
