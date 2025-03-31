package com.example.thuchanhweek1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class thongtin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thongtin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String hoTen = intent.getStringExtra("hoTen");
        String mssv = intent.getStringExtra("mssv");
        String lop = intent.getStringExtra("lop");
        String namHoc = intent.getStringExtra("namHoc");
        String chuyenNganh = intent.getStringExtra("chuyenNganh");

        // Gán dữ liệu vào TextView
        ((TextView) findViewById(R.id.tvHoTen)).setText(hoTen);
        ((TextView) findViewById(R.id.tvMSSV)).setText(mssv);
        ((TextView) findViewById(R.id.tvLop)).setText(lop);
        ((TextView) findViewById(R.id.tvNamHoc)).setText(namHoc);
        ((TextView) findViewById(R.id.tvChuyenNganh)).setText(chuyenNganh);

        // Xử lý sự kiện nút quay lại
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
