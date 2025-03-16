package com.example.helloandriod;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Ánh xạ các thành phần giao diện
        TextView textEquation = findViewById(R.id.textEquation);
        TextView textResult = findViewById(R.id.textResult);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Lấy kết quả từ Intent
        String result = getIntent().getStringExtra("result");
        textResult.setText(result);

        // Xử lý khi nhấn nút "Quay lại"
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng SecondActivity để quay lại MainActivity
            }
        });
    }
}
