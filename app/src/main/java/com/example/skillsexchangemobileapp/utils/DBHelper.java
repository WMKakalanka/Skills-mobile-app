package com.example.skillsexchangemobileapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Database configuration
    private static final String DATABASE_NAME = "UserManagement.db";
    private static final int DATABASE_VERSION = 2;

    // Table: Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_ROLE = "role";
    private static final String COLUMN_USER_INTERESTS = "interests";
    private static final String COLUMN_USER_SKILLS = "skills";
    private static final String COLUMN_USER_EXPERIENCE = "experience";

    // Table: Profile
    private static final String TABLE_PROFILE = "user_profile";
    private static final String COLUMN_PROFILE_ID = "id";
    private static final String COLUMN_PROFILE_NAME = "name";
    private static final String COLUMN_PROFILE_SKILLS = "skills";
    private static final String COLUMN_PROFILE_PICTURE_PATH = "picture_path";

    // SQL for table creation
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_NAME + " TEXT, " +
                    COLUMN_USER_EMAIL + " TEXT, " +
                    COLUMN_USER_PASSWORD + " TEXT, " +
                    COLUMN_USER_ROLE + " TEXT, " +
                    COLUMN_USER_INTERESTS + " TEXT, " +
                    COLUMN_USER_SKILLS + " TEXT, " +
                    COLUMN_USER_EXPERIENCE + " INTEGER);";

    private static final String CREATE_TABLE_PROFILE =
            "CREATE TABLE " + TABLE_PROFILE + " (" +
                    COLUMN_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PROFILE_NAME + " TEXT, " +
                    COLUMN_PROFILE_SKILLS + " TEXT, " +
                    COLUMN_PROFILE_PICTURE_PATH + " TEXT);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PROFILE);

        // Insert default profile data
        ContentValues defaultProfile = new ContentValues();
        defaultProfile.put(COLUMN_PROFILE_NAME, "Default Name");
        defaultProfile.put(COLUMN_PROFILE_SKILLS, "Default Skills");
        defaultProfile.put(COLUMN_PROFILE_PICTURE_PATH, ""); // Default empty picture path
        db.insert(TABLE_PROFILE, null, defaultProfile);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add picture_path column during upgrade
            db.execSQL("ALTER TABLE " + TABLE_PROFILE + " ADD COLUMN " + COLUMN_PROFILE_PICTURE_PATH + " TEXT DEFAULT '';");
        }
    }

    // User Management Methods
    public boolean checkUserCredentials(String email, String password, String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USER_EMAIL + " = ? AND " +
                COLUMN_USER_PASSWORD + " = ? AND " +
                COLUMN_USER_ROLE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password, role});

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }

    public long insertUser(String name, String email, String password, String role,
                           String interests, String skills, int experience) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_ROLE, role);
        values.put(COLUMN_USER_INTERESTS, interests);
        values.put(COLUMN_USER_SKILLS, skills);
        values.put(COLUMN_USER_EXPERIENCE, experience);

        return db.insert(TABLE_USERS, null, values);
    }

    // Profile Management Methods
    public String[] getUserProfile() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROFILE,
                new String[]{COLUMN_PROFILE_NAME, COLUMN_PROFILE_SKILLS, COLUMN_PROFILE_PICTURE_PATH},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(0);
            String skills = cursor.getString(1);
            String picturePath = cursor.getString(2);
            cursor.close();
            return new String[]{name, skills, picturePath};
        }
        return null;
    }

    public boolean updateUserProfile(String name, String skills, String picturePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROFILE_NAME, name);
        values.put(COLUMN_PROFILE_SKILLS, skills);
        values.put(COLUMN_PROFILE_PICTURE_PATH, picturePath);

        // Update profile where the name matches (customize this as needed)
        int rowsAffected = db.update(TABLE_PROFILE, values, COLUMN_PROFILE_NAME + "=?", new String[]{name});
        return rowsAffected > 0;
    }

    public boolean insertCourseDetails(String title, String description, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("start_date", startDate);
        contentValues.put("end_date", endDate);

        long result = db.insert("courses", null, contentValues);
        db.close();
        return result != -1; // Returns true if insertion was successful
    }

}