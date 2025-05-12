package com.example.sinhvien;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageAvatar;
    private TextView textName, textMssv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageAvatar = findViewById(R.id.imageAvatarDetail);
        textName = findViewById(R.id.textNameDetail);
        textMssv = findViewById(R.id.textMssvDetail);

        // Nhận dữ liệu từ Intent
        String name = getIntent().getStringExtra("name");
        String mssv = getIntent().getStringExtra("mssv");
        String avatarPath = getIntent().getStringExtra("avatarPath");

        // Hiển thị dữ liệu
        textName.setText(name);
        textMssv.setText(mssv);

        if (avatarPath != null && !avatarPath.isEmpty()) {
            imageAvatar.setImageURI(Uri.parse(avatarPath));
        } else {
            imageAvatar.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }
}
