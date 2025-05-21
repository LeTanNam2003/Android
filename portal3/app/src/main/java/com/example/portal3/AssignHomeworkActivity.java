package com.example.portal3;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class AssignHomeworkActivity extends AppCompatActivity {

    private EditText edtHomeworkTitle;
    private Button btnSelectFile, btnSubmit;

    private File copiedFile = null;

    // Launcher chọn file từ bộ nhớ
    private ActivityResultLauncher<String> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    File file = copyFileFromUri(uri);
                    if (file != null) {
                        copiedFile = file;
                        btnSelectFile.setText("Đã chọn file: " + file.getName());
                    } else {
                        Toast.makeText(this, "Lỗi khi lưu file", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_homework);

        edtHomeworkTitle = findViewById(R.id.et_homework_title);
        btnSelectFile = findViewById(R.id.btn_select_file);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSelectFile.setOnClickListener(v -> {
            // Mở trình chọn file (ví dụ chọn PDF, bạn có thể đổi)
            filePickerLauncher.launch("application/pdf");
        });

        btnSubmit.setOnClickListener(v -> {
            String title = edtHomeworkTitle.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tiêu đề bài tập", Toast.LENGTH_SHORT).show();
                return;
            }
            if (copiedFile == null) {
                Toast.makeText(this, "Vui lòng chọn file đính kèm", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("homework_title", title);
            // Trả đường dẫn file (absolute path)
            resultIntent.putExtra("homework_file_path", copiedFile.getAbsolutePath());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    private File copyFileFromUri(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            InputStream inputStream = resolver.openInputStream(uri);
            if (inputStream == null) return null;

            // Thư mục lưu file trong internal storage
            File dir = new File(getFilesDir(), "homeworks");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Lấy tên file gốc
            String fileName = queryFileName(uri);
            if (fileName == null) fileName = "tempfile.pdf";

            File copiedFile = new File(dir, fileName);

            OutputStream outputStream = new FileOutputStream(copiedFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            return copiedFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String queryFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) result = cursor.getString(index);
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}
