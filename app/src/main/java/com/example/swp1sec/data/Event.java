package com.example.swp1sec.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RatingBar;

import java.util.Calendar;

/**
 * Created by Hugo Andrade on 25/03/2018.
 */

public class Event implements Parcelable {

    private String mID;
    private String mTitle;
    private Calendar mDate;
    private int mColor;
    private boolean isCompleted;
    //추가
    private  boolean mDay;
    private RatingBar mStar;

    private int mdivision;
    private String memo;
    private int star;

    public Event(){ }

    public Event(String id, String title, Calendar date, int color, boolean isCompleted) {
        mID = id;
        mTitle = title;
        mDate = date;
        mColor = color;
        this.isCompleted = isCompleted;


    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getMdivision() {
        return mdivision;
    }

    public void setMdivision(int mdivision) {
        this.mdivision = mdivision;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDate(Calendar mDate) {
        this.mDate = mDate;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public Calendar getDate() {
        return mDate;
    }

    public int getColor() {
        return mColor;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    //추가

    public boolean getDDay(){
        return  mDay;
    }

    public RatingBar getStar(){
        return mStar;
    }

    protected Event(Parcel in) {
        mID = in.readString();
        mTitle = in.readString();
        mColor = in.readInt();
        mDate = (Calendar) in.readSerializable();
        isCompleted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mTitle);
        dest.writeInt(mColor);
        dest.writeSerializable(mDate);
        dest.writeByte((byte) (isCompleted ? 1 : 0));
        //추가

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
