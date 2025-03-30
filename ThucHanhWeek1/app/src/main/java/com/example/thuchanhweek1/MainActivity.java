package com.example.thuchanhweek1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText edtHoTen, edtMSSV, edtLop;
    private RadioGroup groupNamHoc, groupChuyenNganh;
    private Button btnCollectInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần
        edtHoTen = findViewById(R.id.edtHoTen);
        edtMSSV = findViewById(R.id.edtMSSV);
        edtLop = findViewById(R.id.edtLop);
        groupNamHoc = findViewById(R.id.groupNamHoc);
        groupChuyenNganh = findViewById(R.id.groupChuyenNganh);
        btnCollectInfo = findViewById(R.id.btnCollectInfo);

        btnCollectInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStudentInfo();
            }
        });
    }

    private void sendStudentInfo() {
        String hoTen = edtHoTen.getText().toString().trim();
        String mssv = edtMSSV.getText().toString().trim();
        String lop = edtLop.getText().toString().trim();

        // Lấy năm học
        int selectedNamHocId = groupNamHoc.getCheckedRadioButtonId();
        String namHoc = "";
        if (selectedNamHocId != -1) {
            RadioButton selectedNamHoc = findViewById(selectedNamHocId);
            namHoc = selectedNamHoc.getText().toString();
        }

        // Lấy chuyên ngành
        int selectedChuyenNganhId = groupChuyenNganh.getCheckedRadioButtonId();
        String chuyenNganh = "";
        if (selectedChuyenNganhId != -1) {
            RadioButton selectedChuyenNganh = findViewById(selectedChuyenNganhId);
            chuyenNganh = selectedChuyenNganh.getText().toString();
        }

        // Gửi dữ liệu sang ThongTinActivity
        Intent intent = new Intent(MainActivity.this, thongtin.class);
        intent.putExtra("hoTen", hoTen);
        intent.putExtra("mssv", mssv);
        intent.putExtra("lop", lop);
        intent.putExtra("namHoc", namHoc);
        intent.putExtra("chuyenNganh", chuyenNganh);
        startActivity(intent);
    }
}
