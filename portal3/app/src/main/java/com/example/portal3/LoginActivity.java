package com.example.portal3;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvSignup; // Thêm TextView để dẫn đến màn hình đăng ký
    DBHelper dbHelper;
    private static String email_typed;
    private String password_typed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.cirLoginButton);
        tvSignup = findViewById(R.id.tvSignup); // Kết nối TextView

        dbHelper = new DBHelper(this);

        // Read login info from .csv file
        List<String[]> csvData = CSVHelper.readCSV(this, "login_2.csv");
        for (int i = 1; i < csvData.size(); i++) {
            String[] row = csvData.get(i);
            String id = row[0];
            String email = row[1];
            String password = row[2];
            String name = row[3];
            String schedule = row[4];
            Log.i(TAG, schedule);
            // Split the schedule
            if(schedule != null && !schedule.isEmpty()) {
                String[] schedules = schedule.split(";");
                for (String s : schedules) {
                    String[] parts = s.trim().split("\\|");
                    if (parts.length == 3) {
                        String time = parts[0].trim();
                        String day = parts[1].trim();
                        String className = parts[2].trim();
                        Log.i(TAG, time);
                        Log.i(TAG, day);
                        Log.i(TAG, className);
                        // Do something with time, day, className
                        boolean checker = dbHelper.addSchedule(id, time, className, day);
                    }
                }
            }
            boolean checker = dbHelper.addUser(id, email, password, name);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_typed = etEmail.getText().toString().trim();
                password_typed = etPassword.getText().toString().trim();

                if (dbHelper.checkUser(email_typed, password_typed)) {
                    // Đăng nhập thành công, chuyển sang MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Không quay lại LoginActivity
                } else {
                    Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Thiết lập sự kiện cho TextView "Chưa có tài khoản?"
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình đăng ký
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public static String getEmail_typed() {
        return email_typed;
    }
}
