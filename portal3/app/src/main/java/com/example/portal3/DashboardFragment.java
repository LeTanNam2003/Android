package com.example.portal3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private TextView tvTotalStudents, tvTotalTeachers, tvTotalClasses;
    private TextView tvCurrentTime, tvDailyVisits;
    private TextView tvTodayDate, tvActiveClasses, tvFinishedClasses;
    private ListView lvTodaySchedule;

    private DBHelper dbHelper;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ view
        tvTotalStudents = view.findViewById(R.id.tvTotalStudents);
        tvTotalTeachers = view.findViewById(R.id.tvTotalTeachers);
        tvTotalClasses = view.findViewById(R.id.tvTotalClasses);
        tvCurrentTime = view.findViewById(R.id.tvCurrentTime);
        tvDailyVisits = view.findViewById(R.id.tvDailyVisits);

        tvTodayDate = view.findViewById(R.id.tvTodayDate);
        tvActiveClasses = view.findViewById(R.id.tvActiveClasses);
        tvFinishedClasses = view.findViewById(R.id.tvFinishedClasses);
        lvTodaySchedule = view.findViewById(R.id.lvTodaySchedule);  // Đảm bảo tên này trùng với XML

        dbHelper = new DBHelper(getContext());

        // Cập nhật giờ hiện tại
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        tvCurrentTime.setText("Giờ hiện tại: " + currentTime);

        // Cập nhật lượt truy cập
        dbHelper.increaseVisitCount();
        int visitCount = dbHelper.getVisitCount();
        tvDailyVisits.setText("Số lượt truy cập hôm nay: " + visitCount);

        // Ngày hôm nay
        String currentDate = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN")).format(new Date());
        tvTodayDate.setText(currentDate);

        // Dữ liệu giả (có thể thay bằng dữ liệu thật)
        tvTotalStudents.setText("Tổng số học viên: 120");
        tvTotalTeachers.setText("Tổng số giáo viên: 10");
        tvTotalClasses.setText("Số lớp học: 6");

        tvActiveClasses.setText("4");
        tvFinishedClasses.setText("2");

        // Cập nhật lịch học hôm nay
        ArrayList<String> scheduleList = new ArrayList<>();
        scheduleList.add("08:00 - Tin học 10A1");
        scheduleList.add("10:00 - Vật lý 11B");
        scheduleList.add("13:30 - Hóa học 12C");

        // Dùng adapter để hiển thị lịch học
        SimpleScheduleAdapter adapter = new SimpleScheduleAdapter(requireContext(), scheduleList);
        lvTodaySchedule.setAdapter(adapter);
    }

    // Adapter nội bộ hiển thị danh sách lịch học hôm nay
    private static class SimpleScheduleAdapter extends ArrayAdapter<String> {
        public SimpleScheduleAdapter(Context context, ArrayList<String> schedules) {
            super(context, android.R.layout.simple_list_item_1, schedules);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            String schedule = getItem(position);
            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(schedule);

            return convertView;
        }
    }
}
