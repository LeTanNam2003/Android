package com.example.portal3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentFragment extends Fragment {

    private TextView todayDate;
    private RecyclerView rvSubjectList;

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
        rvSubjectList = view.findViewById(R.id.rv_subject_list);

        Button btnLogout = view.findViewById(R.id.btn_logout);
        LinearLayout btnTurnInHW = view.findViewById(R.id.btn_turnin);

        String currentDate = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN")).format(new Date());
        todayDate.setText(currentDate);

        rvSubjectList.setLayoutManager(new LinearLayoutManager(getContext()));

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

        btnTurnInHW.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), StudentHomeworkActivity.class);
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
                List<String> tokens = parseCSVLine(line);
                if (tokens.size() >= 8 && tokens.get(1).trim().equals(email)) {
                    StudentInfo student = new StudentInfo();
                    student.name = tokens.get(0).trim();
                    student.email = tokens.get(1).trim();
                    student.password = tokens.get(2).trim();
                    student.studentId = tokens.get(3).trim();
                    student.date = tokens.get(4).trim();
                    student.subjects = tokens.get(5).trim().split(",\\s*"); // split subjects
                    //student.subjects = tokens.get(5).trim();
                    student.attendedDays = tokens.get(6).trim();
                    student.absentDays = tokens.get(7).trim();
                    return student;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Function to parse CSV line
    private List<String> parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                tokens.add(current.toString());
                current.setLength(0); // Clear the builder
            } else {
                current.append(ch);
            }
        }

        // Add the last token
        tokens.add(current.toString());
        return tokens;
    }
    private void loadSubjectData(String[] subjects) {
        List<SubjectItem> subjectItems = new ArrayList<>();

        for (String subject : subjects) {
            String teacher = subject.contains("Toeic") ? "Thầy A" : "Cô B";
            String room = subject.contains("Toeic") ? "B201" : "C302";
            subjectItems.add(new SubjectItem(subject.trim(), teacher, room));
        }
        /*String teacher = "Thầy A";
        String room = "C302";
        subjectItems.add(new SubjectItem(subjects, teacher, room));*/
        SubjectAdapter adapter = new SubjectAdapter(subjectItems);
        rvSubjectList.setAdapter(adapter);
    }

    private static class StudentInfo {
        String name;
        String email;
        String password;
        String studentId;
        String attendedDays;
        String absentDays;
        String[] subjects;
        String date;
    }

    // SubjectItem model
    private static class SubjectItem {
        String name;
        String teacher;
        String room;

        SubjectItem(String name, String teacher, String room) {
            this.name = name;
            this.teacher = teacher;
            this.room = room;
        }
    }

    // RecyclerView Adapter
    private class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
        private final List<SubjectItem> subjectList;

        SubjectAdapter(List<SubjectItem> subjectList) {
            this.subjectList = subjectList;
        }

        @NonNull
        @Override
        public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_subject, parent, false);
            return new SubjectViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
            SubjectItem item = subjectList.get(position);
            holder.tvSubjectName.setText(item.name);
            holder.tvTeacher.setText("Giảng viên: " + item.teacher);
            holder.tvRoom.setText("Phòng: " + item.room);

            holder.itemView.setOnClickListener(v ->
                    Toast.makeText(getContext(), "Bạn chọn: " + item.name, Toast.LENGTH_SHORT).show());
        }

        @Override
        public int getItemCount() {
            return subjectList.size();
        }

        class SubjectViewHolder extends RecyclerView.ViewHolder {
            TextView tvSubjectName, tvTeacher, tvRoom;

            SubjectViewHolder(@NonNull View itemView) {
                super(itemView);
                tvSubjectName = itemView.findViewById(R.id.tv_subject_name);
                tvTeacher = itemView.findViewById(R.id.tv_teacher);
                tvRoom = itemView.findViewById(R.id.tv_room);
            }
        }
    }
}
