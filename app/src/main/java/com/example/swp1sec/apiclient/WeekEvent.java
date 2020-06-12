package com.example.swp1sec.apiclient;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.library.WeekViewEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * An event model that was built for automatic serialization from json to object.
 * Created by Raquib-ul-Alam Kanak on 1/3/16.
 * Website: http://alamkanak.github.io
 */
public class WeekEvent implements Parcelable {

    @Expose @SerializedName("name")
    private String mName;
    @Expose @SerializedName("dayOfMonth")
    private int mDayOfMonth;
    @Expose @SerializedName("startTime")
    private String mStartTime;
    @Expose @SerializedName("endTime")
    private String mEndTime;
    @Expose @SerializedName("color")
    private String mColor;

    private String mStartDate;
    private String mEndDate;
    private String mMemo;
    private String mStar;

    protected WeekEvent(Parcel in) {
        mName = in.readString();
        mDayOfMonth = in.readInt();
        mStartTime = in.readString();
        mEndTime = in.readString();
        mColor = in.readString();
        mStartDate = in.readString();
        mEndDate = in.readString();
        mMemo = in.readString();
        mStar = in.readString();
    }

    public static final Creator<WeekEvent> CREATOR = new Creator<WeekEvent>() {
        @Override
        public WeekEvent createFromParcel(Parcel in) {
            return new WeekEvent(in);
        }

        @Override
        public WeekEvent[] newArray(int size) {
            return new WeekEvent[size];
        }
    };

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public String getmMemo() {
        return mMemo;
    }

    public void setmMemo(String mMemo) {
        this.mMemo = mMemo;
    }

    public String getmStar() {
        return mStar;
    }

    public void setmStar(String mStar) {
        this.mStar = mStar;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.mDayOfMonth = dayOfMonth;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        this.mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        this.mEndTime = endTime;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    @SuppressLint("SimpleDateFormat")
    public WeekViewEvent toWeekViewEvent(){

        // Parse time.
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date start = new Date();
        Date end = new Date();
        try {
            start = sdf.parse(getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            end = sdf.parse(getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Initialize start and end time.
        Calendar now = Calendar.getInstance();
        Calendar startTime = (Calendar) now.clone();
        startTime.setTimeInMillis(start.getTime());
        startTime.set(Calendar.YEAR, now.get(Calendar.YEAR));
        startTime.set(Calendar.MONTH, now.get(Calendar.MONTH));
        startTime.set(Calendar.DAY_OF_MONTH, getDayOfMonth());
        Calendar endTime = (Calendar) startTime.clone();
        endTime.setTimeInMillis(end.getTime());
        endTime.set(Calendar.YEAR, startTime.get(Calendar.YEAR));
        endTime.set(Calendar.MONTH, startTime.get(Calendar.MONTH));
        endTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH));

        // Create an week view event.
        WeekViewEvent weekViewEvent = new WeekViewEvent();
        weekViewEvent.setName(getName());
        weekViewEvent.setStartTime(startTime);
        weekViewEvent.setEndTime(endTime);
        weekViewEvent.setColor(Color.parseColor(getColor()));

        return weekViewEvent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mDayOfMonth);
        dest.writeString(mStartTime);
        dest.writeString(mEndTime);
        dest.writeString(mColor);
        dest.writeString(mStartDate);
        dest.writeString(mEndDate);
        dest.writeString(mMemo);
        dest.writeString(mStar);

    }
}
