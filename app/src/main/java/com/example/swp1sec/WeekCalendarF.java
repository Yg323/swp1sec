package com.example.swp1sec;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.DateTimeInterpreter;
import com.example.library.MonthLoader;
import com.example.library.WeekView;
import com.example.library.WeekViewEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class WeekCalendarF extends AppCompatActivity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    //Week뷰 선언
    private WeekView mWeekView;
    //버튼 및 DB json 내용
    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private Intent intent;
    private String weekjsonString;
    private static String WEEKURL = "http://159.89.193.200//getweek.php";
    private static String TAG = "getweek";

    //리스트로 이벤트 값 저장 변수 선언
    private ArrayList<WeekCalendarSub> eventList;
    private List<WeekViewEvent> myEvents;
    private List<WeekViewEvent> matchedEvents;
    private List<WeekViewEvent> events;
    private ArrayList mNewEvents;
    WeekViewEvent eventt;

    //이메일
    String email;
    String calendar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_calendar);

        mNewEvents = new ArrayList();
        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);

        //옮겨옴
        events = new ArrayList<WeekViewEvent>();
        myEvents = new ArrayList<WeekViewEvent>();
        eventList = new ArrayList<>();

        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist = findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus = findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);


        email =  PreferenceManager.getString(this,"email");
        calendar_title = PreferenceManager.getString(getApplicationContext(),"cal_title");

        WeekCalendarF.GetData weektask = new WeekCalendarF.GetData(); //밑에 만들었던 클래스 만들고

        weektask.execute(WEEKURL, email, calendar_title); //task 실행



        final Toolbar toolbar = findViewById(R.id.toolbar);
        //final TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("주간 캘린더");


        // 액티비티 월간캘린더로 전환
        ImageButton changer = findViewById(R.id.cal_changer);
        changer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 액티비티 전환 코드
                Intent intent = new Intent(getApplicationContext(), CalendarView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });

        ibtn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(WeekCalendarF.this, CalendarView.class);
                startActivity(intent);
            }
        });
        ibtn_calenderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(WeekCalendarF.this, CalendarListActivity.class);
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
                intent = new Intent(WeekCalendarF.this, DayTrackerActivity.class);
                startActivity(intent);
            }
        });
        ibtn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(WeekCalendarF.this, Store_main.class);
                startActivity(intent);
            }
        });




        /*mWeekView.setWeekViewLoader(new WeekViewLoader() {

            int i = 1;
            @Override
            public double toWeekViewPeriodIndex(Calendar instance) {
                return instance.getTime().getDate()+instance.getTime().getDate();
            }

            @Override
            public List<? extends WeekViewEvent> onLoad(int periodIndex) {

                if(i == 3){
                    i = 1;
                    List event = getEventTitle()
                    //List events = getEventListWeek(getActivity(), student.getCOMPANY_ID_VALUE());
                    return eventt;

                }
                else {
                    i++;
                    //empty List
                    List events=new ArrayList();
                    return events;

                }
            }
        });*/



//event.getEndTime().getTimeInMillis() > startTime.getTimeInMillis() &&
//                    event.getStartTime().getTimeInMillis() < endTime.getTimeInMillis()


        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                List<WeekViewEvent> lastone = new ArrayList<WeekViewEvent> ();

                Calendar today = Calendar.getInstance();
                today.setTime(new Date());

                lastone = loadDateFromJson( newYear ,  newMonth);
                events.addAll(lastone);
                ArrayList<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
                for (WeekViewEvent event : events) {
                    if (eventMatches(event, newYear, newMonth)) {
                        matchedEvents.add(event);
                    }
                }
                return matchedEvents;
            }
        });

        //mWeekView.notifyDatasetChanged();

    }


    private boolean eventMatches(WeekViewEvent event, int year, int month) {


        //event.getStartTime().get(Calendar.YEAR) == year &&
        //event.getEndTime().get(Calendar.YEAR) == year &&
        //(event.getEndTime().get(Calendar.MONTH) == month-1)
            return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month-1)
                    ||event.getEndTime().get(Calendar.YEAR) == year && (event.getEndTime().get(Calendar.MONTH) == month-1)


            ;

    }

    /*private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR)
                == year && event.getStartTime().get(Calendar.MONTH) == month - 1)
                || (event.getEndTime().get(Calendar.YEAR)
                == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat("d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + "\n" + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) > 0 ? (hour - 12) + " PM" : 12 + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }
    //이벤트 타이틀
    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }
    protected String getEventTitle(String title){
        return String.format("%s", title);
    }

    //이벤트 클릭시
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Clicked " + event.getmDivision(), Toast.LENGTH_SHORT).show();

        if(event.getmDivision() == 0){

            Intent intent = new Intent(getApplicationContext(),CreateSubjectEdit.class);
            intent.putExtra("eventsub", event);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.stay );

        }
        else if(event.getmDivision() == 1){

            Intent intent = new Intent(getApplicationContext(),CreateExerciseEdit.class);
            intent.putExtra("eventex", event);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.stay );

        }
        else {

            Intent intent = new Intent(getApplicationContext(),CreateNormalEdit.class);
            intent.putExtra("eventnor", event);
            startActivity(intent);
            overridePendingTransition( R.anim.slide_in_up, R.anim.stay );

        }



    }
    //이벤트 롱클릭
    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

        int div = event.getmDivision();
        int id = event.getID();

        if(div == 0){
            div = 0;
            weekcalselect(div, id);

        }
        else if(div == 1){
            div = 1;
            weekcalselect(div, id);

        }
        else if(div == 2){
            div = 2;
            weekcalselect(div, id);

        }

        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();

        //다이얼로그 띄우고 삭제 선택으로.
        //deleteCalendarData(String.valueOf(event.getID()));
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }

    private void deleteCalendarData(final String id, final int division) {
        StringRequest request =new StringRequest(Request.Method.POST, "http://159.89.193.200/deleteCalendardata.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("Data Deleted")){
                            Toast.makeText(WeekCalendarF.this,"Data Deleted Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(WeekCalendarF.this,"Data Not Deleted",Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeekCalendarF.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();
                params.put("id", id);
                params.put("division", String.valueOf(division));
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);

    }




    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
            List<WeekViewEvent> lastone = new ArrayList<WeekViewEvent> ();

            lastone = loadDateFromJson( newYear ,  newMonth);
            events.addAll(lastone);
            ArrayList<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
            for (WeekViewEvent event : events) {
                if (eventMatches(event, newYear, newMonth)) {
                    matchedEvents.add(event);
                }
            }
            return matchedEvents;

    }

    private class GetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(WeekCalendarF.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);


            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                weekjsonString = result; //크롬으로 확인했던 문자열 받아오고
                ShowResult(); //밑에 ShowResult함수 실행
            }
        }

        @Override
        protected String doInBackground(String... params) { //task.excute로 넘겨주는 매개변수들

            String serverURL = params[0]; //PHPURL
            String email = (String)params[1]; //email
            String cal_title = (String)params[2];
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
                Log.d(TAG, "response code - " + responseStatusCode);

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

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void ShowResult() { //이부분 잘봐

        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그
        String TAG_TITLE = "title";
        String TAG_DATE = "date";
        String TAG_TIME = "time";
        String TAG_ENDDATE = "enddate";
        String TAG_ENDTIME = "endtime";
        String TAG_DIVISION = "division";//카테고리 division을 말함.
        String TAG_NDIVISION = "Ndivision";//노멀 일정 divsion을 가져옴.
        String TAG_ID = "id";
        String TAG_COLOR = "color";
        String TAG_MEMO = "memo";
        String TAG_IMPORTANCE = "importance";

        try {
            JSONObject jsonObject = new JSONObject(weekjsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와

            for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                JSONObject item = jsonArray.getJSONObject(i);


                //반복문인점 주의!
                String Title = item.getString(TAG_TITLE); //그럼 거기서 이제 "title"에 해당하는 문자열 값 가져와서 저장
                String Date = item.getString(TAG_DATE);
                String Time = item.getString(TAG_TIME);
                String EndDate = item.getString(TAG_ENDDATE);
                String EndTime = item.getString(TAG_ENDTIME);
                int ID = item.getInt(TAG_ID);


                int division = item.getInt(TAG_DIVISION);
                String Color = item.getString(TAG_COLOR);

                /*String Ndivision = null;
                int iNdivision = 0;

                if(TextUtils.isEmpty(TAG_NDIVISION)){
                    //Ndivision = "3";

                }else
                    Ndivision = item.getString(TAG_NDIVISION);

                iNdivision = Integer.valueOf(Ndivision);*/

                String Memo = item.getString(TAG_MEMO);
                int Importance = item.getInt(TAG_IMPORTANCE);


                //DB 저장
                WeekCalendarSub we = new WeekCalendarSub(Title);

                we.setTitle(Title);
                we.setDate(Date);
                we.setTime(Time);
                we.setEnddate(EndDate);
                we.setEndtime(EndTime);
                we.setColor(Color);
                we.setDivision(division);
                we.setId(ID);
                we.setMemo(Memo);
                we.setStar(Importance);

                //we.setNdivision(iNdivision);

                eventList.add(we);
                mWeekView.notifyDatasetChanged();

            }


        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }


    }


    public  List<WeekViewEvent> loadDateFromJson(int syear , int smonth) {
        for (int i = 0; i < eventList.size(); i++) {

            syear = 0;
            smonth = 0;
            String Title = eventList.get(i).getTitle();
            String StartDate = eventList.get(i).getDate();
            String StartTime = eventList.get(i).getTime();
            String EndDate = eventList.get(i).getEnddate();
            String EndTime = eventList.get(i).getEndtime();
            String Color = eventList.get(i).getColor();

            int Division = eventList.get(i).getDivision();
            int Star = eventList.get(i).getStar();
            String Memo = eventList.get(i).getMemo();
            int ID = eventList.get(i).getId();

            int NDivision = eventList.get(i).getNdivision();

            Random rndId = new Random();
            int Id = rndId.nextInt(3000);



            int shour = 0;
            int sminute = 0;
            syear = Integer.parseInt(StartDate.substring(0,4));
            smonth = Integer.parseInt(StartDate.substring(5,7))-1;
            int sdate = Integer.parseInt(StartDate.substring(8,10));


            int ehour = 0;
            int eminute = 0;
            //int eyear = Integer.parseInt(EndDate.substring(0,4));
            //int emonth = Integer.parseInt(EndDate.substring(5,7))-1;
            //int edate = Integer.parseInt(EndDate.substring(8,10));


            if(!StartTime.equals("null")){
                shour = Integer.parseInt(StartTime.substring(0,2));
                sminute = Integer.parseInt(StartTime.substring(3,5));
            }else{
                shour = 8;
                sminute = 0;
            }

            if(!EndTime.equals("null")){
                ehour = Integer.parseInt(EndTime.substring(0,2));
                eminute = Integer.parseInt(EndTime.substring(3,5));

            }else{
                ehour = shour + 1;
                eminute = sminute;
            }

            if(ehour <= shour){
                ehour = shour + 1;
            }
            /*String Name = eventList.get(i).getTitle();
            //Rand Colors for Events
            Random rand = new Random();
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            int randomColor = Color.rgb(r,g,b);
            if(Color.rgb(r,g,b) == getResources().getColor(R.color.event_color_01)){
                randomColor = getResources().getColor(R.color.event_color_02);
            }*/

            //Set StarTime
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, shour);
            startTime.set(Calendar.MINUTE, sminute);
            startTime.set(Calendar.YEAR, syear);
            startTime.set(Calendar.MONTH, smonth);
            startTime.set(Calendar.DAY_OF_MONTH, sdate);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, ehour);
            endTime.set(Calendar.MINUTE, eminute);
            //endTime.set(Calendar.YEAR, eyear);
            //endTime.set(Calendar.MONTH, emonth);
            //endTime.set(Calendar.DAY_OF_MONTH, edate);
            WeekViewEvent weekViewEvent = new WeekViewEvent(Id, Title, startTime, endTime, Memo);
            weekViewEvent.setColor(android.graphics.Color.parseColor(Color));
            weekViewEvent.setmDivision(Division);
            weekViewEvent.setmMemo(Memo);
            weekViewEvent.setmStar(Star);
            weekViewEvent.setmStartDate(StartDate);
            weekViewEvent.setsTime(StartTime);
            weekViewEvent.setmEndDate(EndDate);
            weekViewEvent.seteTime(EndTime);
            weekViewEvent.setID(ID);

            weekViewEvent.setNdivision(NDivision);
            myEvents.add(weekViewEvent);

            //mWeekView.notifyDatasetChanged();

        }
        return myEvents;
    }

    public void weekcalselect(final int division, final int idd)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("일정 삭제").setMessage("일정을 삭제하시겠습니까?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                deleteCalendarData(String.valueOf(idd), division);
                intent = new Intent(getApplicationContext(),WeekCalendarF.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


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




}
