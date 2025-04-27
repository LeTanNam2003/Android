package com.example.bai_5_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitArrayAdapter extends ArrayAdapter<Fruit> {

    private Context mContext;
    private List<Fruit> fruitList;

    public FruitArrayAdapter(Context context, List<Fruit> list) {
        super(context, 0, list);
        mContext = context;
        fruitList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = fruitList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_fruit, parent, false);
        }

        ImageView fruitImage = convertView.findViewById(R.id.fruitImage);
        TextView fruitName = convertView.findViewById(R.id.fruitName);
        TextView fruitCalories = convertView.findViewById(R.id.fruitCalories);

        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getFruitName());
        fruitCalories.setText(fruit.getCalories());

        return convertView;
    }
}
