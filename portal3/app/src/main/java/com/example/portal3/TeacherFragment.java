package com.example.portal3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TeacherFragment extends Fragment {

    private ExpandableListView elv_class_schedule;
    private ArrayList<Map<String, String>> listGroup;
    private ArrayList<List<Map<String, String>>> listItem;
    private TextView today_date;
    private SimpleExpandableListAdapter adapter;

    public TeacherFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView teacher = view.findViewById(R.id.teacher_name);
        TextView teacher_id = view.findViewById(R.id.teacher_id);
        TextView teacher_email = view.findViewById(R.id.teacher_email);
        today_date = view.findViewById(R.id.tv_today);
        elv_class_schedule = view.findViewById(R.id.elv_class_schedule);

        // Hiển thị ngày hiện tại
        String currentDate = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN")).format(new Date());
        today_date.setText(currentDate);

        // Nhận dữ liệu từ Bundle
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name", "Không rõ");
            String email = args.getString("email", "Không rõ");
            String id = args.getString("id", "Không rõ");

            teacher.setText("GIÁO VIÊN: " + name);
            teacher_email.setText("Email: " + email);
            teacher_id.setText("MSGV: " + id);
        } else {
            teacher.setText("GIÁO VIÊN: (Không rõ)");
            teacher_email.setText("Email: (Không rõ)");
            teacher_id.setText("MSGV: (Không rõ)");
        }

        // Expandable list setup
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

        elv_class_schedule.setOnChildClickListener(((parent, v1, groupPosition, childPosition, id) -> {
            List<Map<String, String>> childList = listItem.get(groupPosition);
            Map<String, String> childMap = childList.get(childPosition);
            String item = childMap.get("Function");
            Toast.makeText(requireContext(), "Bạn chọn: " + item, Toast.LENGTH_SHORT).show();
            return true;
        }));

        // ==== Xử lý sự kiện nút Đăng xuất ====
        Button btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        initData();
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
