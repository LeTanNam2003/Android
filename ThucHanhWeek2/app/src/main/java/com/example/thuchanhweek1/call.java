package com.example.thuchanhweek1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class call extends AppCompatActivity {

    private EditText edtPhoneNumber;
    private Button btnCall, btnBack;
    private static final int REQUEST_CALL_PERMISSION = 1; // Mã yêu cầu quyền

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần UI
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnCall = findViewById(R.id.btnCall);
        btnBack = findViewById(R.id.btnBack);

        // Xử lý sự kiện nhấn nút gọi
        btnCall.setOnClickListener(v -> makeCall());

        // Xử lý sự kiện nhấn nút quay lại
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(call.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void makeCall() {
        String phoneNumber = edtPhoneNumber.getText().toString().trim();

        if (!phoneNumber.isEmpty()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Yêu cầu cấp quyền
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
            } else {
                // Nếu đã có quyền, thực hiện cuộc gọi
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
        }
    }

    // Xử lý kết quả cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall(); // Nếu được cấp quyền, gọi lại hàm makeCall()
            } else {
                Toast.makeText(this, "Bạn chưa cấp quyền gọi điện!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
