package com.example.portal3;

import static android.content.ContentValues.TAG;
import static com.example.portal3.LoginActivity.getEmail_typed;

import android.os.Bundle;
import android.util.Log;
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
import android.database.Cursor;

public class TeacherFragment extends Fragment {

    private ExpandableListView elv_class_schedule;
    private List<Map<String, String>> listGroup;
    private List<List<Map<String, String>>> listItem;
    private TextView today_date;
    private SimpleExpandableListAdapter adapter;
    private static String user_email;
    private Cursor teacher_info_cursor;
    private Cursor teacher_schedule_cursor;
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
        TextView teacher_name = view.findViewById(R.id.class_name);
        TextView teacher_id = view.findViewById(R.id.teacher_id);
        TextView teacher_email = view.findViewById(R.id.teacher_email);
        super.onViewCreated(view, savedInstanceState);
        today_date = view.findViewById(R.id.tv_today);
        String currentDate = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN")).format(new Date());
        today_date.setText(currentDate);
        // Read data from email
        user_email = getEmail_typed();
        DBHelper db = new DBHelper(requireContext());
        teacher_info_cursor = db.getTeacherInfoByMail(user_email);
        String user_id = "";
        if(teacher_info_cursor != null && teacher_info_cursor.moveToFirst()) {
            teacher_name.setText(teacher_name.getText().toString() + teacher_info_cursor.getString(teacher_info_cursor.getColumnIndexOrThrow("name")));
            teacher_id.setText(teacher_id.getText().toString() + user_id);
            teacher_email.setText(teacher_email.getText().toString() + user_email);
        }
        if(teacher_info_cursor != null && teacher_info_cursor.moveToFirst()) {
            user_id = teacher_info_cursor.getString(teacher_info_cursor.getColumnIndexOrThrow("_id"));
        }
        teacher_schedule_cursor = db.getScheduleByID(user_id);
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
            String class_name = null;
            if(teacher_schedule_cursor != null && teacher_schedule_cursor.moveToPosition(groupPosition)) {
                class_name = teacher_schedule_cursor.getString(teacher_schedule_cursor.getColumnIndexOrThrow("class"));
            }
            Fragment targetFragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.main_container);
            switch(item) {
                case "Bài tập":
                    targetFragment = new TeacherHomeworkFragment();
                    break;
                case "Danh sách lớp":
                    targetFragment = new StudentList();

                    Bundle bundle = new Bundle();
                    bundle.putString("class_name", class_name);
                    targetFragment.setArguments(bundle);
                    break;
                default:
                    break;
            }
            // Replace current fragment with targetFragment
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, targetFragment)  // your container id
                    .addToBackStack(null)
                    .commit();
            return true;
        }));
        initData(teacher_schedule_cursor);
    }

    private void initData(Cursor cursor) {
        //String[] class_teach = {"IELTS 6.5 (LỚP A)", "TOEIC 900 (LỚP B)"};
        /*String[][] function = {
                {"Danh sách lớp", "Bài tập", "Điểm số"},
                {"Danh sách lớp", "Bài tập", "Điểm số"}
        };*/

        ArrayList<String> class_teach = new ArrayList<>();
        List<List<String>> function = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                int i = 0;
                class_teach.add(cursor.getString(cursor.getColumnIndexOrThrow("class")));
                Log.i(TAG, class_teach.get(i));
                List<String> row = new ArrayList<>();
                row.add("Danh sách lớp");
                row.add("Bài tập");
                row.add("Điểm số");
                function.add(row);
                i += 1;
            } while(cursor.moveToNext());
        }


        for (int i = 0; i < class_teach.size(); i++) {
            Map<String, String> classMap = new HashMap<>();
            classMap.put("Class", class_teach.get(i));
            listGroup.add(classMap);

            List<Map<String, String>> childList = new ArrayList<>();
            for (String func : function.get(i)) {
                Map<String, String> funcMap = new HashMap<>();
                funcMap.put("Function", func);
                childList.add(funcMap);
            }
            listItem.add(childList);
        }

        adapter.notifyDataSetChanged();
    }
}