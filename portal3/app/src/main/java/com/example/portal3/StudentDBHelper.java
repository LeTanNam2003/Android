package com.example.portal3;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    public static final String TABLE_USER = "student";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CLASS = "class";

    private static final String DATABASE_CREATE =
            "create table " + TABLE_USER + " (" +
                    COLUMN_ID + " text primary key, " +
                    COLUMN_EMAIL + " text not null, " +
                    COLUMN_PASSWORD + " text not null, " +
                    COLUMN_NAME + " text not null, " +
                    COLUMN_TIME + " text not null, " +
                    COLUMN_CLASS + " text not null)";
    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE); // Tạo bảng nếu chưa tồn tại
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER); // Xóa bảng cũ nếu có
        onCreate(db); // Tạo lại bảng
    }

    // Hàm thêm người dùng vào cơ sở dữ liệu
    public boolean addStudent(String id, String email, String password, String name, String class_name, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("INSERT INTO " + TABLE_USER + " (" + COLUMN_ID + ", " + COLUMN_EMAIL + ", " + COLUMN_PASSWORD + ", " + COLUMN_NAME + ", " + COLUMN_TIME + ", " + COLUMN_CLASS + ") VALUES (?, ?, ?, ?, ?, ?)",
                    new Object[]{id, email, password, name, time, class_name});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Cursor getStudentbyClass(String class_name) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE class = ?", new String[]{class_name});
    }
}
