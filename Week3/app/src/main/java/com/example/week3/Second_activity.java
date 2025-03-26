package com.example.week3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Second_activity extends AppCompatActivity {
    EditText teacherFix;
    Button btnFixed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Ánh xạ view
        teacherFix = findViewById(R.id.paragraph_editText);
        btnFixed = findViewById(R.id.corrected_Button);

        // Nhận dữ liệu từ MainActivity
        Intent receivedIntent = getIntent();
        String paragraphReceived = receivedIntent.getStringExtra("student_write");
        teacherFix.setText(paragraphReceived);

        // Xử lý khi nhấn nút "ĐÃ SỬA BÀI"
        btnFixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacherText = teacherFix.getText().toString();

                // Tạo Intent mới để trả về kết quả
                Intent resultIntent = new Intent();
                resultIntent.putExtra("fixed", teacherText);
                setResult(66, resultIntent);
                finish();
            }
        });
    }
}
