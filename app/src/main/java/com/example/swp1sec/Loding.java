package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import java.util.Calendar;

public class Loding extends AppCompatActivity {
    private Intent intent;
    private int nweek;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        nweek = cal.get(Calendar.DAY_OF_WEEK); //요일 구하기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loding);
        ImageView iv = (ImageView)findViewById(R.id.iv);

        Glide.with(this).load(R.raw.loding1sec).into(iv);

        Handler handler = new Handler() {

            public void handleMessage(Message msg) {

                super.handleMessage(msg);

                if(PreferenceManager.getString(Loding.this,"email").length()==0){
                    intent = new Intent(Loding.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    if(PreferenceManager.getInt(Loding.this,"nweek")==nweek){ //오늘 접속한 적이 있음
                        intent = new Intent(Loding.this,CalendarView.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(nweek==2){ //오늘 접속한 적이 없고 월요일이다.
                        PreferenceManager.setInt(Loding.this, "nweek",nweek);
                        intent = new Intent(Loding.this,WeekRemind.class);
                        startActivity(intent);
                        finish();
                    }
                    else {//오늘 접속한 적이 없고 월요일이 아니다.
                        intent = new Intent(Loding.this,DayRemind.class);
                        PreferenceManager.setInt(Loding.this, "nweek",nweek);
                        startActivity(intent);
                        finish();
                    }
                }



                finish();

            }

        };

        handler.sendEmptyMessageDelayed(0, 7000); //7초후 화면전환

        // 액션바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

}
