package com.example.swp1sec;

public class DayHabit {
    private String title;
    int performance;

    public DayHabit(String dayHabit) {
        this.title = dayHabit;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public int getPerformance() { return performance; }

    public void setPerformance(int performance) { this.performance = performance; }
}
