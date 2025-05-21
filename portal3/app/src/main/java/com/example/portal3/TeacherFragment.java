package com.example.portal3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TeacherFragment extends Fragment {

    private TextView today_date;
    private RecyclerView rvClassSchedule;
    private ClassScheduleAdapter adapter;
    private List<ClassGroup> classGroups;

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
        rvClassSchedule = view.findViewById(R.id.rv_class_schedule);
        // Nút giao bài tập
//        Button btnAssignHomework = view.findViewById(R.id.button);
//        btnAssignHomework.setOnClickListener(v -> {
//            Intent intent = new Intent(requireContext(), AssignHomeworkActivity.class);
//            startActivity(intent);
//        });
        // Hiển thị ngày hiện tại
        String currentDate = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN")).format(new Date());
        today_date.setText(currentDate);

        // Setup RecyclerView
        rvClassSchedule.setLayoutManager(new LinearLayoutManager(requireContext()));
        classGroups = new ArrayList<>();
        adapter = new ClassScheduleAdapter(classGroups);
        rvClassSchedule.setAdapter(adapter);

        // Nhận dữ liệu từ Bundle
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name", "Không rõ");
            String email = args.getString("email", "Không rõ");
            String id = args.getString("id", "Không rõ");

            teacher.setText("GIÁO VIÊN: " + name);
            teacher_email.setText("Email: " + email);
            teacher_id.setText("MSGV: " + id);

            loadTeacherCourses(email);

        } else {
            teacher.setText("GIÁO VIÊN: (Không rõ)");
            teacher_email.setText("Email: (Không rõ)");
            teacher_id.setText("MSGV: (Không rõ)");
        }

        // Nút đăng xuất
        Button btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            SharedPreferences prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
            prefs.edit().putBoolean("teacher_logged_in", false).apply();

            Toast.makeText(requireContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void loadTeacherCourses(String emailFromBundle) {
        try {
            InputStream is = requireContext().getAssets().open("teacher_info.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                List<String> partsList = new ArrayList<>();
                Matcher m = Pattern.compile("\"([^\"]*)\"|([^,]+)").matcher(line);
                while (m.find()) {
                    if (m.group(1) != null) {
                        partsList.add(m.group(1).trim());
                    } else if (m.group(2) != null) {
                        partsList.add(m.group(2).trim());
                    }
                }

                if (partsList.size() < 7) continue;

                String email = partsList.get(2);
                if (email.equalsIgnoreCase(emailFromBundle)) {
                    TextView teachDateView = getView().findViewById(R.id.teach_date);
                    TextView absentDayView = getView().findViewById(R.id.absent_day);

                    if (teachDateView != null) teachDateView.setText("Số ngày dạy: " + partsList.get(5));
                    if (absentDayView != null) absentDayView.setText("Số ngày vắng: " + partsList.get(6));

                    classGroups.clear();
                    List<String> courseList = parseCourseList(partsList.get(4));

                    for (String course : courseList) {
                        ClassGroup group = new ClassGroup(course);
                        group.functions.add("Danh sách lớp");
                        group.functions.add("Bài tập");
                        group.functions.add("Điểm số");
                        classGroups.add(group);
                    }

                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(), "Lỗi đọc dữ liệu giáo viên", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private List<String> parseCourseList(String courses) {
        List<String> result = new ArrayList<>();
        Matcher m = Pattern.compile("\"([^\"]*)\"|([^,]+)").matcher(courses);
        while (m.find()) {
            String value = m.group(1) != null ? m.group(1) : m.group(2);
            if (value != null && !value.trim().isEmpty()) {
                result.add(value.trim());
            }
        }
        return result;
    }

    private static class ClassGroup {
        String className;
        List<String> functions;
        boolean expanded;

        ClassGroup(String className) {
            this.className = className;
            this.functions = new ArrayList<>();
            this.expanded = false;
        }
    }

    private class ClassScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_GROUP = 0;
        private static final int TYPE_CHILD = 1;
        private final List<Object> items = new ArrayList<>();

        public ClassScheduleAdapter(List<ClassGroup> groups) {
            setGroups(groups);
        }

        private void setGroups(List<ClassGroup> groups) {
            items.clear();
            for (ClassGroup group : groups) {
                items.add(group);
                if (group.expanded) {
                    items.addAll(group.functions);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            return (items.get(position) instanceof ClassGroup) ? TYPE_GROUP : TYPE_CHILD;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == TYPE_GROUP) {
                View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                return new GroupViewHolder(view);
            } else {
                View view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
                return new ChildViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_GROUP) {
                ClassGroup group = (ClassGroup) items.get(position);
                GroupViewHolder groupHolder = (GroupViewHolder) holder;
                groupHolder.bind(group);
                groupHolder.itemView.setOnClickListener(v -> {
                    group.expanded = !group.expanded;
                    setGroups(classGroups);
                    notifyDataSetChanged();
                });
            } else {
                String func = (String) items.get(position);
                ChildViewHolder childHolder = (ChildViewHolder) holder;
                childHolder.bind(func);
                childHolder.itemView.setOnClickListener(v -> {
                    Toast.makeText(requireContext(), "Bạn chọn: " + func, Toast.LENGTH_SHORT).show();
                });
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        private class GroupViewHolder extends RecyclerView.ViewHolder {
            TextView text1;

            GroupViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
            }

            void bind(ClassGroup group) {
                text1.setText(group.className + (group.expanded ? " ▲" : " ▼"));
                text1.setTextSize(18);
            }
        }

        private class ChildViewHolder extends RecyclerView.ViewHolder {
            TextView text1, text2;

            ChildViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
                text2 = itemView.findViewById(android.R.id.text2);
            }

            void bind(String func) {
                text1.setText(func);
                text2.setText("");
            }
        }
    }
}
