package com.example.portal3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TeacherAuthActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_TEACHER_LOGGED_IN = "teacher_logged_in";

    public static class Teacher {
        public String id;
        public String name;
        public String email;

        public Teacher(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_auth);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            Teacher teacher = validateLogin(email, password);
            if (teacher != null) {
                // Lưu trạng thái đăng nhập
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                prefs.edit().putBoolean(KEY_TEACHER_LOGGED_IN, true).apply();

                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                // Chuyển sang MainActivity và truyền dữ liệu
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("teacher_id", teacher.id);
                intent.putExtra("teacher_name", teacher.name);
                intent.putExtra("teacher_email", teacher.email);
                intent.putExtra("open_teacher_fragment", true);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Teacher validateLogin(String email, String password) {
        try {
            InputStream is = getAssets().open("teacher.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;

            reader.readLine(); // Bỏ dòng tiêu đề

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                String teacherId = parts[0].trim();
                String name = parts[1].trim();
                String userEmail = parts[2].trim().toLowerCase();
                String userPass = parts[3].trim();

                if (userEmail.equals(email.toLowerCase()) && userPass.equals(password)) {
                    reader.close();
                    return new Teacher(teacherId, name, userEmail);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi đọc file tài khoản giáo viên", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
