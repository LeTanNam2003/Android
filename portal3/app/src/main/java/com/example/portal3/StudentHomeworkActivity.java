package com.example.portal3;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StudentHomeworkActivity extends AppCompatActivity {
    private ListView listView;
    private HomeworkDBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private List<HomeworkModel> homeworkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_homework);

        listView = findViewById(R.id.listView_homework);
        dbHelper = new HomeworkDBHelper(this);

        homeworkList = dbHelper.getAllHomework();

        List<String> displayList = new ArrayList<>();
        for (HomeworkModel hw : homeworkList) {
            displayList.add(hw.className + " - " + hw.title);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            HomeworkModel selected = homeworkList.get(position);
            Toast.makeText(this, "File path:\n" + selected.filePath, Toast.LENGTH_LONG).show();

            // Optionally open the file
            openFile(selected.filePath);
        });
    }

    private void openFile(String filePath) {
        File file = new File(getFilesDir(), filePath);
        Uri uri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                file
        );
        if (!file.exists()) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
            return;
        }

        uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "*/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No app found to open this file", Toast.LENGTH_SHORT).show();
        }
    }
}
