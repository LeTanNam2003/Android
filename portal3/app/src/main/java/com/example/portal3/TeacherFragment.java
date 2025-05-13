package com.example.portal3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
//import java.util.Date;
//import java.util.Locale;

public class TeacherFragment extends Fragment {

    private ExpandableListView elv_class_schedule;
    private ArrayList listGroup;
    private ArrayList listItem;
    private TextView today_date;
    private SimpleExpandableListAdapter adapter;
    public TeacherFragment() {
        // Required empty public constructor
        //super(R.layout.fragment_teacher);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView teacher = view.findViewById(R.id.teacher_name);
        TextView teacher_id = view.findViewById(R.id.teacher_id);
        TextView teacher_email = view.findViewById(R.id.teacher_email);
        super.onViewCreated(view, savedInstanceState);
        today_date = view.findViewById(R.id.tv_today);
        String currentDate = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN")).format(new Date());
        today_date.setText(currentDate);
        elv_class_schedule = view.findViewById(R.id.elv_class_schedule);
        listGroup = new ArrayList<>();
        listItem = new ArrayList<>();
        adapter = new SimpleExpandableListAdapter(
                requireContext(),
                listGroup,
                android.R.layout.simple_expandable_list_item_1,
                new String[]{"Class"},
                new int[]{android.R.id.text1},
                listItem,
                R.layout.child_item_clickable_text,
                new String[]{"Function"},
                new int[]{R.id.tv_child}
        );
        elv_class_schedule.setAdapter(adapter);
        elv_class_schedule.setOnChildClickListener(((parent, v, groupPosition, childPosition, id) -> {
            List<Map<String, String>> childList = (List<Map<String, String>>) listItem.get(groupPosition);
            Map<String, String> childMap = childList.get(childPosition);
            String item = childMap.get("Function");
            Toast.makeText(requireContext(), "Clicked: " + item, Toast.LENGTH_SHORT).show();
            return true;
        }));
        initData();


        teacher.setText(teacher.getText().toString() + "VŨ NAM SƠN");
        teacher_id.setText(teacher_id.getText().toString() + "T-001");
        teacher_email.setText(teacher_email.getText().toString() + "vunamson@email.com");

    }

    private void initData() {
        String[] class_teach = {"IELTS 6.5 (LỚP A)", "TOEIC 900 (LỚP B)"};
        String[][] function = {
                {"Danh sách lớp", "Bài tập", "Điểm số"},
                {"Danh sách lớp", "Bài tập", "Điểm số"}
        };

        for (int i = 0; i < class_teach.length; i++) {
            Map<String, String> classMap = new HashMap<>();
            classMap.put("Class", class_teach[i]);
            listGroup.add(classMap);

            List<Map<String, String>> childList = new ArrayList<>();
            for (String func : function[i]) {
                Map<String, String> funcMap = new HashMap<>();
                funcMap.put("Function", func);
                childList.add(funcMap);
            }
            listItem.add(childList);
        }

        adapter.notifyDataSetChanged();
    }

}