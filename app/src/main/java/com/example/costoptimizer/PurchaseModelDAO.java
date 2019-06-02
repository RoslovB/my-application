package com.example.costoptimizer;

import com.example.costoptimizer.models.PurchaseCategory;
import com.example.costoptimizer.models.PurchaseModel;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PurchaseModelDAO extends BaseDaoImpl<PurchaseModel, Integer> {

    PurchaseModelDAO(ConnectionSource connectionSource, Class<PurchaseModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<PurchaseModel> getAllPurchases() throws SQLException {
        return this.queryForAll();
    }

    public List<PurchaseModel> getPurchasesForMonth() throws SQLException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date monthAgo = cal.getTime();
        QueryBuilder<PurchaseModel, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().ge("date", monthAgo);
        PreparedQuery<PurchaseModel> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    public List<PurchaseModel> getPurchasesForToday() throws SQLException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date day = cal.getTime();
        QueryBuilder<PurchaseModel, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().ge("date", day);
        PreparedQuery<PurchaseModel> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    public int getMoneySpentForMonth() throws SQLException {
        List<PurchaseModel> purchaseModels = this.getPurchasesForMonth();
        int total = 0;
        for (PurchaseModel purchase : purchaseModels) {
            total += purchase.getTotal();
        }
        return total;
    }

    public int getMoneySpentToday() throws SQLException {
        List<PurchaseModel> purchaseModels = this.getPurchasesForToday();
        int total = 0;
        for (PurchaseModel purchase : purchaseModels) {
            total += purchase.getTotal();
        }
        return total;
    }

    public static List<PurchaseModel> getPurchasesByDate(List<PurchaseModel> purchases, Date date) {
        List<PurchaseModel> purchasesOfDate = new ArrayList<>();
        for (PurchaseModel purchase : purchases) {
            if (purchase.date.getDate() == date.getDate() &&
                    purchase.date.getMonth() == date.getMonth()) {
                purchasesOfDate.add(purchase);
            }
        }
        return purchasesOfDate;
    }

    public static List<PurchaseModel> getPurchasesByCategory(List<PurchaseModel> purchases, PurchaseCategory category) {
        List<PurchaseModel> outPurchases = new ArrayList<>();
        for (PurchaseModel purchase : purchases) {
            if (purchase.category == category) {
                outPurchases.add(purchase);
            }
        }
        return outPurchases;
    }
}