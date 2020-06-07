package com.example.swp1sec;

public class category_title_data {
    private String cate_title;
    private String id;
    private String pro_name;
    private String pro_email;
    private String day;
    private String class_start;
    private String class_ends;
    private String lectureroom;
    private int division;
    private String color;
    private String day1;
    private String class_start1;
    private String class_ends1;





    public category_title_data(String id,String cate_title,String pro_name,String pro_email,String day,String class_start,String class_ends,String lectureroom,int division,String color,String day1,String class_start1,String class_ends1) {
        this.cate_title = cate_title;
        this.id = id;
        this.pro_name = pro_name;
        this.pro_email = pro_email;
        this.day = day;
        this.class_start = class_start;
        this.class_ends = class_ends;
        this.lectureroom = lectureroom;
        this.division = division;
        this.color = color;
        this.day1 = day1;
        this.class_start1 = class_start1;
        this.class_ends1 = class_ends1;


    }

    public void settitle(String cate_title) { this.cate_title = cate_title; }
    public String gettitle() {
        return cate_title;
    }

    public void setid(String id) { this.id = id; }
    public String getid() {
        return id;
    }

    public void setpro_name(String pro_name) { this.pro_name = pro_name; }
    public String getpro_name() {
        return pro_name;
    }

    public void setpro_email(String pro_email) { this.pro_email = pro_email; }
    public String getpro_email() {
        return pro_email;
    }

    public void setday(String day) { this.day = day; }
    public String getday() {
        return day;
    }

    public void setclass_start(String class_start) { this.class_start = class_start; }
    public String getclass_start() {
        return class_start;
    }

    public void setclass_ends(String class_ends) { this.class_ends = class_ends; }
    public String getclass_ends() {
        return class_ends;
    }

    public void setlectureroom(String lectureroom) { this.lectureroom = lectureroom; }
    public String getlectureroom() {
        return lectureroom;
    }

    public void setdivision(int division) { this.division = division; }
    public int getdivision() {
        return division;
    }

    public void setcolor(String color) { this.color = color; }
    public String getcolor() {
        return color;
    }

    public void setday1(String day) { this.day1 = day1; }
    public String getday1() {
        return day1;
    }

    public void setclass_start1(String class_start1) { this.class_start1 = class_start1; }
    public String getclass_start1() {
        return class_start1;
    }

    public void setclass_ends1(String class_ends1) { this.class_ends1 = class_ends1; }
    public String getclass_ends1() {
        return class_ends1;
    }
}
