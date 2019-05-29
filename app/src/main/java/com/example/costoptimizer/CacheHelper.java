package com.example.costoptimizer;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by erma4elo on 09.10.17.
 */

public class CacheHelper {
    private static final Gson gson = new GsonBuilder().create();

    public static int getMonthlyLimit(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MonthlyLimit", Context.MODE_PRIVATE);
        String limit = preferences.getString("limit", "");
        assert limit != null;
        return limit.isEmpty() ? 0 : Integer.parseInt(limit);
    }

    public static void saveMonthlyLimit(Context context, String moneyLimit) {
        final SharedPreferences preferences = context.getSharedPreferences("MonthlyLimit", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("limit", moneyLimit);
        editor.apply();
    }

    public static int getDailyLimit(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("DailyLimit", Context.MODE_PRIVATE);
        String limit = preferences.getString("DailyLimit", "");
        assert limit != null;
        return limit.isEmpty() ? 0 : Integer.parseInt(limit);
    }

    public static void saveDailyLimit(Context context, String moneyLimit) {
        final SharedPreferences preferences = context.getSharedPreferences("DailyLimit", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("DailyLimit", moneyLimit);
        editor.apply();
    }
}
