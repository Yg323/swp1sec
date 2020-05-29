package com.example.swp1sec;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HabitList extends AppCompatActivity {
    private String title;
    private int performance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitlist_item);

    }
    public HabitList(String title) {
        this.title = title;
    }

    public String getHabitTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }


}
