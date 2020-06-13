package com.example.swp1sec;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.swp1sec.data.Event;
import com.example.swp1sec.uihelpers.CalendarDialog;
import com.kyleduo.switchbutton.SwitchButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class CalendarView extends AppCompatActivity {

    //학사일정
    private String[][] school = {{"1", "1학기 개강일", "2019-03-16"}, {"2", "1학기 수강신청 확인 및정정", "2019-03-16"}, {"3", "1학기 수강신청 확인 및정정", "2020-03-17"}, {"4", "1학기 수강신청 확인 및정정", "2020-03-18"}, {"5", "1학기 수강신청 확인 및정정", "2020-03-19"}, {"6", "1학기 수업일수", "2020-04-08"}, {"7", "1학기 중간시험시작", "2020-04-28"},  {"8", "근로자의 날", "2020-05-01"}, {"9", "1학기 중간시험 끝", "2020-05-08"}, {"10", "1학기 교육실습 시작", "2020-05-11"},
            {"11", "1학기 교육실습 끝", "2020-06-05"}, {"12", "1학기 기말시험 시작", "2020-06-22"}, {"13", "1학기 기말시험 끝", "2020-07-01"}, {"14", "1학기 종강일", "2020-07-01"}, {"15", "하계 계절학기 시작", "2020-07-13"}, {"16", "하계방학 시작", "2020-07-14"}, {"17", "하계 계절학기 끝", "2020-07-31"}, {"18", "하계방학 종료", "2020-08-31"},  {"19", "2학기 복학신청 시작", "2020-07-14"}, {"20", "2학기 복학신청 끝", "2020-09-24"},
            {"21", "2학기 휴학신청 시작", "2020-07-14"},  {"22", "2학기 휴학신청 끝", "2020-07-24"}, {"23", "2학기 수강신청 시작", "2020-08-07"}, {"24", "2학기 수강신청 끝", "2020-08-12"}, {"25", "2학기 재학생등록 시작", "2020-08-25"}, {"26", "2학기 재학생등록 끝", "2020-08-28"}, {"27", "2019학년도 후기 학위수여식", "2020-08-28"}, {"28", "2학기 개강일 시작", "2020-09-01"}, {"29", "2학기 개강일 끝", "2020-09-07"}, {"30", "개척 대동제 시작", "2020-10-14"},
            {"31", "개척 대동제 끝", "2020-10-16"}, {"32", "개교 기념일", "2020-10-20"}, {"33", "2학기 중간시험 시작", "2020-10-26"}, {"34", "2학기 중간시험 끝", "2020-10-30"}, {"35", "2021년 재입학 신청 시작", "2020-12-01"}, {"36", "2021년 재입학 신청 끝", "2020-12-11"}, {"37", "2학기 기말시험 시작", "2020-12-07"},{"38", "2학기 기말시험 끝", "2020-12-21"}, {"39", "2학기 종강", "2020-12-21"}, {"40", "동계방학 시작", "2020-12-22"},
            {"41", "동계 계절학기 시작", "2020-12-28"}};
    private ArrayList<String[]> school_push;

    //음력
    private String[][] lunar = {{"1", "음력 12.15", "2020-01-09"},{"2", "음력 1.1", "2020-01-25"},{"3", "음력 1.15", "2020-02-08"},{"4", "음력 2.1", "2020-02-24"},{"5", "음력 2.15", "2020-03-09"},{"6", "음력 3.1", "2020-03-24"},{"7", "음력 3.15", "2020-04-07"},{"8", "음력 4.1", "2020-04-23"},{"9", "음력 4.15", "2020-05-07"},{"10", "음력 5.1", "2020-06-21"},
            {"11", "음력 5.15", "2020-07-05"},{"12", "음력 6.1", "2020-07-21"}, {"13", "음력 6.15", "2020-08-04"},{"14", "음력 7.1", "2020-08-19"},{"15", "음력 7.15", "2020-09-02"},{"16", "음력 8.1", "2020-09-17"},{"17", "음력 8.15", "2020-10-01"},{"350", "음력 9.1", "2020-10-17"},{"18", "음력 9.15", "2020-10-31"},{"19", "음력 10.1", "2020-11-15"},{"20", "음력 10.15", "2020-11-29"},
            {"21", "음력 11.1", "2020-12-15"},{"22", "음력 11.15", "2020-12-29"}};
    private ArrayList<String[]> lunar_push;

    //공휴일
    private String[][] holiday = {{"1", "신정", "2020-01-01"}, {"2", "설날", "2020-01-24"}, {"3", "설날", "2020-01-25"}, {"4", "설날", "2020-01-26"}, {"5", "대체 휴일", "2020-01-27"}, {"6", "대체 휴일", "2020-03-01"}, {"7", "부처님 오신날", "2020-04-30"}, {"8", "어린이날", "2020-05-05"}, {"9", "현충일", "2020-06-06"}, {"10", "광복절", "2020-08-15"},
            {"11", "추석", "2020-09-30"}, {"12", "추석", "2020-10-01"}, {"13", "추석", "2020-10-02"}, {"14", "개천절", "2020-10-03"}, {"15", "한글날", "2020-10-09"}, {"16", "크리스마스", "2020-12-25"}};
    private ArrayList<String[]> holiday_push;


    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private String[] mShortMonths;
    private com.example.swp1sec.CalendarViewM mCalendarView;
    private CalendarDialog mCalendarDialog;
    private Intent intent;
    private CalendarDialog.OnCalendarDialogListener mListener;

    //setting
    RecyclerView cateRecyclerView, calRecyclerView;
    private Intent cateintent;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageButton imgLeft, imgRight;
    LinearLayout drawer_right, drawer_left;
    TextView morningtime, nighttime,setting_theme,setting_start_day,badge;
    int mHour, mMinute, nHour, nMinute;
    private TextView txt_logout;

    //월간 일정 표시
    public static ArrayList<category_title_data> monthArrayList;
    private String monthjsonString;
    private static String MONTHURL = "http://159.89.193.200//get_month.php";
    private static String MONTHTAG = "getmonth";
    private List<Event> mEventList = new ArrayList<>();
    private Calendar mCalendar;
    //새로고침
    SwipeRefreshLayout mSwipe;

    //카테고리
    private category_title_adapter category_title_adapter;
    public static ArrayList<category_title_data> categoryArrayList;
    private String categoryjsonString;
    private static String CATEURL = "http://159.89.193.200//getcategory.php";
    private static String CATETAG = "getcategory";
    //달력
    private cal_title_adapter cal_title_adapter;
    private ArrayList<cal_title_data> calArrayList;
    private String caljsonString;
    private static String CALURL = "http://159.89.193.200//caltitle.php";
    private static String CALTAG = "getcal";

    private String email;
    private String calendar_title;

    private String check1, check2, check3;


    private List<Event> ArrEventList = new ArrayList<>();

    //체크박스
    private CheckBox lunarBox, holidayBox, schoolBox;


    public static Intent makeIntent(Context context) {
        return new Intent(context, CalendarView.class);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShortMonths = new DateFormatSymbols().getShortMonths();

        setContentView(R.layout.activity_calendar_view);

        //학사.공휴일,음력 푸시
        school_push = new ArrayList<String[]>();
        holiday_push = new ArrayList<String[]>();
        lunar_push = new ArrayList<String[]>();
        //list.add(new String[]{"1", "공휴일1", "2020-06-11"});
        //list.add(new String[]{"2", "공휴일2", "2020-06-12"});
        //list.add(new String[]{"3", "공휴일3", "2020-06-13"});

        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist = findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus = findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setting_theme = findViewById(R.id.setting_theme);
        setting_start_day=findViewById(R.id.setting_start_day);
        setting_theme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CalendarView.this, color_theme_dialog.class);
                startActivity(intent);
            }
        });
        setting_start_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_day_show();
            }
        });
        String text = PreferenceManager.getString(CalendarView.this,"start_week_title");
        setting_start_day.setText(text);

        email =  PreferenceManager.getString(CalendarView.this,"email");
        calendar_title = PreferenceManager.getString(CalendarView.this,"cal_title");

        //뱃지
        badge=findViewById(R.id.badge);
                badge.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(CalendarView.this, badge_dialog.class);
                        startActivity(intent);
            }
        });

        //로그아웃
        txt_logout = findViewById(R.id.txt_logout);
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CalendarView.this, LoginActivity.class);
                PreferenceManager.clear(CalendarView.this);

                startActivity(intent);
            }
        });

        //맨 처음 세팅값.
        //PreferenceManager.setString(CalendarView.this, "check1", "l");
        //PreferenceManager.setString(CalendarView.this, "check2", "h");
        //PreferenceManager.setString(CalendarView.this, "check3", "u");

        //check1 = PreferenceManager.getString(CalendarView.this, "check1");
        //check2 = PreferenceManager.getString(CalendarView.this, "check2");
        //check3 = PreferenceManager.getString(CalendarView.this, "check3");

        //개인 설정 체크박스
        lunarBox = findViewById(R.id.lunar);
        holidayBox = findViewById(R.id.holiday);
        schoolBox = findViewById(R.id.univer);

        //맨 처음 세팅
        PreferenceManager.setBoolean(getApplicationContext(),"lunarBox",false);
        PreferenceManager.setBoolean(getApplicationContext(),"holidayBox",false);
        PreferenceManager.setBoolean(getApplicationContext(),"schoolBox",false);

        //음력 이벤트
        lunarBox.setChecked(PreferenceManager.getBoolean(getApplicationContext(),"lunarBox"));
        lunarBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(lunarBox.isChecked() == true){
                    for(int i =0;i<lunar.length;i++) {
                        lunar_push.add(lunar[i]);
                    }
                    LunarArray();
                    //Toast toast = Toast.makeText(getApplicationContext(), lunar_push.get(1)[2], Toast.LENGTH_SHORT);
                    //toast.show();
                    PreferenceManager.setBoolean(getApplicationContext(),"lunarBox",true);
                }else{
                    lunar_push.clear();
                    PreferenceManager.setBoolean(getApplicationContext(),"lunarBox",false);
                    //Toast toast = Toast.makeText(getApplicationContext(), "음력 체크 해제", Toast.LENGTH_SHORT);
                    //toast.show();
                    intent = new Intent(getApplicationContext(),CalendarView.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        if(PreferenceManager.getBoolean(getApplicationContext(),"lunarBox")){
            for(int i =0;i<lunar.length;i++) {
                lunar_push.add(lunar[i]);
            }
            LunarArray();
            //Toast toast = Toast.makeText(getApplicationContext(), lunar_push.get(1)[2], Toast.LENGTH_SHORT);
            //toast.show();
            PreferenceManager.setBoolean(getApplicationContext(),"lunarBox",true);
        }
        else{
            lunar_push.clear();
            PreferenceManager.setBoolean(getApplicationContext(),"lunarBox",false);
            //Toast toast = Toast.makeText(getApplicationContext(), "음력 체크 해제된 상태", Toast.LENGTH_SHORT);
            //toast.show();
        }

        //공휴일 이벤트
        holidayBox.setChecked(PreferenceManager.getBoolean(getApplicationContext(),"holidayBox"));
        holidayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holidayBox.isChecked() == true){
                    for(int i =0;i<holiday.length;i++) {
                        holiday_push.add(holiday[i]);
                    }
                    HolidayArray();
                    //Toast toast = Toast.makeText(getApplicationContext(), holiday_push.get(1)[2], Toast.LENGTH_SHORT);
                    //toast.show();
                    PreferenceManager.setBoolean(getApplicationContext(),"holidayBox",true);
                }else{
                    holiday_push.clear();
                    PreferenceManager.setBoolean(getApplicationContext(),"holidayBox",false);
                    //Toast toast = Toast.makeText(getApplicationContext(), "공휴일 체크 해제", Toast.LENGTH_SHORT);
                    //toast.show();
                    intent = new Intent(getApplicationContext(),CalendarView.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        if(PreferenceManager.getBoolean(getApplicationContext(),"holidayBox")){
            for(int i =0;i<holiday.length;i++) {
                holiday_push.add(holiday[i]);
            }
            HolidayArray();
            //Toast toast = Toast.makeText(getApplicationContext(), school_push.get(1)[2], Toast.LENGTH_SHORT);
            //toast.show();
            PreferenceManager.setBoolean(getApplicationContext(),"holidayBox",true);
        }
        else{
            holiday_push.clear();
            PreferenceManager.setBoolean(getApplicationContext(),"holidayBox",false);
            //Toast toast = Toast.makeText(getApplicationContext(), "공휴일 체크 해제된 상태", Toast.LENGTH_SHORT);
            //toast.show();
        }

        //학사일정 이벤트
        schoolBox.setChecked(PreferenceManager.getBoolean(getApplicationContext(),"schoolBox"));
        schoolBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(schoolBox.isChecked() == true){
                    for(int i =0;i<school.length;i++) {
                        school_push.add(school[i]);
                    }
                    Array();
                    //Toast toast = Toast.makeText(getApplicationContext(), school_push.get(1)[2], Toast.LENGTH_SHORT);
                    //toast.show();
                    PreferenceManager.setBoolean(getApplicationContext(),"schoolBox",true);
                }else{
                    school_push.clear();
                    PreferenceManager.setBoolean(getApplicationContext(),"schoolBox",false);
                    //Toast toast = Toast.makeText(getApplicationContext(), "학사일정 체크 해제", Toast.LENGTH_SHORT);
                    //toast.show();
                    intent = new Intent(getApplicationContext(),CalendarView.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        if(PreferenceManager.getBoolean(getApplicationContext(),"schoolBox")){
            for(int i =0;i<school.length;i++) {
                school_push.add(school[i]);
            }
            Array();
            //Toast toast = Toast.makeText(getApplicationContext(), school_push.get(1)[2], Toast.LENGTH_SHORT);
            //toast.show();
            PreferenceManager.setBoolean(getApplicationContext(),"schoolBox",true);
        }
        else{
            school_push.clear();
            PreferenceManager.setBoolean(getApplicationContext(),"schoolBox",false);
            //Toast toast = Toast.makeText(getApplicationContext(), "학사일정 체크 해제된 상태", Toast.LENGTH_SHORT);
            //toast.show();
        }



        /*
        if(check1 == "l"){
            lunarBox.setChecked(false);

        }else {
            lunarBox.setChecked(true);

        }
        if(check2 == "h"){
            holidayBox.setChecked(false);
        }else {
            holidayBox.setChecked(true);

        }
        if(check3 == "u"){
            univerBox.setChecked(false);
        }else {
            univerBox.setChecked(true);
        }

        //박스 선택시 preferencemanager에 값 넘겨줌.
        if(lunarBox.isChecked()){
            PreferenceManager.setString(CalendarView.this, "check1", "ll");
            lunarBox.setChecked(true);
        }else{
            PreferenceManager.setString(CalendarView.this, "check1", "l");
            lunarBox.setChecked(false);
        }

        if(holidayBox.isChecked()){
            PreferenceManager.setString(CalendarView.this, "check2", "hh");
            holidayBox.setChecked(true);
        }else{
            PreferenceManager.setString(CalendarView.this, "check2", "h");
            holidayBox.setChecked(false);
        }

        if(univerBox.isChecked()){
            PreferenceManager.setString(CalendarView.this, "check3", "uu");
            univerBox.setChecked(true);
        }else{
            PreferenceManager.setString(CalendarView.this, "check3", "u");
            univerBox.setChecked(false);
        }*/




        //툴바
        setSupportActionBar(toolbar);
        mCalendarView = findViewById(R.id.calendarView);
        //상단 툴바에 뜨는 달(Title)이랑 년도(subtitle) 설정 움직일때 변경되는 것
        mCalendarView.setOnMonthChangedListener(new com.example.swp1sec.CalendarViewM.OnMonthChangedListener() {
            @Override // 여기에 int day 추가했음
            public void onMonthChanged(int month, int year) {

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(year +"."+(month+1));//mShortMonths[month]

                }
            }
        });

        //캘린더를 선택했을 때 size가 제로가 아니다 즉, 일정이 있으면 다이얼로그를 표시하고 없으면 바로 일정 추가창을 띄운다.
        mCalendarView.setOnItemClickedListener(new com.example.swp1sec.CalendarViewM.OnItemClickListener() {
            @Override
            public void onItemClicked(List<com.example.swp1sec.CalendarViewM.CalendarObject> calendarObjects,
                                      Calendar previousDate,
                                      Calendar selectedDate) {
                if (calendarObjects.size() != 0) {
                    mCalendarDialog.setSelectedDate(selectedDate);
                    mCalendarDialog.show();
                } else {
                    if (diffYMD(previousDate, selectedDate) == 0)
                        createEvent(selectedDate);
                }
            }
        });




        //상단 액션바와 연관 움직일때x 초기화면을 말함
        if (getSupportActionBar() != null) {
            // int day = mCalendarView.getCurrentDate().get(Calendar.DATE);
            int month = mCalendarView.getCurrentDate().get(Calendar.MONTH);
            int year = mCalendarView.getCurrentDate().get(Calendar.YEAR);
            getSupportActionBar().setTitle(year + "." + (month+1)); // //mShortMonths[month] 여기에 +day 를 추가

        }

        //캘린더 다이얼로그 창
        mCalendarDialog = CalendarDialog.Builder.instance(this)
                .setEventList(mEventList)
                .setOnItemClickListener(new CalendarDialog.OnCalendarDialogListener() {
                    //다이얼로그에서 각 이벤트 클릭시 이벤트 발생
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onEventClick(Event event) {
                        //onEventSelected(event);


                        //event = mEventList.get()
                        //mListener.onEventClick(mEventList.get(getAdapterPosition());
                        Log.d("이벤트 : ", String.valueOf(event.getMdivision()));
                        Log.d("타이틀", String.valueOf(event.getTitle()));
                        if(event.getMdivision() == 0){

                            Intent intent = new Intent(getApplicationContext(),CreateSubject.class);
                            intent.putExtra("eventsub", event);
                            startActivity(intent);
                            overridePendingTransition( R.anim.slide_in_up, R.anim.stay );

                        }
                        else if(event.getMdivision() == 1){

                            Intent intent = new Intent(getApplicationContext(),CreateExercise.class);
                            intent.putExtra("eventex", event);
                            startActivity(intent);
                            overridePendingTransition( R.anim.slide_in_up, R.anim.stay );

                        }
                        else {

                            Intent intent = new Intent(getApplicationContext(),CreateNormal.class);
                            intent.putExtra("eventnor", event);
                            startActivity(intent);
                            overridePendingTransition( R.anim.slide_in_up, R.anim.stay );

                        }



                    }
                    //다이얼로그 우측 하단의 플러스 버튼 누를시 일정 생성 이벤트 발생
                    @Override
                    public void onCreateEvent(Calendar calendar) {
                        createEvent(calendar);
                    }
                })
                .create();


        //액티비티 주간 캘린더로 전환
        ImageButton changer = findViewById(R.id.cal_changer);
        changer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // 액티비티 전환 코드
                Intent intent = new Intent(CalendarView.this, WeekCalendarF.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);//
                startActivity(intent);


            }
        });


        ibtn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CalendarView.this, CalendarView.class);
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
        ibtn_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CalendarView.this, DayTrackerActivity.class);
                startActivity(intent);
            }
        });
        ibtn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(CalendarView.this, Store_main.class);
                startActivity(intent);
            }
        });

        //캘린더
        calRecyclerView = (RecyclerView) findViewById(R.id.cal_title_list);
        LinearLayoutManager calLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        calRecyclerView.setLayoutManager(calLayoutManager);
        calArrayList = new ArrayList<>();


        cal_title_adapter = new cal_title_adapter(this, calArrayList);
        calRecyclerView.setAdapter(cal_title_adapter);

        calArrayList.clear();
        cal_title_adapter.notifyDataSetChanged();
        calGetData caltask = new calGetData(); //밑에 만들었던 클래스 만들고
        caltask.execute(CALURL, email); //task 실행

        //캘린더 데이터

        //카테고리
        cateRecyclerView = (RecyclerView) findViewById(R.id.category_title_list);
        LinearLayoutManager cateLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cateRecyclerView.setLayoutManager(cateLayoutManager);
        categoryArrayList = new ArrayList<>();
        category_title_adapter = new category_title_adapter(this, categoryArrayList);
        cateRecyclerView.setAdapter(category_title_adapter);

        categoryArrayList.clear();
        category_title_adapter.notifyDataSetChanged();
        cateGetData catetask = new cateGetData(); //밑에 만들었던 클래스 만들고
        catetask.execute(CATEURL, email, calendar_title); //task 실행


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

        //월간
        monthArrayList = new ArrayList<>();
        monthGetData monthtask = new monthGetData(); //밑에 만들었던 클래스 만들고
        monthtask.execute(MONTHURL, email, calendar_title); //task 실행



        //setting
        drawerLayout = findViewById(R.id.activity_calendar_view);
        drawer_left = findViewById(R.id.drawer_left);
        drawer_right = findViewById(R.id.drawer_right);
        imgLeft = findViewById(R.id.imgleft);
        imgRight = findViewById(R.id.imgRight);

        getSupportActionBar().setDisplayShowHomeEnabled(true);



        cal_title_adapter.setOnCheckedChangeListener(new cal_title_adapter.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, int pos){
                //String cal_title = calArrayList.get(pos).gettitle();
                String cal_title = PreferenceManager.getString(getApplicationContext(),"cal_title");
                int cal_pos = PreferenceManager.getInt(getApplicationContext(),"cal_pos");
                //Toast.makeText(CalendarView.this, cal_title +Integer.toString(calArrayList.get(cal_pos).getperformance()), Toast.LENGTH_SHORT).show();

                category_title_adapter.notifyDataSetChanged();
                categoryArrayList.clear();

                cateGetData catetask = new cateGetData(); //밑에 만들었던 클래스 만들고
                catetask.execute(CATEURL, email, cal_title);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);
                            boolean success=jasonObject.getBoolean("success");
                            if (success) {

                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(), "다시 시도해주세요.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                toast.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                cal_check_request cal_check_request =new cal_check_request(email,cal_title,Integer.toString(calArrayList.get(cal_pos).getperformance()),responseListener);
                RequestQueue queue=Volley.newRequestQueue(CalendarView.this);
                queue.add(cal_check_request);


            }


        });




        /*cal_title_adapter.setOnCheckedChangeListener(new cal_title_adapter.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(View v,int position){
                final String cal_title = calArrayList.get(position).gettitle();
                PreferenceManager.setString(CalendarView.this,"cal_title",cal_title);
                Toast.makeText(CalendarView.this,PreferenceManager.getString(CalendarView.this,"cal_title"),Toast.LENGTH_SHORT).show();
                categoryArrayList.clear();
                category_title_adapter.notifyDataSetChanged();
                cateGetData catetask = new cateGetData(); //밑에 만들었던 클래스 만들고
                catetask.execute(CATEURL, email, cal_title); //task 실행
            }
        });*/

        //데이터 수정 및 삭제
        category_title_adapter.setOnClickListener(new category_title_adapter.OnClickListener(){
            @Override
            public void onClick(View v, int vi, final int position){
                String cal_title = PreferenceManager.getString(CalendarView.this, "cal_title");
                String cate_title = categoryArrayList.get(position).gettitle();
                if(vi == 0){ //이미지 뷰 클릭이란 말
                    if(categoryArrayList.get(position).getperformance()==0){//선택이 되어있지 않았음
                        categoryArrayList.get(position).setperformance(1);
                        category_title_adapter.notifyDataSetChanged();
                    }
                    else{
                        categoryArrayList.get(position).setperformance(0);
                        category_title_adapter.notifyDataSetChanged();
                    }
                    Response.Listener<String> responseListener=new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject=new JSONObject(response);
                                boolean success=jasonObject.getBoolean("success");
                                if (!success) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "다시 시도해주세요.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                    toast.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    Cate_check_request cate_check_request=new Cate_check_request(email,cal_title,cate_title,Integer.toString(categoryArrayList.get(position).getperformance()),responseListener);
                    RequestQueue queue= Volley.newRequestQueue(CalendarView.this);
                    queue.add(cate_check_request);
                }
                else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(cateRecyclerView.getContext());
                    ProgressDialog progressDialog = new ProgressDialog(cateRecyclerView.getContext());

                    CharSequence[] dialogItem ={"view data","edit data","delete data"};
                    builder.setTitle(categoryArrayList.get(position).gettitle());
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            switch (i){
                                case 0:
                                    if(categoryArrayList.get(position).getdivision() == 0){
                                    cateintent = new Intent(getApplicationContext(),cate_dataview.class);
                                    cateintent.putExtra("position",position);
                                    startActivity(cateintent);}
                                    else
                                    {
                                        cateintent = new Intent(getApplicationContext(),cate_dataview_exnm.class);
                                        cateintent.putExtra("position",position);
                                        startActivity(cateintent);
                                    }



                                    break;
                                case 1:
                                    if(categoryArrayList.get(position).getdivision() == 0) {

                                        cateintent = new Intent(getApplicationContext(), update_cate_dialog.class);
                                        cateintent.putExtra("position", position);
                                        startActivity(cateintent);
                                    }
                                    else
                                    {
                                        cateintent = new Intent(getApplicationContext(), update_cate_exsb_dialog.class);
                                        cateintent.putExtra("position", position);
                                        startActivity(cateintent);
                                    }

                                /*cateintent = new Intent(getApplicationContext(), update_cate_dialog.class);
                                cateintent.putExtra("position", position);
                                startActivity(cateintent);*/

                                    break;
                                case 2:
                                    deleteData(categoryArrayList.get(position).getid());

                                    break;
                            }

                        }
                    });
                    builder.create().show();
                }

            }
        });

        //스위치 구현
        SwitchButton switchButton = (SwitchButton) findViewById(R.id.sb_use_listener);//스위치버튼 구현
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(getApplicationContext(), ScreenService.class);

                if(isChecked){
                    startService(intent);//스위치가 켜진 상태일때.
                }else{
                    stopService(intent);//스위치가 꺼진 상태일때.
                }
            }
        });


        //Timepicker
        morningtime = findViewById(R.id.morning_time);

        nighttime = findViewById(R.id.night_time);
        //아침시간
        morningtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        CalendarView.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mHour = hourOfDay;
                                mMinute = minute;
                                String time = mHour + ":" + mMinute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                    morningtime.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(mHour, mMinute);
                timePickerDialog.show();
            }
        });
        //저녁 시간
        nighttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        CalendarView.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                nHour = hourOfDay;
                                nMinute = minute;
                                String time = nHour + ":" + nMinute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                    nighttime.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(nHour, nMinute);
                timePickerDialog.show();
            }
        });

        ImageButton btn_go = findViewById(R.id.add_button);
        btn_go.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(CalendarView.this, add_dialog.class);
                        startActivity(intent);
                    }
                });


        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(drawer_left)) {
                    drawerLayout.closeDrawer(drawer_left);
                } else if (!drawerLayout.isDrawerOpen(drawer_left)) {
                    drawerLayout.openDrawer(drawer_left);
                }
                if (drawerLayout.isDrawerOpen(drawer_right)) {
                    drawerLayout.closeDrawer(drawer_right);
                }
            }
        });
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(drawer_right)) {
                    drawerLayout.closeDrawer(drawer_right);
                } else if (!drawerLayout.isDrawerOpen(drawer_right)) {
                    drawerLayout.openDrawer(drawer_right);
                }
                if (drawerLayout.isDrawerOpen(drawer_left)) {
                    drawerLayout.closeDrawer(drawer_left);
                }
            }
        });


        //새로고침
        mSwipe = (SwipeRefreshLayout)findViewById(R.id.refresh_calendar);
        mSwipe.setColorSchemeColors(R.color.event_color_01);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
                mSwipe.setRefreshing(false);
            }
        });
        //mSwipe.setRefreshing(false);

    }





    //일간 캘린더 다이얼로그에서 이벤트를 클릭시 일정 추가 즉, 이벤트 생성창이 올라온다.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onEventSelected(Event event) {


        Event oldEvent = null;
        for (Event e : mEventList) {
            if (Objects.equals(event.getID(), e.getID())) {
                oldEvent = e;
                break;
            }
        }

        if(event.getMdivision() == 0){

            Intent intent = new Intent(getApplicationContext(),CreateSubject.class);
            intent.putExtra("eventsub", event);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.stay );

        }
        else if(event.getMdivision() == 1){

            Intent intent = new Intent(getApplicationContext(),CreateExercise.class);
            intent.putExtra("eventex", event);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.stay );

        }
        else {

            Intent intent = new Intent(getApplicationContext(),CreateNormal.class);
            intent.putExtra("eventnor", event);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.stay );

        }


        //여기아래는 필요 없을 듯.
        /*if (oldEvent != null) {
            mEventList.remove(oldEvent);
            mEventList.add(event);

            mCalendarView.removeCalendarObjectByID(parseCalendarObject(oldEvent));
            mCalendarView.addCalendarObject(parseCalendarObject(event));
            mCalendarDialog.setEventList(mEventList);
        }*/


        //calclick();
        /*Activity context = CalendarView.this;
        Intent intent = CreateSubject.makeIntent(context, event);

        startActivityForResult(intent, CREATE_EVENT_REQUEST_CODE);
        overridePendingTransition( R.anim.slide_in_up, R.anim.stay );*/
    }

    //실험
    private static CalendarViewM.CalendarObject parseCalendarObject(Event event) {
        return new CalendarViewM.CalendarObject(
                event.getID(),
                event.getDate(),
                event.getTitle(),
                event.getColor());
    }

    //이벤트 생성 transition을 통해 애니메이션 기법 사용, 아래에서 위로 올라온다.
    private void createEvent(Calendar selectedDate) {

        calclick();
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

    /*@Override//메뉴바 아이템  <오늘> 버튼 / 수정:메뉴바를 빼고 버튼을 하단에 추가하였음.
    public boolean onOptionsItemSelected(MenuItem item) {
        *//*switch (item.getItemId()) {
            case R.id.action_today: {
                mCalendarView.setSelectedDate(Calendar.getInstance());
                return true; }
        }*//*
        return super.onOptionsItemSelected(item);
    }*/

    public static int diffYMD(Calendar date1, Calendar date2) {
        if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
                date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH))
            return 0;

        return date1.before(date2) ? -1 : 1;
    }

    //가운데 일정 추가버튼
    public void OnClickHandler(View view)
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
    }

    public void calclick(){

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
    }



    private void deleteData(final String id) {
        StringRequest request =new StringRequest(Request.Method.POST, "http://159.89.193.200/deletedata.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("Data Deleted")){
                            Toast.makeText(CalendarView.this,"Data Deleted Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(CalendarView.this,"Data Not Deleted",Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CalendarView.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();
                params.put("id", id);

                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);

    }


    public void onPopupButtonClick(View button){
        PopupMenu popup = new PopupMenu(this,button);
        popup.getMenuInflater().inflate(R.menu.category,popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            public  boolean onMenuItemClick(MenuItem item){
                switch (item.getItemId()){
                    case R.id.subject:
                        Intent intent = new Intent(CalendarView.this,sb_dialog.class);
                        startActivity(intent);
                        break;
                    case R.id.exercise:
                        Intent intent1 = new Intent(CalendarView.this,exercise_dialog.class);
                        startActivity(intent1);
                        break;
                    case R.id.normal:
                        Intent intent2 = new Intent(CalendarView.this,normal_dialog.class);
                        startActivity(intent2);
                        break;
                }
                return  true;
            }
        });
        popup.show();
    }

    //카테고리 타이틀

    private class cateGetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(CalendarView.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(CATETAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                category_title_adapter.notifyDataSetChanged();
                categoryArrayList.clear();
                categoryjsonString = result; //크롬으로 확인했던 문자열 받아오고
                daycateShowResult(); //밑에 dayHabitShowResult함수 실행

            }
        }

        @Override
        protected String doInBackground(String... params) { //task.excute로 넘겨주는 매개변수들

            String serverURL = params[0]; //PHPURL
            String email = (String)params[1]; //email
            String cal_title = (String)params[2]; //캘린더 타이틀

            String postParameters = "email=" + email +"&" +"cal_title="+cal_title; //php 파일에 $_POST 변수가 받기 위한 코드

            try { //여기부턴 php코드 한줄씩 읽는거니까 그냥 읽기만 해봐

                java.net.URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(CATETAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(CATETAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void daycateShowResult() { //이부분 잘봐

        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그

        String TAG_id = "id";
        String TAG_TITLE = "cate_title";
        String TAG_pro_name = "pro_name";
        String TAG_pro_email = "pro_email";
        String TAG_day = "day";
        String TAG_class_start = "class_start";
        String TAG_class_ends = "class_ends";
        String TAG_lectureroom = "lectureroom";
        String TAG_division = "division";
        String TAG_color = "color";
        String TAG_day1 = "day1";
        String TAG_class_start1 = "class_start1";
        String TAG_class_ends1 = "class_ends1";
        String TAG_performance = "performance";



        try {
            JSONObject jsonObject = new JSONObject(categoryjsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와

            for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                JSONObject item = jsonArray.getJSONObject(i);
                //반복문인점 주의!
                //그럼 거기서 이제 "title"에 해당하는 문자열 값 가져와서 저장

                String id = item.getString(TAG_id);
                String Title = item.getString(TAG_TITLE);
                String pro_name = item.getString(TAG_pro_name);
                String pro_email = item.getString(TAG_pro_email);
                String day = item.getString(TAG_day);
                String class_start = item.getString(TAG_class_start);
                String class_ends = item.getString(TAG_class_ends);
                String lectureroom = item.getString(TAG_lectureroom);
                String division = item.getString(TAG_division);
                String color = item.getString(TAG_color);
                String day1 = item.getString(TAG_day1);
                String class_start1 = item.getString(TAG_class_start1);
                String class_ends1 = item.getString(TAG_class_ends1);
                String performance = item.getString(TAG_performance);


                int div=Integer.parseInt(division);
                int per = Integer.parseInt(performance);

                category_title_data category_title_data = new category_title_data(id,Title,pro_name,pro_email,day,class_start,class_ends,lectureroom,div,color,day1,class_start1,class_ends1,per);
                category_title_data.setid(id);
                category_title_data.settitle(Title);
                category_title_data.setpro_name(pro_name);
                category_title_data.setpro_email(pro_email);
                category_title_data.setday(day);
                category_title_data.setclass_start(class_start);
                category_title_data.setclass_ends(class_ends);
                category_title_data.setlectureroom(lectureroom);
                category_title_data.setdivision(div);
                category_title_data.setcolor(color);
                category_title_data.setday1(day1);
                category_title_data.setclass_start1(class_start1);
                category_title_data.setclass_ends1(class_ends1);
                category_title_data.setperformance(per);




                categoryArrayList.add(category_title_data); //받아온값이들어있는 dayHabit 객체들을 ArrayList<DayHabit>에 차례로 집어넣고
                category_title_adapter.notifyDataSetChanged(); //집어넣었으니까 어댑터한테 값 새로 들어갔다고 알려줌 -> 리사이클러뷰 새로고침
            }

        } catch (JSONException e) {
            Log.d(CATETAG, "showResult : ", e);
        }
    }


    //캘린더 데이터
    private class calGetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(CalendarView.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(CALTAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                caljsonString = result;
                daycalShowResult();
                String cal_title = PreferenceManager.getString(CalendarView.this, "cal_title");
                categoryArrayList.clear();
                category_title_adapter.notifyDataSetChanged();
                cateGetData catetask = new cateGetData(); //밑에 만들었던 클래스 만들고
                catetask.execute(CATEURL, email, cal_title); //task 실행
            }
        }

        @Override
        protected String doInBackground(String... params) { //task.excute로 넘겨주는 매개변수들

            String serverURL = params[0]; //PHPURL
            String email = (String)params[1]; //email

            String postParameters = "email=" + email; //php 파일에 $_POST 변수가 받기 위한 코드

            try { //여기부턴 php코드 한줄씩 읽는거니까 그냥 읽기만 해봐

                java.net.URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(CALTAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(CALTAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void daycalShowResult() { //이부분 잘봐

        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그
        String TAG_TITLE = "cal_title";
        String TAg_Performance ="performance";


        try {
            JSONObject jsonObject = new JSONObject(caljsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와

            for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                JSONObject item = jsonArray.getJSONObject(i);
                //반복문인점 주의!
                String Title = item.getString(TAG_TITLE); //그럼 거기서 이제 "title"에 해당하는 문자열 값 가져와서 저장
                int performnace = item.getInt(TAg_Performance);
                if(performnace == 1){
                    PreferenceManager.setString(CalendarView.this,"cal_title",Title);
                    PreferenceManager.setInt(CalendarView.this,"cal_pos",i);
                }
                cal_title_data cal_title_data = new cal_title_data(Title);
                cal_title_data.settitle(Title);
                cal_title_data.setperformance(performnace);


                calArrayList.add(cal_title_data); //받아온값이들어있는 dayHabit 객체들을 ArrayList<DayHabit>에 차례로 집어넣고
                cal_title_adapter.notifyDataSetChanged(); //집어넣었으니까 어댑터한테 값 새로 들어갔다고 알려줌 -> 리사이클러뷰 새로고침
            }

        } catch (JSONException e) {
            Log.d(CALTAG, "showResult : ", e);
        }
    }

    void start_day_show()
    {

        final List<String> ListItems = new ArrayList<>();
        ListItems.add("월요일");
        ListItems.add("일요일");

        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("주 시작 요일");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                switch (pos){
                    case 0:
                        PreferenceManager.setInt(CalendarView.this,"start_week", 1);
                        PreferenceManager.setString(CalendarView.this, "start_week_title", "월요일");
                        //String text = PreferenceManager.getString(CalendarView.this,"start_week_title");
                        //setting_start_day.setText(text);
                        setting_start_day.setText("월요일");



                        break;
                    case 1:
                        PreferenceManager.setInt(CalendarView.this,"start_week", 2);
                        PreferenceManager.setString(CalendarView.this, "start_week_title", "일요일");
                        //String text2 = PreferenceManager.getString(CalendarView.this,"start_week_title2");
                        //setting_start_day.setText(text2);
                        setting_start_day.setText("일요일");
                        break;

                }
                String selectedText = items[pos].toString();
                Toast.makeText(CalendarView.this, selectedText, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }


    //월간 일정 표시

    private class monthGetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(CalendarView.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);


            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(MONTHTAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                monthjsonString = result; //크롬으로 확인했던 문자열 받아오고
                MonthResult(); //밑에 ShowResult함수 실행
                //추가
                //Array(); ??
            }
        }

        @Override
        protected String doInBackground(String... params) { //task.excute로 넘겨주는 매개변수들

            String serverURL = params[0]; //PHPURL
            String email = (String)params[1]; //email
            String cal_title =(String)params[2];

            String postParameters = "email=" + email +"&"+"cal_title="+cal_title; //php 파일에 $_POST 변수가 받기 위한 코드

            try { //여기부턴 php코드 한줄씩 읽는거니까 그냥 읽기만 해봐

                java.net.URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(MONTHTAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(MONTHTAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void MonthResult() { //이부분 잘봐

        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그
        String TAG_TITLE = "title";
        String TAG_DATE = "date";
        String TAG_TIME = "time";
        // TAG_ENDDATE = "enddate";
        //String TAG_ENDTIME = "endtime";
        String TAG_DIVISION = "division"; //디비전으로 일정 다이얼로그에 해당 일정 창에 맞게 뜨도록 유도.
        String TAG_ID = "id";
        String TAG_COLOR = "color";

        try {
            JSONObject jsonObject = new JSONObject(monthjsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와

            for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                JSONObject item = jsonArray.getJSONObject(i);
                //DB에서 받아오기
                String Title = item.getString(TAG_TITLE);
                String Date = item.getString(TAG_DATE);
                String Time = item.getString(TAG_TIME);
                //String EndDate = item.getString(TAG_ENDDATE);
                //String EndTime = item.getString(TAG_ENDTIME);
                int ID = item.getInt(TAG_ID);
                int division = item.getInt(TAG_DIVISION);
                String Color = item.getString(TAG_COLOR);

                //캘린더 id 랜덤으로
                Random rndId = new Random();
                int Id = rndId.nextInt(3000);

                //캘린더에 일정 저장하는 부분
                Event event = new Event();
                mCalendar = Calendar.getInstance();
                int year = Integer.parseInt(Date.substring(0,4));
                int month = Integer.parseInt(Date.substring(5,7))-1;
                int date = Integer.parseInt(Date.substring(8,10));
                int hour = 0;
                int minute = 0;
                if(Time != "null"){
                    hour = Integer.parseInt(Time.substring(0,2));
                    minute = Integer.parseInt(Time.substring(3,5));
                }else{
                    hour = 0;
                    minute = 0;
                }
                //int hour = Integer.parseInt(Time.substring(0,2));
                //int minute = Integer.parseInt(Time.substring(3,5));
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);
                mCalendar.set(Calendar.MINUTE, minute);
                mCalendar.set(Calendar.SECOND, 0);
                mCalendar.set(Calendar.MILLISECOND, 0);

                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, date);


                event.setMdivision(division); // 디비전 값 저장
                //android.graphics.Color.parseColor(Color);
                event.setmID(String.valueOf(Id));
                event.setmTitle(Title);
                event.setmColor(android.graphics.Color.parseColor(Color));
                //event.setmDate(Calendar.getInstance());
                event.setmDate(mCalendar);
                //event.setmDate(Calendar.MINUTE, );
                event.setCompleted(false);
                mEventList.add(event); // 일간 다이얼로그에 값 넣어줌
                //월간 캘린더 표시에 값 넣어줌
                mCalendarView.addCalendarObject(new com.example.swp1sec.CalendarViewM.CalendarObject(event.getID(), event.getDate(), event.getTitle(), event.getColor()));

            }


        } catch (JSONException e) {
            Log.d(MONTHTAG, "showResult : ", e);
        }


    }


    //학사일정 일정 표시
    public void Array(){

        Event event = new Event();

        for(int i = 0; i < school_push.size(); i++){
            String Title = school_push.get(i)[1];
            String Date = school_push.get(i)[2];
            String Id = school_push.get(i)[0];

            //캘린더에 일정 저장하는 부분
            mCalendar = Calendar.getInstance();
            int year = Integer.parseInt(Date.substring(0,4));
            int month = Integer.parseInt(Date.substring(5,7))-1;
            int date = Integer.parseInt(Date.substring(8,10));
            int hour = 0;
            int minute = 0;
            String Time = "null";
            if(Time != "null"){
                hour = Integer.parseInt(Time.substring(0,2));
                minute = Integer.parseInt(Time.substring(3,5));
            }else{
                hour = 0;
                minute = 0;
            }
            mCalendar.set(Calendar.HOUR_OF_DAY, hour);
            mCalendar.set(Calendar.MINUTE, minute);
            mCalendar.set(Calendar.SECOND, 0);
            mCalendar.set(Calendar.MILLISECOND, 0);
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, date);

            //캘린더 id 랜덤으로
            //Random rndId = new Random();
            //int Id = rndId.nextInt(3000);
            event.setmID(Id);
            event.setmTitle(Title);
            event.setmColor(R.color.school);
            //event.setmDate(Calendar.getInstance());
            event.setmDate(mCalendar);
            //event.setmDate(Calendar.MINUTE, );
            event.setCompleted(false);
            ArrEventList.add(event); // 일간 다이얼로그에 값 넣어줌
            //월간 캘린더 표시에 값 넣어줌
            mCalendarView.addCalendarObject(new com.example.swp1sec.CalendarViewM.CalendarObject(event.getID(), event.getDate(), event.getTitle(), event.getColor()));
            Log.d("id", ArrEventList.get(i).getID());
            Log.d("title", ArrEventList.get(i).getTitle());
        }
    }

    //공휴일 일정 표시
    public void HolidayArray(){

        Event event = new Event();

        for(int i = 0; i < holiday_push.size(); i++){
            String Title = holiday_push.get(i)[1];
            String Date = holiday_push.get(i)[2];
            String Id = holiday_push.get(i)[0];

            //캘린더에 일정 저장하는 부분
            mCalendar = Calendar.getInstance();
            int year = Integer.parseInt(Date.substring(0,4));
            int month = Integer.parseInt(Date.substring(5,7))-1;
            int date = Integer.parseInt(Date.substring(8,10));
            int hour = 0;
            int minute = 0;
            String Time = "null";
            if(Time != "null"){
                hour = Integer.parseInt(Time.substring(0,2));
                minute = Integer.parseInt(Time.substring(3,5));
            }else{
                hour = 0;
                minute = 0;
            }
            mCalendar.set(Calendar.HOUR_OF_DAY, hour);
            mCalendar.set(Calendar.MINUTE, minute);
            mCalendar.set(Calendar.SECOND, 0);
            mCalendar.set(Calendar.MILLISECOND, 0);
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, date);

            //캘린더 id 랜덤으로
            //Random rndId = new Random();
            //int Id = rndId.nextInt(3000);
            event.setmID(Id);
            event.setmTitle(Title);
            event.setmColor(R.color.holiday);
            //event.setmDate(Calendar.getInstance());
            event.setmDate(mCalendar);
            //event.setmDate(Calendar.MINUTE, );
            event.setCompleted(false);
            ArrEventList.add(event); // 일간 다이얼로그에 값 넣어줌
            //월간 캘린더 표시에 값 넣어줌
            mCalendarView.addCalendarObject(new com.example.swp1sec.CalendarViewM.CalendarObject(event.getID(), event.getDate(), event.getTitle(), event.getColor()));
            Log.d("id", ArrEventList.get(i).getID());
            Log.d("title", ArrEventList.get(i).getTitle());
        }
    }

    //음력 일정 표시
    public void LunarArray(){

        Event event = new Event();

        for(int i = 0; i < lunar_push.size(); i++){
            String Title = lunar_push.get(i)[1];
            String Date = lunar_push.get(i)[2];
            String Id = lunar_push.get(i)[0];

            //캘린더에 일정 저장하는 부분
            mCalendar = Calendar.getInstance();
            int year = Integer.parseInt(Date.substring(0,4));
            int month = Integer.parseInt(Date.substring(5,7))-1;
            int date = Integer.parseInt(Date.substring(8,10));
            int hour = 0;
            int minute = 0;
            String Time = "null";
            if(Time != "null"){
                hour = Integer.parseInt(Time.substring(0,2));
                minute = Integer.parseInt(Time.substring(3,5));
            }else{
                hour = 0;
                minute = 0;
            }
            mCalendar.set(Calendar.HOUR_OF_DAY, hour);
            mCalendar.set(Calendar.MINUTE, minute);
            mCalendar.set(Calendar.SECOND, 0);
            mCalendar.set(Calendar.MILLISECOND, 0);
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, date);

            //캘린더 id 랜덤으로
            //Random rndId = new Random();
            //int Id = rndId.nextInt(3000);
            event.setmID(Id);
            event.setmTitle(Title);
            event.setmColor(R.color.lunar);
            //event.setmDate(Calendar.getInstance());
            event.setmDate(mCalendar);
            //event.setmDate(Calendar.MINUTE, );
            event.setCompleted(false);
            ArrEventList.add(event); // 일간 다이얼로그에 값 넣어줌
            //월간 캘린더 표시에 값 넣어줌
            mCalendarView.addCalendarObject(new com.example.swp1sec.CalendarViewM.CalendarObject(event.getID(), event.getDate(), event.getTitle(), event.getColor()));
            Log.d("id", ArrEventList.get(i).getID());
            Log.d("title", ArrEventList.get(i).getTitle());
        }
    }


    /*public void ArrayRemove(){
        for(int i = 0; i<ArrEventList.size(); i++) {
                //ArrEventList.remove(oldEvent);
                mCalendarView.removeCalendarObjectByID(new CalendarViewM.CalendarObject(String.valueOf(1), ArrEventList.get(i).getDate(), ArrEventList.get(i).getTitle(), ArrEventList.get(i).getColor()));
                Log.d("id", ArrEventList.get(i).getID());

                Log.d("타이틀", ArrEventList.get(i).getTitle());

                Log.d("i번", String.valueOf(i));
                Log.d("번호1", ArrEventList.get(1).getID());
                Log.d("번호1", ArrEventList.get(2).getID());
                Log.d("번호1", ArrEventList.get(2).getID());
                //mCalendarDialog.setEventList(mEventList);
        }

    }*/





}
