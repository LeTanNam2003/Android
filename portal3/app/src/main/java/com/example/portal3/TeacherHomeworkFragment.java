package com.example.portal3;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import static com.example.portal3.LoginActivity.getEmail_typed;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;




public class TeacherHomeworkFragment extends Fragment {
    private static final int PICK_FILE_REQUEST = 1;

    private EditText etTitle, etDescription;
    private TextView tvFileName;
    private Uri fileUri = null;
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private HomeworkDBHelper dbHelper;
    private String attachedFileUriString = null; // Store picked file URI as string
    private String class_name = null;
    private String user_id = null;

    public TeacherHomeworkFragment() {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_homework, container, false);
    }
    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etTitle = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_description);
        tvFileName = view.findViewById(R.id.tv_file_name);

        Button btnAttach = view.findViewById(R.id.btn_attach);
        Button btnSubmit = view.findViewById(R.id.btn_submit);
        Button btnReturn = view.findViewById(R.id.btn_return);

        // Register file picker launcher
        dbHelper = new HomeworkDBHelper(requireContext());
        DBHelper db_info = new DBHelper(requireContext());
        String teacher_email = getEmail_typed();
        Log.i(TAG, teacher_email);
        Cursor teacher_info_cursor = db_info.getTeacherInfoByMail(teacher_email);
        if(teacher_info_cursor != null && teacher_info_cursor.moveToFirst()) {
            user_id = teacher_info_cursor.getString(teacher_info_cursor.getColumnIndexOrThrow("_id"));
        }
        Log.i(TAG, user_id);
        Cursor teacher_schedule_cursor = db_info.getScheduleByID(user_id);
        if(teacher_schedule_cursor != null && teacher_schedule_cursor.moveToFirst()) {
            do {
                class_name = teacher_schedule_cursor.getString(teacher_schedule_cursor.getColumnIndexOrThrow("class"));
            } while(teacher_schedule_cursor.moveToNext());
        }
        Log.i(TAG, class_name);
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri fileUri = result.getData().getData();
                        int takeFlags = result.getData().getFlags();
                        takeFlags &= (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        requireContext().getContentResolver().takePersistableUriPermission(fileUri, takeFlags);
                        // Display file name
                        String fileName = getFileNameFromUri(fileUri);
                        tvFileName.setText(fileName);

                        // Save URI string for saving to DB later
                        attachedFileUriString = fileUri.toString();
                    }
                }
        );

        btnAttach.setOnClickListener(v -> openFilePicker());

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a title", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert homework data including file URI string into SQLite
            boolean inserted = dbHelper.insertHomework(title, description, class_name, attachedFileUriString);
            if (inserted) {
                Toast.makeText(requireContext(), "Homework assigned!", Toast.LENGTH_SHORT).show();

                // Optionally clear fields
                etTitle.setText("");
                etDescription.setText("");
                tvFileName.setText("No file attached");
                attachedFileUriString = null;

            } else {
                Toast.makeText(requireContext(), "Failed to assign homework", Toast.LENGTH_SHORT).show();
            }
        });
        btnReturn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");  // Allow all file types, or specify mime types
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        filePickerLauncher.launch(intent);
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}


