package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthTrackerActivity extends AppCompatActivity{

    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private Intent intent;
    private TableRow tr_1, tr_2, tr_3, tr_4, tr_5, tr_6;
    private TextView txt_monthhabitcheck, txt_monthfeedback, txt_monthname;
    private Spinner spin_habit;

    //스피너
    private ArrayList<String> monthHabitNameArrayList;
    private ArrayAdapter<String> adapter;
    private ArrayList<ArrayList<String>> grouptrackercheck;
    private ArrayList<String> trackercheck;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthtracker);
        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist = findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus = findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);

        txt_monthname = findViewById(R.id.txt_monthname);
        txt_monthhabitcheck = findViewById(R.id.txt_monthhabitcheck);
        txt_monthfeedback = findViewById(R.id.txt_monthfeedback);

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
        final int date = cal.get(Calendar.DATE); //오늘이 몇일?
        int dayOfweek = cal.get(Calendar.DAY_OF_WEEK); //오늘이 무슨 요일 ( 1:일, 2:월, ..., 7:토)
        int weekday = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH); //이번달에 몇번째 주인지
        final int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //이 달의 마지막 날짜


        Calendar mDay = Calendar.getInstance();
        mDay.set(cal.get(Calendar.YEAR),month-1, 1);
        final int startday = mDay.get(Calendar.DAY_OF_WEEK); //이 달의 첫번째 요일
        txt_monthname.setText(month + "월");

        //1주
        for(int i = 0; i<startday-1 ; i++){
            TextView text = (TextView) tr_1.getChildAt(i);
            text.setText(null);
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
                text.setText(null);
            }
        }
        else{
            for(int i = 0; i < max + startday - 29 ; i++){
                TextView text = (TextView) tr_5.getChildAt(i);
                text.setText(Integer.toString(30 +i-startday));
            }
            for(int i = max +startday - 29 ; i<7 ; i++){
                TextView text = (TextView) tr_5.getChildAt(i);
                text.setText(null);
            }
            for(int i = 0 ; i<7 ; i++){
                TextView text = (TextView) tr_6.getChildAt(i);
                text.setText(null);
            }
        }
        monthHabitNameArrayList =  new ArrayList<>();
        grouptrackercheck = new ArrayList<ArrayList<String>>();
        txt_monthfeedback.setText(Integer.toString(startday));
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,monthHabitNameArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_habit.setAdapter(adapter);

        grouptrackercheck.clear();
        monthHabitNameArrayList.clear();
        adapter.notifyDataSetChanged();

        getData();

        spin_habit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                count = 0;
                for(int i =0;i<7;i++){
                    tr_1.getChildAt(i).setBackgroundResource(R.drawable.circle_trans);
                    tr_2.getChildAt(i).setBackgroundResource(R.drawable.circle_trans);
                    tr_3.getChildAt(i).setBackgroundResource(R.drawable.circle_trans);
                    tr_4.getChildAt(i).setBackgroundResource(R.drawable.circle_trans);
                    tr_5.getChildAt(i).setBackgroundResource(R.drawable.circle_trans);
                    tr_6.getChildAt(i).setBackgroundResource(R.drawable.circle_trans);
                }
                for(int i = 1; i< 9-startday ; i++){ //첫째주
                    TextView text = (TextView) tr_1.getChildAt(startday-2+i);
                    if(grouptrackercheck.get(position).get(i - 1).equals("1")){
                        tr_1.getChildAt(startday-2+i).setBackgroundResource(R.drawable.circle_yellow);
                        count++;
                    }
                }
                for(int i = 9 - startday; i< 16 - startday ; i++){ //둘째주
                    TextView text = (TextView) tr_2.getChildAt(i - 9 + startday);
                    if(grouptrackercheck.get(position).get(i - 1).equals("1")){
                        tr_2.getChildAt(i - 9 + startday).setBackgroundResource(R.drawable.circle_yellow);
                        count++;
                    }
                }
                for(int i = 16 - startday; i< 23 - startday ; i++){ //셋째주
                    TextView text = (TextView) tr_3.getChildAt(i - 16 + startday);
                    if(grouptrackercheck.get(position).get(i - 1).equals("1")){
                        tr_3.getChildAt(i - 16 + startday).setBackgroundResource(R.drawable.circle_yellow);
                        count++;
                    }
                }
                for(int i = 23 - startday; i< 30 - startday ; i++){ //넷째주
                    TextView text = (TextView) tr_4.getChildAt(i - 23 + startday);
                    if(grouptrackercheck.get(position).get(i - 1).equals("1")){
                        tr_4.getChildAt(i - 23 + startday).setBackgroundResource(R.drawable.circle_yellow);
                        count++;
                    }
                }
                for(int i = 30 - startday; i< max+1 ; i++){ //다섯째주
                    TextView text = (TextView) tr_5.getChildAt(i - 30 + startday);
                    if(grouptrackercheck.get(position).get(i - 1).equals("1")){
                        tr_5.getChildAt(i - 30 + startday).setBackgroundResource(R.drawable.circle_yellow);
                        count++;
                    }
                }
                txt_monthhabitcheck.setText(max + "일중 "+count+"일 완료했습니다.");
                switch (count*10/date){
                    case 0: case 1:
                        txt_monthfeedback.setText("분발하세요!");
                        break;
                    case 2: case 3:
                        txt_monthfeedback.setText("조금만 더 힘내볼까요");
                        break;
                    case 4: case 5:
                        txt_monthfeedback.setText("반은 했어요!");
                        break;
                    case 6: case 7:
                        txt_monthfeedback.setText("잘 할 수 있어요! 화이팅! ");
                        break;
                    case 8: case 9:
                        txt_monthfeedback.setText("고지가 코앞이에요! 조금더 수행해볼까요?");
                        break;
                    default:
                        txt_monthfeedback.setText("완벽합니다! 수고 많으셨어요!");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void getData(){
        String email = PreferenceManager.getString(MonthTrackerActivity.this, "email");
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        JSONArray data = jsonObject.getJSONArray("data");

                        for(int i=0;i<data.length();i++){
                            JSONObject item = data.getJSONObject(i);
                            String title = item.getString("title");
                            monthHabitNameArrayList.add(title);
                            JSONObject tracker = item.getJSONObject("tracker");
                            trackercheck = new ArrayList<String>();
                            trackercheck.clear();
                            trackercheck.add(tracker.getString("1일"));
                            trackercheck.add(tracker.getString("2일"));
                            trackercheck.add(tracker.getString("3일"));
                            trackercheck.add(tracker.getString("4일"));
                            trackercheck.add(tracker.getString("5일"));
                            trackercheck.add(tracker.getString("6일"));
                            trackercheck.add(tracker.getString("7일"));
                            trackercheck.add(tracker.getString("8일"));
                            trackercheck.add(tracker.getString("9일"));
                            trackercheck.add(tracker.getString("10일"));
                            trackercheck.add(tracker.getString("11일"));
                            trackercheck.add(tracker.getString("12일"));
                            trackercheck.add(tracker.getString("13일"));
                            trackercheck.add(tracker.getString("14일"));
                            trackercheck.add(tracker.getString("15일"));
                            trackercheck.add(tracker.getString("16일"));
                            trackercheck.add(tracker.getString("17일"));
                            trackercheck.add(tracker.getString("18일"));
                            trackercheck.add(tracker.getString("19일"));
                            trackercheck.add(tracker.getString("20일"));
                            trackercheck.add(tracker.getString("21일"));
                            trackercheck.add(tracker.getString("22일"));
                            trackercheck.add(tracker.getString("23일"));
                            trackercheck.add(tracker.getString("24일"));
                            trackercheck.add(tracker.getString("25일"));
                            trackercheck.add(tracker.getString("26일"));
                            trackercheck.add(tracker.getString("27일"));
                            trackercheck.add(tracker.getString("28일"));
                            trackercheck.add(tracker.getString("29일"));
                            trackercheck.add(tracker.getString("30일"));
                            trackercheck.add(tracker.getString("31일"));
                            grouptrackercheck.add(trackercheck);
                            adapter.notifyDataSetChanged();
                        }

                    }
                    else{ //실패
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MonthTrackerRequest monthTrackerRequest=new MonthTrackerRequest(email,responseListener);
        RequestQueue queue= Volley.newRequestQueue(MonthTrackerActivity.this);
        queue.add(monthTrackerRequest);
    }

}