package com.example.bai_5_1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int[] images = {
            R.drawable.applepie,
            R.drawable.bananabread,
            R.drawable.cupcake,
            R.drawable.donut,
            R.drawable.eclair,
            R.drawable.froyo
    };

    String[] version = {"Android Apple Pie", "Android Banana Bread", "Android Cupcake", "Android Donut", "Android Eclair", "Android Froyo"};
    String[] versionNumber = {"1.0", "1.1", "1.5", "1.6", "2.0", "2.2"};

    ListView lView;
    ListAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lView = findViewById(R.id.androidList);
        lAdapter = new ListAdapter(MainActivity.this, version, versionNumber, images);
        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, version[i] + " " + versionNumber[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
