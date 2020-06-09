package com.example.swp1sec;

public class badge_habit_data {
    private String title;
    private static int badge_habit;

    //static으로 선언
    public badge_habit_data(String title) {
        this.title = title;
    }

    public void  setbadge_habit_title(String title) {
        this.title = title;
    }

    public String getbadge_habit_title() {
        return title;
    }

    public void setbadge_habit(int badge_habit) {
        this.badge_habit= badge_habit;
    }

    public static int getbadge_habit() {
        return badge_habit;
    }
}