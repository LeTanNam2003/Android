package com.example.portal3;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private TextView tvTotalStudents, tvTotalTeachers, tvTotalClasses;
    private TextView tvCurrentTime, tvDailyVisits;
    private TextView tvTodayDate, tvActiveClasses, tvFinishedClasses;
    private RecyclerView rvTodaySchedule;

    private DBHelper dbHelper;
    private final Handler handler = new Handler();

    public DashboardFragment() {
        // Required empty public constructor
    }

    private final Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            tvCurrentTime.setText("Giờ hiện tại: " + currentTime);
            handler.postDelayed(this, 1000); // update every second
        }
    };

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
        rvTodaySchedule = view.findViewById(R.id.rv_class);  // RecyclerView thay cho ListView

        dbHelper = new DBHelper(getContext());

        // Cập nhật giờ hiện tại
        handler.post(updateTimeRunnable);

        // Cập nhật lượt truy cập
        dbHelper.increaseVisitCount();
        int visitCount = dbHelper.getVisitCount();
        tvDailyVisits.setText("Số lượt truy cập hôm nay: " + visitCount);

        // Ngày hôm nay
        String currentDate = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN")).format(new Date());
        tvTodayDate.setText(currentDate);

        // Đọc dữ liệu từ CSV
        List<String[]> csvData = CSVHelper.readCSV(requireContext(), "data.csv");
        HashMap<String, String> dataMap = new HashMap<>();
        ArrayList<String> scheduleList = new ArrayList<>();

        for (String[] row : csvData) {
            if (row.length >= 2) {
                if (row[0].equals("schedule")) {
                    scheduleList.add(row[1]);
                } else {
                    dataMap.put(row[0], row[1]);
                }
            }
        }

        // Cập nhật UI với dữ liệu từ CSV
        tvTotalStudents.setText("Tổng số học viên: " + dataMap.get("students"));
        tvTotalTeachers.setText("Tổng số giáo viên: " + dataMap.get("teachers"));
        tvTotalClasses.setText("Số lớp học: " + dataMap.get("classes"));
        tvActiveClasses.setText(dataMap.get("active_classes"));
        tvFinishedClasses.setText(dataMap.get("finished_classes"));

        // Thiết lập RecyclerView
        rvTodaySchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTodaySchedule.setAdapter(new ClassScheduleAdapter(scheduleList));
    }
}
