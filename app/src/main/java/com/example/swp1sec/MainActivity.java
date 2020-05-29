package com.example.swp1sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton ibtn_calender,ibtn_calenderlist,ibtn_calenderplus,ibtn_tracker,ibtn_store;
    private Button btn_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist=findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus=findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);
        btn_logout = findViewById(R.id.btn_logout);

        ibtn_calenderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CalendarListActivity.class);
                startActivity(intent);
            }
        });
        ibtn_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, DayTrackerActivity.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                PreferenceManager.clear(MainActivity.this);

                startActivity(intent);
            }
        });
    }
}
