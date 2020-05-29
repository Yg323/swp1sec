package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WeekRemind extends AppCompatActivity {
    private Intent intent;
    private Button btn_reweekclose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekremind);
        btn_reweekclose = findViewById(R.id.btn_reweekclose);

        btn_reweekclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WeekRemind.this,DayRemind.class);
                startActivity(intent);
            }
        });
    }
}
