package com.example.portal3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 2;

    // Tên bảng và các cột
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String TABLE_USER_SCHEDULE = "user_schedule";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CLASS = "class";

    private static final String DATABASE_CREATE =
            "create table " + TABLE_USER + " (" +
                    COLUMN_ID + " text primary key, " +
                    COLUMN_EMAIL + " text not null, " +
                    COLUMN_PASSWORD + " text not null, " +
                    COLUMN_NAME + " text not null)";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE); // Tạo bảng nếu chưa tồn tại
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_SCHEDULE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID + " text not null, " +
                COLUMN_TIME + " text not null, " +
                COLUMN_CLASS + " text not null)");
        db.execSQL("CREATE TABLE IF NOT EXISTS visits(id INTEGER PRIMARY KEY AUTOINCREMENT, count INTEGER)");
        db.execSQL("INSERT INTO visits (count) SELECT 0 WHERE NOT EXISTS (SELECT 1 FROM visits)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER); // Xóa bảng cũ nếu có
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SCHEDULE);
        onCreate(db); // Tạo lại bảng
    }

    // Hàm thêm người dùng vào cơ sở dữ liệu
    public boolean addUser(String id, String email, String password, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("INSERT INTO " + TABLE_USER + " (" + COLUMN_ID + ", " + COLUMN_EMAIL + ", " + COLUMN_PASSWORD + ", " + COLUMN_NAME + ") VALUES (?, ?, ?, ?)",
                        new Object[]{id, email, password, name});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSchedule(String id, String time, String class_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("INSERT INTO " + TABLE_USER_SCHEDULE + " (" + COLUMN_ID + ", " + COLUMN_TIME + ", " + COLUMN_CLASS + ") VALUES (?, ?, ?)",
                    new Object[]{id, time, class_name});
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm kiểm tra đăng nhập
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER +
                        " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{email, password});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true; // Người dùng tồn tại
        }
        return false; // Người dùng không tồn tại
    }
    public void increaseVisitCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE visits SET count = count + 1");
    }

    public int getVisitCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count FROM visits", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public Cursor getTeacherInfoByMail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE email = ?", new String[]{email});
    }

    public Cursor getScheduleByID(String id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER_SCHEDULE + " WHERE _id = ?", new String[]{id});
    }

}
