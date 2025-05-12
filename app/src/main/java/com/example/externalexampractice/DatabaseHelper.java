package com.example.externalexampractice.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.externalexampractice.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "user_info";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_GENDER = "gender";
    private static final String COL_ADDRESS = "address";
    private static final String COL_PHONE = "phone";
    private static final String COL_PASSWORD = "password";
    private static final String COL_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_EMAIL + " TEXT,"
                + COL_GENDER + " TEXT,"
                + COL_ADDRESS + " TEXT,"
                + COL_PHONE + " TEXT,"
                + COL_PASSWORD + " TEXT,"
                + COL_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert user data
    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_GENDER, user.getGender());
        values.put(COL_ADDRESS, user.getAddress());
        values.put(COL_PHONE, user.getPhone());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_IMAGE, user.getImage());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    // Fetch all users (if needed)
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        // Query to fetch user details by ID
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_ID, COL_NAME, COL_EMAIL, COL_GENDER, COL_ADDRESS, COL_PHONE, COL_PASSWORD, COL_IMAGE},
                COL_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve user data from cursor
            user = new User(
                    cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COL_GENDER)),
                    cursor.getString(cursor.getColumnIndex(COL_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(COL_PHONE)),
                    cursor.getString(cursor.getColumnIndex(COL_PASSWORD)),
                    cursor.getBlob(cursor.getColumnIndex(COL_IMAGE))
            );
            cursor.close();
        }

        db.close();  // Close the database after operation
        return user;  // Return the User object or null if not found
    }
    public int deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"id = ?",new String[]{String.valueOf(id)});
    }

}
