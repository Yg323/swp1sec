package com.example.swp1sec;

public class cal_title_data {
    private String cal_title;
    private String usr_email;
    private  int performance;
    private String id;




    public cal_title_data(String cal_title,String id) {
        this.cal_title = cal_title;
        this.id=id;
    }

    public void settitle(String cal_title) {
        this.cal_title = cal_title;
    }

    public String gettitle() {
        return cal_title;
    }

    public void setusr_email(String cal_title) {
        this.usr_email = usr_email;
    }

    public String getusr_email() {
        return usr_email;
    }

    public void setperformance(int performance) {
        this.performance = performance;
    }

    public  int getperformance() {
        return performance;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getid() {
        return id;
    }

}
