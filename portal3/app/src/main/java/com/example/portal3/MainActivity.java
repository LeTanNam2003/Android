package com.example.portal3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_STUDENT_LOGGED_IN = "student_logged_in";
    private static final String KEY_TEACHER_LOGGED_IN = "teacher_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getBooleanExtra("open_student_fragment", false)) {
                setStudentLoggedIn(true);

                // Truyền dữ liệu sinh viên sang Fragment
                StudentFragment fragment = new StudentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", intent.getStringExtra("student_name"));
                bundle.putString("email", intent.getStringExtra("student_email"));
                bundle.putString("id", intent.getStringExtra("student_id"));
                fragment.setArguments(bundle);

                replaceFragment(fragment);
                navigationView.setCheckedItem(R.id.nav_students);
                return;
            } else if (intent.getBooleanExtra("open_teacher_fragment", false)) {
                setTeacherLoggedIn(true);

                // Truyền dữ liệu giáo viên sang Fragment
                TeacherFragment fragment = new TeacherFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", intent.getStringExtra("teacher_name"));
                bundle.putString("email", intent.getStringExtra("teacher_email"));
                bundle.putString("id", intent.getStringExtra("teacher_id"));
                fragment.setArguments(bundle);

                replaceFragment(fragment);
                navigationView.setCheckedItem(R.id.nav_teachers);
                return;
            }
        }

        // Mặc định mở Dashboard nếu không có Intent
        if (savedInstanceState == null) {
            replaceFragment(new DashboardFragment());
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }

        // Mở navigation drawer
        toolbar.setNavigationIcon(R.drawable.ic_menu_hamburger_scaled);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            replaceFragment(new DashboardFragment());
        } else if (id == R.id.nav_students) {
            if (isStudentLoggedIn()) {
                replaceFragment(new StudentFragment()); // Có thể gán lại bundle nếu cần
            } else {
                Intent intent = new Intent(this, StudentAuthActivity.class);
                startActivity(intent);
                finish();
            }
        } else if (id == R.id.nav_teachers) {
            if (isTeacherLoggedIn()) {
                replaceFragment(new TeacherFragment()); // Có thể gán lại bundle nếu cần
            } else {
                Intent intent = new Intent(this, TeacherAuthActivity.class);
                startActivity(intent);
                finish();
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    private boolean isStudentLoggedIn() {
        return sharedPreferences.getBoolean(KEY_STUDENT_LOGGED_IN, false);
    }

    private boolean isTeacherLoggedIn() {
        return sharedPreferences.getBoolean(KEY_TEACHER_LOGGED_IN, false);
    }

    public void setStudentLoggedIn(boolean loggedIn) {
        sharedPreferences.edit().putBoolean(KEY_STUDENT_LOGGED_IN, loggedIn).apply();
    }

    public void setTeacherLoggedIn(boolean loggedIn) {
        sharedPreferences.edit().putBoolean(KEY_TEACHER_LOGGED_IN, loggedIn).apply();
        sharedPreferences.edit().putBoolean(KEY_TEACHER_LOGGED_IN, loggedIn).apply();
    }

    public void logoutStudent() {
        sharedPreferences.edit().putBoolean(KEY_STUDENT_LOGGED_IN, false).apply();
    }

    public void logoutTeacher() {
        sharedPreferences.edit().putBoolean(KEY_TEACHER_LOGGED_IN, false).apply();
    }
}
