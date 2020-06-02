package com.example.swp1sec;

public class badge_todo_data {
    private String title;
    private int badge_todo;


    public badge_todo_data(String title) {
        this.title = title;
    }

    public void  setbadge_todotitle(String title) {
        this.title = title;
    }

    public String getbadge_todottitle() {
        return title;
    }

    public void setbadge_todo(int badge_todo) {
        this.badge_todo = badge_todo;
    }

    public int getbadge_todo() {
        return badge_todo;
    }
}
