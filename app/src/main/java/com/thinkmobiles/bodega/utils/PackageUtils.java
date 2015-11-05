package com.thinkmobiles.bodega.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by illia on 04.11.15.
 */
public abstract class PackageUtils {

    public static boolean isAppInstalled(String packageName, Context c) {
        Intent mIntent = c.getPackageManager().getLaunchIntentForPackage(packageName);
        return mIntent != null;
    }
}
