package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class cate_dataview extends AppCompatActivity {
    TextView title,proname,proemail,day,classstart,classends,lectureroom,day1,classstart1,classends1;
    int position;

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.category_dataview);

        //Initializing Views

        title =findViewById(R.id.dt_category_title);
        proname =findViewById(R.id.dt_pro_name);
        proemail =findViewById(R.id.dt_pro_email);
        day =findViewById(R.id.dt_day);
        classstart =findViewById(R.id.dt_class_start);
        classends =findViewById(R.id.dt_class_ends);
        lectureroom =findViewById(R.id.dt_lectureroom);
        day1 =findViewById(R.id.dt_day1);
        classstart1 =findViewById(R.id.dt_class_start1);
        classends1 =findViewById(R.id.dt_class_ends1);

        position = getIntent().getIntExtra("position",1);


        title.setText("제목: " +CalendarView.categoryArrayList.get(position).gettitle());
        proname.setText("교수님 성함: " +CalendarView.categoryArrayList.get(position).getpro_name());
        proemail.setText("교수님 이메일: " +CalendarView.categoryArrayList.get(position).getpro_email());
        day.setText("날짜: " +CalendarView.categoryArrayList.get(position).getday());
        classstart.setText(CalendarView.categoryArrayList.get(position).getclass_start());
        classends.setText(CalendarView.categoryArrayList.get(position).getclass_ends());
        day1.setText("날짜: " +CalendarView.categoryArrayList.get(position).getday1());
        classstart1.setText(CalendarView.categoryArrayList.get(position).getclass_start1());
        classends1.setText(CalendarView.categoryArrayList.get(position).getclass_ends1());
        lectureroom.setText("강의실: " +CalendarView.categoryArrayList.get(position).getlectureroom());

    }

    public  void dt_cate_cancle(View v){

        finish();
    }
    public  void dt_cate_OK(View v){

        finish();
    }
}
