package com.comp7506.sql_test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDatabase extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table users("
            + "id integer primary key autoincrement,"
            + "user_name text,"
            + "user_pwd text)";
    public UserDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER);

        ContentValues values = new ContentValues();
        values.put("user_name","test01");
        values.put("user_pwd", "abc12345");
        db.insert("users",null, values);
        values.clear();

        values.put("user_name","test02");
        values.put("user_pwd", "2580");
        db.insert("users",null, values);
        values.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
        onCreate(db);
    }

}

