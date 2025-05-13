package com.example.portal3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class StudentFragment extends Fragment {

    private TextView todayDate;
    private ExpandableListView elvSubjects;
    private ArrayList<Map<String, String>> groupList;
    private ArrayList<List<Map<String, String>>> childList;
    private SimpleExpandableListAdapter adapter;

    public StudentFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView studentName = view.findViewById(R.id.student_name);
        TextView studentId = view.findViewById(R.id.student_id);
        TextView studentEmail = view.findViewById(R.id.student_email);
        TextView attendDay = view.findViewById(R.id.attend_day);
        TextView absentDay = view.findViewById(R.id.absent_day);
        todayDate = view.findViewById(R.id.tv_today_date);
        elvSubjects = view.findViewById(R.id.elv_subject_list);

        String currentDate = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN")).format(new Date());
        todayDate.setText(currentDate);

        // Gán dữ liệu mẫu
        studentName.setText("SINH VIÊN: Nguyễn Văn A");
        studentId.setText("MSSV: SV123456");
        studentEmail.setText("Email: nguyenvana@email.com");
        attendDay.setText("Số buổi học: 45");
        absentDay.setText("Số buổi vắng: 2");

        // Expandable List
        groupList = new ArrayList<>();
        childList = new ArrayList<>();

        adapter = new SimpleExpandableListAdapter(
                requireContext(),
                groupList,
                android.R.layout.simple_expandable_list_item_1,
                new String[]{"Subject"},
                new int[]{android.R.id.text1},
                childList,
                R.layout.child_item_clickable_text,
                new String[]{"Detail"},
                new int[]{R.id.tv_child}
        );
        elvSubjects.setAdapter(adapter);

        elvSubjects.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Map<String, String> child = childList.get(groupPosition).get(childPosition);
            String detail = child.get("Detail");
            Toast.makeText(getContext(), "Bạn chọn: " + detail, Toast.LENGTH_SHORT).show();
            return true;
        });

        loadSubjectData();
    }

    private void loadSubjectData() {
        groupList.clear();
        childList.clear();

        String[] subjects = {"Toeic 900+", "Ielt 6.5"};
        String[][] details = {
                {"Giảng viên: Thầy A", "Phòng: B201"},
                {"Giảng viên: Cô B", "Phòng: C302"}
        };

        for (int i = 0; i < subjects.length; i++) {
            Map<String, String> subjectMap = new HashMap<>();
            subjectMap.put("Subject", subjects[i]);
            groupList.add(subjectMap);

            List<Map<String, String>> child = new ArrayList<>();
            for (String d : details[i]) {
                Map<String, String> detailMap = new HashMap<>();
                detailMap.put("Detail", d);
                child.add(detailMap);
            }
            childList.add(child);
        }

        adapter.notifyDataSetChanged();
    }
}
