package com.example.mtrsliit.productdetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";


    /*CREATE TABLE USERS( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRSTNAME + " TEXT, "+ KEY_EMAIL + " TEXT,"+ KEY_PHONE +" TEXT,"+KEY_PASSWORD + " TEXT  );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_USERS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        onCreate(db);
    }

    public long addUserDetail(String name, String email,String phone,String pwd,String cpwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE, phone);
        values.put(KEY_PASSWORD, pwd);

        // insert row in USERS table
        long insert = db.insert(TABLE_USER, null, values);

        return insert;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                userModel.setName(c.getString(c.getColumnIndex(KEY_FIRSTNAME)));
                userModel.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
                userModel.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));
                userModel.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                // adding to USERS list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public int updateUser(int id, String name,String email,String phone,String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE, phone);
        values.put(KEY_PASSWORD, pwd);
        // update row in USERS table base on students.is value
        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteUSer(int id) {

        // delete row in USERS table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    //public long addUserDetail(String s, String s1, String s2, String s3, String s4) {
    //}
}
