package com.thinkmobiles.bodega.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.thinkmobiles.bodega.Constants;

/**
 * Created by denis on 21.10.15.
 */
public class SharedPrefUtils {

    private static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPrefs(context).edit();
    }

    public static String getLastUpdate(Context context) {
        return getSharedPrefs(context).getString(Constants.SHARED_PREFS_LAST_UPDATE_KEY, "");
    }

    public static void setLastUpdate(Context context, String date) {
        getEditor(context).putString(Constants.SHARED_PREFS_LAST_UPDATE_KEY, date).apply();
    }

}
