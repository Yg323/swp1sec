package com.example.swp1sec;

public class Todo {
    private String title;
    private String category_title;
    private int performance;

    public Todo(String title) {
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public int getPerformance() { return performance; }

    public void setPerformance(int performance) { this.performance = performance; }

    public String getCategory_title() { return category_title; }

    public void setCategory_title(String category_title) { this.category_title = category_title; }
}
