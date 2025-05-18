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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Button btnLogout = view.findViewById(R.id.btn_logout);

        String currentDate = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN")).format(new Date());
        todayDate.setText(currentDate);

        Bundle args = getArguments();
        if (args != null) {
            String email = args.getString("email", "");

            StudentInfo student = getStudentInfoByEmail(email);
            if (student != null) {
                studentName.setText("SINH VIÊN: " + student.name);
                studentEmail.setText("Email: " + student.email);
                studentId.setText("MSSV: " + student.studentId);
                attendDay.setText("Số buổi học: " + student.attendedDays);
                absentDay.setText("Số buổi vắng: " + student.absentDays);

                loadSubjectData(student.subjects);
            } else {
                studentName.setText("SINH VIÊN: Không rõ");
                studentEmail.setText("Email: Không rõ");
                studentId.setText("MSSV: Không rõ");
            }
        }

        btnLogout.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).logoutStudent();
            }

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private StudentInfo getStudentInfoByEmail(String email) {
        try {
            InputStream is = requireContext().getAssets().open("student_info.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            reader.readLine(); // Bỏ qua header

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 7 && tokens[1].trim().equals(email)) {
                    StudentInfo student = new StudentInfo();
                    student.name = tokens[0].trim();
                    student.email = tokens[1].trim();
                    student.password = tokens[2].trim();
                    student.studentId = tokens[3].trim();
                    student.attendedDays = tokens[4].trim();
                    student.absentDays = tokens[5].trim();
                    student.subjects = tokens[6].trim().split(";");
                    return student;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadSubjectData(String[] subjects) {
        groupList = new ArrayList<>();
        childList = new ArrayList<>();

        for (String subject : subjects) {
            Map<String, String> groupMap = new HashMap<>();
            groupMap.put("Subject", subject.trim());
            groupList.add(groupMap);

            List<Map<String, String>> children = new ArrayList<>();
            Map<String, String> teacher = new HashMap<>();
            teacher.put("Detail", "Giảng viên: " + (subject.contains("Toeic") ? "Thầy A" : "Cô B"));
            children.add(teacher);

            Map<String, String> room = new HashMap<>();
            room.put("Detail", "Phòng: " + (subject.contains("Toeic") ? "B201" : "C302"));
            children.add(room);

            childList.add(children);
        }

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
            String detail = childList.get(groupPosition).get(childPosition).get("Detail");
            Toast.makeText(getContext(), "Bạn chọn: " + detail, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private static class StudentInfo {
        String name;
        String email;
        String password;
        String studentId;
        String attendedDays;
        String absentDays;
        String[] subjects;
    }
}
