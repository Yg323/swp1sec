package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class cate_dataview extends AppCompatActivity {
    TextView tvid,tvname,tvemail,tvcontact;
    int position;

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.category_dataview);

        //Initializing Views

        tvid =findViewById(R.id.cate_id);

        position = getIntent().getIntExtra("position",1);


        tvid.setText("cate_id" +CalendarView.categoryArrayList.get(position).gettitle());

    }
}
