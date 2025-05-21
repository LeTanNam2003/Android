package com.example.portal3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class StudentList extends Fragment {
    private String class_name_received;
    private StudentDBHelper db;
    public StudentList() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null) {
            class_name_received = getArguments().getString("class_name");
        }
        Log.i(TAG, class_name_received);
        db = new StudentDBHelper(requireContext());
        Cursor student_info_cursor = db.getStudentbyClass(class_name_received);
        ArrayList<String> StudentList = new ArrayList<>();
        if(student_info_cursor.moveToFirst()) {
            do {
                String name_received = student_info_cursor.getString(student_info_cursor.getColumnIndexOrThrow("name"));
                StudentList.add(name_received);
            } while(student_info_cursor.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                StudentList
        );
        ListView listView = view.findViewById(R.id.lv_student);
        listView.setAdapter(adapter);
    }
}
