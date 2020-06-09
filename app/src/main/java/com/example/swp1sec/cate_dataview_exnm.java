package com.example.swp1sec;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class cate_dataview_exnm extends AppCompatActivity {
    TextView title;
    int position;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.cate_dataview_exnm);

        title = findViewById(R.id.dt_category_title_exnm);
        position = getIntent().getIntExtra("position", 1);
        title.setText("제목: " + CalendarView.categoryArrayList.get(position).gettitle());


    }

    public void dt_cate_cancle(View v) {

        finish();
    }

    public void dt_cate_OK(View v) {

        finish();
    }
}
