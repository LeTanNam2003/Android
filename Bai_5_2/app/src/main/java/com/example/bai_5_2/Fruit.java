package com.example.bai_5_2;

public class Fruit {
    private int imageId;
    private String fruitName;
    private String calories;

    public Fruit(int imageId, String fruitName, String calories) {
        this.imageId = imageId;
        this.fruitName = fruitName;
        this.calories = calories;
    }

    public int getImageId() {
        return imageId;
    }

    public String getFruitName() {
        return fruitName;
    }

    public String getCalories() {
        return calories;
    }
}
