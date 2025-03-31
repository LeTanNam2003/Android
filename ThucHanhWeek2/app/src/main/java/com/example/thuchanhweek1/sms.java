package com.example.thuchanhweek1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class sms extends AppCompatActivity {

    private EditText edtPhoneNumber, edtMessage;
    private Button btnSendSms, btnBack; // Thêm nút Quay Lại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sms);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần UI
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtMessage = findViewById(R.id.edtMessage);
        btnSendSms = findViewById(R.id.btnSendSMS);
        btnBack = findViewById(R.id.btnBack); // Ánh xạ nút Quay Lại

        // Xử lý sự kiện nhấn nút gửi tin nhắn
        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });

        // Xử lý sự kiện nhấn nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sms.this, MainActivity.class);
                startActivity(intent);
                finish(); // Đóng activity hiện tại
            }
        });
    }

    private void sendSms() {
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String message = edtMessage.getText().toString().trim();

        if (!phoneNumber.isEmpty() && !message.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:" + phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        }
    }
}
