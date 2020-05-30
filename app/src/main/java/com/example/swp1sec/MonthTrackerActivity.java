package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthTrackerActivity extends AppCompatActivity {

    CalendarView calendarView;
    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private Intent intent;
    private TableRow tr_1, tr_2, tr_3, tr_4, tr_5, tr_6;
    private TextView txt_habitcount, txt_habitpercent, txt_dayfeedback, txt_monthname;
    private Spinner spin_habit;
    MutableLiveData<ArrayList<Object>> mCalendarList= new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthtracker);
        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist = findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus = findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);
        txt_habitcount = findViewById(R.id.txt_habitcount);
        txt_habitpercent = findViewById(R.id.txt_habitpercent);
        txt_dayfeedback = findViewById(R.id.txt_dayfeedback);
        txt_monthname = findViewById(R.id.txt_monthname);

        tr_1 = findViewById(R.id.tr_1);
        tr_2 = findViewById(R.id.tr_2);
        tr_3 = findViewById(R.id.tr_3);
        tr_4 = findViewById(R.id.tr_4);
        tr_5 = findViewById(R.id.tr_5);
        tr_6 = findViewById(R.id.tr_6);

        spin_habit = findViewById(R.id.spin_habit);

        ibtn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MonthTrackerActivity.this, CalendarView.class);
                startActivity(intent);
            }
        });
        ibtn_calenderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MonthTrackerActivity.this, CalendarListActivity.class);
                startActivity(intent);
            }
        });
        /*ibtn_calenderplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MonthTrackerActivity.this, CalendarPlusActivity.class);
                startActivity(intent);
            }
        });*/
        ibtn_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MonthTrackerActivity.this, DayTrackerActivity.class);
                startActivity(intent);
            }
        });
        /*ibtn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MonthTrackerActivity.this, StoreActivity.class);
                startActivity(intent);
            }
        });*/

        // 달력 생성



        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH)+1; //오늘이 몇월?
        int date = cal.get(Calendar.DATE); //오늘이 몇일?
        int dayOfweek = cal.get(Calendar.DAY_OF_WEEK); //오늘이 무슨 요일 ( 1:일, 2:월, ..., 7:토)
        int weekday = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH); //이번달에 몇번째 주인지
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //이 달의 마지막 날짜


        Calendar mDay = Calendar.getInstance();
        mDay.set(cal.get(Calendar.YEAR),month-1, 1);
        int startday = mDay.get(Calendar.DAY_OF_WEEK); //이 달의 첫번째 요일
        txt_monthname.setText(month + "월");
        txt_habitpercent.setText(Integer.toString(startday));
        //1주
        for(int i = 0; i<startday-1 ; i++){
            TextView text = (TextView) tr_1.getChildAt(i);
            text.setText(" ");
        }
        for(int i = 1; i< 9-startday ; i++){
            TextView text = (TextView) tr_1.getChildAt(startday-2+i);
            text.setText(Integer.toString(i));
        }
        //2주
        for(int i = 0 ; i< 7 ; i++){
            TextView text = (TextView) tr_2.getChildAt(i);
            text.setText(Integer.toString(9+i-startday));
        }
        //3주
        for(int i = 0 ; i< 7 ; i++){
            TextView text = (TextView) tr_3.getChildAt(i);
            text.setText(Integer.toString(16 +i-startday));
        }
        //4주
        for(int i = 0 ; i< 7 ; i++){
            TextView text = (TextView) tr_4.getChildAt(i);
            text.setText(Integer.toString(23 +i-startday));
        }
        //5주 - 6주
        if(36-startday<max){ //6주 있음
            for(int i = 0 ; i< 7 ; i++){
                TextView text = (TextView) tr_5.getChildAt(i);
                text.setText(Integer.toString(30 +i-startday));
            }
            for(int i = 0; i < max + startday - 36 ; i++){
                TextView text = (TextView) tr_6.getChildAt(i);
                text.setText(Integer.toString(37 +i-startday));
            }
            for(int i = max +startday - 36 ; i<7 ; i++){
                TextView text = (TextView) tr_6.getChildAt(i);
                text.setText(" ");
            }
        }
        else{
            for(int i = 0; i < max + startday - 29 ; i++){
                TextView text = (TextView) tr_5.getChildAt(i);
                text.setText(Integer.toString(30 +i-startday));
            }
            for(int i = max +startday - 29 ; i<7 ; i++){
                TextView text = (TextView) tr_5.getChildAt(i);
                text.setText(" ");
            }
            for(int i = 0 ; i<7 ; i++){
                TextView text = (TextView) tr_6.getChildAt(i);
                text.setText(" ");
            }
        }
        tr_2.getChildAt(1).setBackgroundResource(R.drawable.icon_circle);
        tr_2.getChildAt(2).setBackgroundResource(R.drawable.icon_circle);
    }



}