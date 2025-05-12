package com.example.sinhvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private Context context;
    private List<Student> studentList;

    public StudentAdapter(Context context, List<Student> students) {
        super(context, 0, students);
        this.context = context;
        this.studentList = students;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = studentList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        }

        ImageView imageAvatar = convertView.findViewById(R.id.imageAvatar);
        TextView textName = convertView.findViewById(R.id.textName);
        TextView textMSSV = convertView.findViewById(R.id.textMSSV);

        textName.setText(student.getName());
        textMSSV.setText(student.getMssv());

        // Load ảnh từ đường dẫn (nếu có) hoặc để ảnh mặc định
        if (student.getAvatarPath() != null && !student.getAvatarPath().isEmpty()) {
            imageAvatar.setImageURI(android.net.Uri.parse(student.getAvatarPath()));
        } else {
            imageAvatar.setImageResource(R.drawable.ic_launcher_foreground); // Ảnh mặc định
        }

        return convertView;
    }
}

