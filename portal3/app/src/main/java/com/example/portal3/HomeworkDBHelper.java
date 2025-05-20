package com.example.portal3;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;


public class HomeworkDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "homework.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "homework";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CLASS = "class";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FILE = "fileUri";


    public HomeworkDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                "id PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLASS + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_FILE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle DB upgrade as needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertHomework(String title, String description, String class_name, String fileUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("class", class_name);
        values.put("title", title);
        values.put("description", description);
        values.put("fileUri", fileUri);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }
}
