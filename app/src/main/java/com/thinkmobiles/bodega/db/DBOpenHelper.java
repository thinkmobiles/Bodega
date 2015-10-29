package com.thinkmobiles.bodega.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thinkmobiles.bodega.db.daogen.DaoMaster;

/**
 * Created by denis on 28.10.15.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DaoMaster.createAllTables(db, true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
