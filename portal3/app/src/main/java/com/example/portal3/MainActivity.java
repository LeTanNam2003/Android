package com.example.portal3;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up NavigationView (Navigation Drawer)
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Show the DashboardFragment by default when the app starts
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new DashboardFragment()) // Start with DashboardFragment
                    .commit();
            navigationView.setCheckedItem(R.id.nav_dashboard); // Set "Dashboard" as checked item
        }

        // Add a hamburger icon to open the drawer
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_view);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment selectedFragment = null;

        // Handle item selection in the navigation drawer
        int id = item.getItemId(); // Dùng biến thay vì switch-case để tránh lỗi constant

        if (id == R.id.nav_dashboard) {
            selectedFragment = new DashboardFragment();
        } else if (id == R.id.nav_students) {
            selectedFragment = new StudentFragment();
        } else if (id == R.id.nav_teachers) {
            selectedFragment = new TeacherFragment();
        }


        // Replace the fragment in the container
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, selectedFragment)
                    .commit();
        }

        // Close the drawer after item is selected
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
