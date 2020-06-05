package com.example.swp1sec;

import com.example.library.WeekViewEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Event extends WeekViewEvent {
    private UUID mEventId;
    private String mTitle;
    private String mDescription;
    private String mPeople;
    private String mPlace;
    private int mColor;
    private int mDayOfMonth;

    private Date mStartEvent = new Date();
    private Date mEndEvent = new Date();


    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }


    public Date getEndEvent() {
        return mEndEvent;
    }


    public void setEndEvent(Calendar calendar) {
        mEndEvent = calendar.getTime();
    }

    public Date getStartEvent() {
        return mStartEvent;
    }


    public void setStartEvent(Calendar calendar) {
        mStartEvent = calendar.getTime();
    }



    public UUID getEventId() {
        return mEventId;
    }

    public void setEventId(UUID eventId) {
        mEventId = eventId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPeople() {
        return mPeople;
    }

    public void setPeople(String people) {
        mPeople = people;
    }



    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    @Override
    public int getColor() {
        return mColor;
    }

    @Override
    public void setColor(int color) {
        mColor = color;
    }

    public Event(){
        this(UUID.randomUUID());
    }

    public Event(UUID uuid){
        mEventId = uuid;
    }


    public Event(UUID id,String name){

        this.mEventId = id;
        this.mTitle = name;
    }

    public Event(UUID id, String name, Calendar startTime, Calendar endTime){

        setStartEvent(startTime);
        setEndEvent(endTime);

        this.mEventId = id;
        this.mTitle = name;
    }


}
