package com.example.swp1sec;

public class Data {

    private String title;
    private String content;
    private String Coin;
    private int m_resId;
    private int c_resId;
    private int id;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getm_ResId() {
        return m_resId;
    }
    public void setm_ResId(int m_resId) {
        this.m_resId = m_resId;
    }

    public int getc_ResId() {
        return c_resId;
    }
    public void setc_ResId(int c_resId) {
        this.c_resId = c_resId;
    }

    public String getCoin() {
        return Coin;
    }
    public void setCoin(String Coin) { this.Coin = Coin; }
}