package com.example.portal3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class HomeworkDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "homework.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "homework";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CLASS = "class";
    private static final String COLUMN_FILE = "fileUri";


    public HomeworkDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLASS + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_FILE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle DB upgrade as needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertHomework(String title, String class_name, String fileUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("class", class_name);
        values.put("title", title);
        values.put("fileUri", fileUri);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public List<HomeworkModel> getAllHomework() {
        List<HomeworkModel> homeworkList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String className = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FILE));

                homeworkList.add(new HomeworkModel(className, title, filePath));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return homeworkList;
    }


}
