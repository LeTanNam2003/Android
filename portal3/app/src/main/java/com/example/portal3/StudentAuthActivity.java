package com.example.portal3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StudentAuthActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button loginButton;

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_STUDENT_LOGGED_IN = "student_logged_in";

    public static class Student {
        public String name;
        public String email;
        public String password;
        public String studentId;

        public Student(String name, String email, String password, String studentId) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.studentId = studentId;
        }
    }

    private Student validateLogin(String email, String password) {
        try {
            InputStream is = getAssets().open("student.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            reader.readLine(); // bỏ qua dòng tiêu đề

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0].trim();
                    String userEmail = parts[1].trim();
                    String userPass = parts[2].trim();
                    String studentId = parts[3].trim();

                    if (userEmail.equals(email) && userPass.equals(password)) {
                        return new Student(name, userEmail, userPass, studentId);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            Student student = validateLogin(email, password);
            if (student != null) {
                // Lưu trạng thái đăng nhập
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                prefs.edit().putBoolean(KEY_STUDENT_LOGGED_IN, true).apply();

                // Truyền dữ liệu sang MainActivity qua Intent extras (nếu cần)
                Intent intent = new Intent(StudentAuthActivity.this, MainActivity.class);
                intent.putExtra("student_name", student.name);
                intent.putExtra("student_email", student.email);
                intent.putExtra("student_id", student.studentId);

                // Nếu muốn có thể set thêm cờ để MainActivity tự động load StudentFragment
                intent.putExtra("open_student_fragment", true);

                startActivity(intent);
                finish();
            } else {
                Toast.makeText(StudentAuthActivity.this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
