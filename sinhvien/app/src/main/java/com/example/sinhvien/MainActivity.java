package com.example.sinhvien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewStudents;
    private StudentAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Thêm sinh viên mẫu vào database nếu danh sách trống
        if (dbHelper.getAllStudents().isEmpty()) {
            addSampleStudents();
        }

        // Load danh sách sinh viên
        studentList = dbHelper.getAllStudents();

        // Khởi tạo Adapter
        adapter = new StudentAdapter(this, studentList);

        // Gắn adapter cho ListView
        listViewStudents = findViewById(R.id.listViewStudents);
        listViewStudents.setAdapter(adapter);

        // Xử lý sự kiện click vào item trong ListView
        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                // Lấy thông tin sinh viên khi click vào item
                Student clickedStudent = studentList.get(position);

                // Chuyển sang DetailActivity với thông tin sinh viên đã chọn
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", clickedStudent.getName());
                intent.putExtra("mssv", clickedStudent.getMssv());
                intent.putExtra("avatarPath", clickedStudent.getAvatarPath());
                startActivity(intent);
            }
        });
    }

    // Thêm một số sinh viên mẫu vào cơ sở dữ liệu
    private void addSampleStudents() {
        dbHelper.addStudent(new Student("Nguyễn Văn A", "123456", "drawable/img1"));
        dbHelper.addStudent(new Student("Trần Thị B", "654321", "drawable/img2"));
        dbHelper.addStudent(new Student("Lê Thị C", "987654", "drawable/img3"));
        dbHelper.addStudent(new Student("Phạm Quang D", "246810", "drawable/img4"));
        dbHelper.addStudent(new Student("Nguyễn Minh E", "112233", "drawable/img5"));
        dbHelper.addStudent(new Student("Lê Hữu F", "445566", "drawable/img6"));
        dbHelper.addStudent(new Student("Trần Hương G", "778899", "drawable/img7"));
        dbHelper.addStudent(new Student("Phan Tùng H", "223344", "drawable/img8"));
        dbHelper.addStudent(new Student("Ngô Văn I", "556677", "drawable/img9"));
        dbHelper.addStudent(new Student("Vũ Bảo J", "998877", "drawable/img10"));
    }
}
