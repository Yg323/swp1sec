package com.example.swp1sec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swp1sec.data.Event;
import com.example.swp1sec.uihelpers.CalendarDialog;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarView extends AppCompatActivity {

    private final static int CREATE_EVENT_REQUEST_CODE = 100;
    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private String[] mShortMonths;
    private com.example.mylibrary.CalendarView mCalendarView;
    private CalendarDialog mCalendarDialog;
    private Intent intent;
    private List<Event> mEventList = new ArrayList<>();

    public static Intent makeIntent(Context context) {
        return new Intent(context, CalendarView.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShortMonths = new DateFormatSymbols().getShortMonths();


        initializeUI();

    }

    private void initializeUI() {

        setContentView(R.layout.activity_calendar_view);

        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist = findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus = findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);
        //final Toolbar toolbar = findViewById(R.id.toolbar);

        //final TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        //setSupportActionBar(toolbar);

        mCalendarView = findViewById(R.id.calendarView);
        //상단 툴바에 뜨는 달(Title)이랑 년도(subtitle) 설정 움직일때 변경되는 것
        mCalendarView.setOnMonthChangedListener(new com.example.mylibrary.CalendarView.OnMonthChangedListener() {
            @Override // 여기에 int day 추가했음
            public void onMonthChanged(int month, int year) {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(year +"."+(month+1));//mShortMonths[month]
                    //getSupportActionBar().setSubtitle(Integer.toString(year)); //위에 년이랑 달을 합쳐서 더이상 필요x
                }
            }
        });

        //캘린더를 선택했을 때 size가 제로가 아니다 즉, 일정이 있으면 다이얼로그를 표시하고 없으면 바로 일정 추가창을 띄운다.

        ibtn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CalendarView.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ibtn_calenderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CalendarView.this, CalendarListActivity.class);
                startActivity(intent);
            }
        });
        /*ibtn_calenderplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(DayTrackerActivity.this, CalendarPlusActivity.class);
                startActivity(intent);
            }
        });*/
        ibtn_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CalendarView.this, DayTrackerActivity.class);
                startActivity(intent);
            }
        });
        /*ibtn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(DayTrackerActivity.this, StoreActivity.class);
                startActivity(intent);
            }
        });*/

        //상단 액션바와 연관 움직일때x 초기화면을 말함
        if (getSupportActionBar() != null) {
            // int day = mCalendarView.getCurrentDate().get(Calendar.DATE);
            int month = mCalendarView.getCurrentDate().get(Calendar.MONTH);
            int year = mCalendarView.getCurrentDate().get(Calendar.YEAR);
            String a = Integer.toString(year);
            String b = Integer.toString(month);

            getSupportActionBar().setTitle(year + "." + (month+1)); // //mShortMonths[month] 여기에 +day 를 추가
            //getSupportActionBar().setSubtitle(Integer.toString(year));
        }



        //오늘 버튼
        Button button = findViewById(R.id.button_today);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.setSelectedDate(Calendar.getInstance());
            }
        });
    }


    //일간 캘린더 다이얼로그에서 이벤트를 클릭시 일정 추가 즉, 이벤트 생성창이 올라온다.
    private void onEventSelected(Event event) {

        //calclick();
        /*Activity context = CalendarView.this;
        Intent intent = CreateHabit.makeIntent(context, event);

        startActivityForResult(intent, CREATE_EVENT_REQUEST_CODE);
        overridePendingTransition( R.anim.slide_in_up, R.anim.stay );*/
    }

    //이벤트 생성 transition을 통해 애니메이션 기법 사용, 아래에서 위로 올라온다.
    private void createEvent(Calendar selectedDate) {

        //calclick();
        /*Activity context = CalendarView.this;
        Intent intent = CreateHabit.makeIntent(context, selectedDate);

        startActivityForResult(intent, CREATE_EVENT_REQUEST_CODE);
        overridePendingTransition( R.anim.slide_in_up, R.anim.stay );*/
    }

    @Override//불러올 메뉴바 입력
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.menu_toolbar_calendar_view, menu);

        return true;
    }

    @Override//메뉴바 아이템  <오늘> 버튼 / 수정:메뉴바를 빼고 버튼을 하단에 추가하였음.
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.action_today: {
                mCalendarView.setSelectedDate(Calendar.getInstance());
                return true; }
        }*/
        return super.onOptionsItemSelected(item);
    }



    /*public void OnClickHandler(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("일정-습관 선택");

        builder.setItems(R.array.LAN, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int pos)
            {
                switch (pos) {
                    case 0:{
                        Intent intent = new Intent(getApplicationContext(), Category.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    }
                    case 1:{
                        Intent intent = new Intent(getApplicationContext(), CreateHabit.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/

    /*public void calclick(){

        AlertDialog.Builder dlg = new AlertDialog.Builder(CalendarView.this);
        dlg.setTitle("일정-습관 선택"); //제목
        //dlg.setMessage("안녕하세요 계발에서 개발까지 입니다."); // 메시지
        //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                버튼 클릭시 동작
        dlg.setItems(R.array.LAN, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int pos)
            {
                switch (pos) {
                    case 0:{
                        Intent intent = new Intent(getApplicationContext(), Category.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    }
                    case 1:{
                        Intent intent = new Intent(getApplicationContext(), CreateHabit.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
        dlg.show();
    }*/

}
