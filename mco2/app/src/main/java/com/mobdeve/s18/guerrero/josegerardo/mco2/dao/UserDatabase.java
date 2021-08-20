package com.mobdeve.s18.guerrero.josegerardo.mco2.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    //column names
    public static final String TABLE_USERS = "users";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_EMAIL = "email";
    public static final String USERS_PASSWORD = "password";

    private static final String CREATE_USER_TABLE =
            "create table " + TABLE_USERS + " ( "
                    + USERS_USERNAME + " integer primary key, "
                    + USERS_EMAIL + " text, "
                    + USERS_PASSWORD + " text ); ";

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
