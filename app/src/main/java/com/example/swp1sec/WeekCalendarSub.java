package com.example.swp1sec;

public class WeekCalendarSub {
    private String title;
    private int division;//카테고리의 디비전
    private int Ndivision;//노멀 일정의 디비전
    private String color;
    private String date;
    private String time;
    private String enddate;
    private int id;

    private int star;
    private String memo;

    private int startyear;
    private int startmonth;
    private int starthour;
    private int startminute;
    private int endhour;
    private int endminute;

    private String endtime;

    private int startday;

    public int getNdivision() {
        return Ndivision;
    }

    public void setNdivision(int ndivision) {
        Ndivision = ndivision;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStartday() {
        return startday;
    }

    public void setStartday(int startday) {
        this.startday = startday;
    }

    public int getStartyear() {
        return startyear;
    }

    public void setStartyear(int startyear) {
        this.startyear = startyear;
    }

    public int getStartmonth() {
        return startmonth;
    }

    public void setStartmonth(int startmonth) {
        this.startmonth = startmonth;
    }

    public int getStarthour() {
        return starthour;
    }

    public void setStarthour(int starthour) {
        this.starthour = starthour;
    }

    public int getStartminute() {
        return startminute;
    }

    public void setStartminute(int startminute) {
        this.startminute = startminute;
    }

    public int getEndhour() {
        return endhour;
    }

    public void setEndhour(int endhour) {
        this.endhour = endhour;
    }

    public int getEndminute() {
        return endminute;
    }

    public void setEndminute(int endminute) {
        this.endminute = endminute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }




    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public WeekCalendarSub(String title) {
        this.title = title;
    }

    public WeekCalendarSub() {
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
