package com.example.swp1sec;

public class CategoryItem {
    private String title;
    private int division;
    private String color;

    public CategoryItem(String title) {
        this.title = title;
    }

    public CategoryItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
