package com.example.bai_5_2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.ListView; // cần import dòng này
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    private ListView fruitListView;
    private FruitArrayAdapter adapter;
    private List<Fruit> fruitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fruitListView = findViewById(R.id.fruitListView);
        fruitList = new ArrayList<>();
        fruitList.add(new Fruit(R.drawable.apple, "Apple", "52 kcal"));
        fruitList.add(new Fruit(R.drawable.banana, "Banana", "89 kcal"));
        fruitList.add(new Fruit(R.drawable.cherry, "Cherry", "50 kcal"));
        // Thêm các mục khác tùy ý

        adapter = new FruitArrayAdapter(this, fruitList);
        fruitListView.setAdapter(adapter);

        fruitListView.setOnItemClickListener((parent, view, position, id) -> {
            Fruit selectedFruit = fruitList.get(position);
            Toast.makeText(MainActivity.this,
                    selectedFruit.getFruitName() + " - " + selectedFruit.getCalories(),
                    Toast.LENGTH_SHORT).show();
        });
    }
}
