package com.example.swp1sec;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.util.Calendar;
import java.util.HashSet;

public class MonthTrackerActivity extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthtracker);
        calendarView = findViewById(R.id.cosmo_calendar);

        //주 시작 요일 설정
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        // 달력 가로 전환 ( 세로전환은 1 )
        calendarView.setCalendarOrientation(0);

        calendarView.setWeekendDays(new HashSet(){{
            add(Calendar.SATURDAY);
            add(Calendar.SUNDAY);
        }});
        //test

        //선택하지 못하게 함
        calendarView.setSelectionType(SelectionType.RANGE);


    }
}