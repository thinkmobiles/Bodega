package com.thinkmobiles.bodega;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by denis on 20.10.15.
 */
public class BodegaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Helvetica LT 45 Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
