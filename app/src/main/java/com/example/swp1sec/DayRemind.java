package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DayRemind extends AppCompatActivity {
    private Intent intent;
    private Button btn_redayclose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayremind);
        btn_redayclose = findViewById(R.id.btn_redayclose);
        btn_redayclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DayRemind.this,CalendarView.class);
                startActivity(intent);
            }
        });
    }
}
