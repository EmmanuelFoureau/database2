package com.example.evoliris.mysqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.evoliris.mysqlite.db.dao.UserDAO;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "HypeSQL";
    public static int CURRENT_VERSION = 1;
    public DbHelper(Context context) {
        super(context, DB_NAME, null, CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (CURRENT_VERSION == oldVersion){
            db.execSQL(UserDAO.DROP);
            CURRENT_VERSION = newVersion;
            db.execSQL(UserDAO.CREATE);
        }
    }
}
