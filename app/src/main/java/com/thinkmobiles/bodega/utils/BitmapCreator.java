package com.thinkmobiles.bodega.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by illia on 22.10.15.
 */
public abstract class BitmapCreator {

    // TODO: solve OutOfMemoryError in decodeFile
    public static Bitmap getBitmap(String _path) {
        Bitmap bitmap = BitmapFactory.decodeFile(_path);
        if (bitmap == null)
            Log.d("BitmapCreator", "BITMAP NULL");
        return bitmap;
    }
}