package com.example.swp1sec;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

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
import java.util.List;
import java.util.Random;

public class WeekCalendar extends BaseActivity {

    private ArrayList<WeekViewEvent> mevents;
    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private Intent intent;
    private String catejsonString;
    private static String URL = "http://159.89.193.200//getweek.php";
    private static String TAG = "gettweek";


    //실험
    private WeekView mWeekView;
    List<WeekViewEvent> myevents = new ArrayList<>();
    private List<WeekViewEvent> mNewEvents;
    private List<WeekViewEvent> myEvents;
    private long mEeventId;

    //OBJECTS
    private ArrayList<WeekCalendarSub> eventList;
    private List<WeekViewEvent> events;
    private List<WeekViewEvent> matchedEvents;


    //int year;
    //int month, date, hour, minute;


    private com.example.mylibrary.CalendarViewM mCalendarView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWeekView = (WeekView) findViewById(R.id.weekView);


        mevents = new ArrayList<>();
        eventList = new ArrayList<>();
        events = new ArrayList<WeekViewEvent>();
        myEvents = new ArrayList<WeekViewEvent>();

        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist = findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus = findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);

        //String email = PreferenceManager.getString(Category.this, "email");
        String email = "14dnfnfn@gmail.com";//임시
        GetData weektask = new GetData(); //밑에 만들었던 클래스 만들고
        weektask.execute(URL, email); //task 실행



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
                intent = new Intent(WeekCalendar.this, CalendarView.class);
                startActivity(intent);
            }
        });
        ibtn_calenderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(WeekCalendar.this, CalendarListActivity.class);
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
                intent = new Intent(WeekCalendar.this, DayTrackerActivity.class);
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


        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
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
        });


    }


    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }



    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {


        //List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        //showEvents(newMonth, newYear);

        matchedEvents = new ArrayList<>();

        //Calendar startTime = null;
        //Calendar endTime = null;

        for (int i = 0; i < eventList.size(); i++) {
            //startCal = Calendar.getInstance();
            //endCal = (Calendar) startCal.clone();

            String Title = eventList.get(i).getTitle();
            int StartYear = eventList.get(i).getStartyear();
            int StartMonth = eventList.get(i).getStartmonth();
            int StartDay = eventList.get(i).getStartday();
            int StartHour = eventList.get(i).getStarthour();
            int StartMinute = eventList.get(i).getStartminute();
            int EndHour = eventList.get(i).getEndhour();
            int EndMinute = eventList.get(i).getEndminute();

            String StartDate = eventList.get(i).getDate();
            String StartTime = eventList.get(i).getTime();
            String EndDate = eventList.get(i).getEnddate();
            String EndTime = eventList.get(i).getEndtime();


            /*Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(StartTime));
            startTime.set(Calendar.MINUTE, Integer.parseInt(StartTime));
            startTime.set(Calendar.YEAR, Integer.parseInt(StartDate));
            startTime.set(Calendar.MONTH, Integer.parseInt(StartDate));
            startTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(StartDate));
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(EndTime));
            endTime.set(Calendar.MINUTE, Integer.parseInt(EndTime));

            WeekViewEvent event = new WeekViewEvent(1, Title, startTime, endTime);
            event.setColor(R.color.event_color_01);
            //mevents.add(weekViewEvent);
            events.add(event);*/


            /*//시작 시간 기억
            Calendar startTime = Calendar.getInstance();
            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(StartTime.substring(0,2)));
            startTime.set(Calendar.MINUTE, Integer.parseInt(StartTime.substring(3,5)));
            startTime.set(Calendar.YEAR, Integer.parseInt(StartDate.substring(0,4)));
            startTime.set(Calendar.MONTH, Integer.parseInt(StartDate.substring(5,7)));
            startTime.set(Calendar.DAY_OF_MONTH,  Integer.parseInt(StartDate.substring(8,10)));
            //끝나는 시간 기억
            Calendar endTime = (Calendar) startTime.clone();
            endTime = (Calendar) startTime.clone();
            //endTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(EndTime.substring(0,2))); //시
            //endTime.set(Calendar.MINUTE, Integer.parseInt(EndTime.substring(3,5))); // 분
            //endTime.set(Calendar.MONTH, Integer.parseInt(EndTime.substring(5,7)));
            WeekViewEvent event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_02)); // 색상지정
            //events.add(event);*/

        }
        mWeekView.notifyDatasetChanged();

        return matchedEvents;
    }


    private void showEvents(int month, int year) {
        int idset = 0;
        int c = 0;

        matchedEvents = new ArrayList<>();

        Calendar startCal = null;
        Calendar endCal = null;

        for (int i = 0; i < eventList.size(); i++) {
            startCal = Calendar.getInstance();
            endCal = (Calendar) startCal.clone();

            String Title = eventList.get(i).getTitle();
            int StartYear = eventList.get(i).getStartyear();
            int StartMonth = eventList.get(i).getStartmonth();
            int StartDay = eventList.get(i).getStartday();
            int StartHour = eventList.get(i).getStarthour();
            int StartMinute = eventList.get(i).getStartminute();
            int EndHour = eventList.get(i).getEndhour();
            int EndMinute = eventList.get(i).getEndminute();


            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, StartHour);
            startTime.set(Calendar.MINUTE, StartMinute);
            startTime.set(Calendar.YEAR, StartYear);
            startTime.set(Calendar.MONTH, StartMonth);
            startTime.set(Calendar.DAY_OF_MONTH, StartDay);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, EndHour);
            endTime.set(Calendar.MINUTE, EndMinute);

            WeekViewEvent event = new WeekViewEvent(1, Title, startTime, endTime);
            event.setColor(R.color.event_color_01);
            //mevents.add(weekViewEvent);




            if (!events.contains(event)) {
                Log.d("Event: ", event.getName());
                events.add(event);
            }

            for (WeekViewEvent we : events) {
                if (eventMatches(we, year, month)) {
                    if (!matchedEvents.contains(we)) {
                        Log.d("we: ", we.getName());
                        matchedEvents.add(we);
                    }
                }
            }

            startCal = null;
            endCal = null;
            event = null;

        }

        mWeekView.notifyDatasetChanged();

    }





    /*private ArrayList<WeekViewEvent> getNewEvents(int year,int month){
        // Get the starting point and ending point of the given month. We need this to find the
        // events of the given month

        WeekCalendarSub weekCalendarSub = new WeekCalendarSub();

        /*int shour = Integer.parseInt(weekCalendarSub.getTime().substring(0,2));
        int sminute = Integer.parseInt(weekCalendarSub.getTime().substring(3,5));
        int smonth = Integer.parseInt(weekCalendarSub.getDate().substring(5,7));
        int syear = Integer.parseInt(weekCalendarSub.getDate().substring(8,10));

        int ehour = Integer.parseInt(weekCalendarSub.getTime().substring(0,2));
        int eminute = Integer.parseInt(weekCalendarSub.getTime().substring(3,5));
        int emonth = Integer.parseInt(weekCalendarSub.getDate().substring(5,7));
        int eyear = Integer.parseInt(weekCalendarSub.getDate().substring(8,10));

        Event event = new Event();
        Date start = new Date();
        Date end = new Date();

        //start = event.getStartEvent();
        //end = event.getEndEvent();

        Calendar startTime = Calendar.getInstance();
        startTime.setTime(start);
        startTime.set(Calendar.YEAR, startTime.get(Calendar.YEAR));
        startTime.set(Calendar.MONTH, startTime.get(Calendar.MONTH));
        startTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.HOUR_OF_DAY,startTime.get(Calendar.HOUR_OF_DAY));
        startTime.set(Calendar.MINUTE,startTime.get(Calendar.MINUTE));
        Calendar endTime = (Calendar) startTime.clone();
        endTime.setTime(end);
        endTime.set(Calendar.YEAR, startTime.get(Calendar.YEAR));
        endTime.set(Calendar.MONTH, startTime.get(Calendar.MONTH));
        endTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH));
        endTime.set(Calendar.HOUR_OF_DAY,endTime.get(Calendar.HOUR_OF_DAY));
        endTime.set(Calendar.MINUTE,endTime.get(Calendar.MINUTE));


        // Create a new event.
        WeekViewEvent eventing = new WeekViewEvent(weekCalendarSub.getId(),"New event",startTime,endTime);
        mNewEvents.add(eventing);

        ArrayList<WeekViewEvent> events = new ArrayList<>();
        /*for(WeekViewEvent evented:mNewEvents) {
            if (event.getEndTime().getTimeInMillis() > startTime.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endTime.getTimeInMillis()) {
                events.add(evented);
            }
        }
        return events;
    }*/



    private class GetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(WeekCalendar.this,
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
                catejsonString = result; //크롬으로 확인했던 문자열 받아오고
                ShowResult(); //밑에 ShowResult함수 실행
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
        String TAG_DIVISION = "division";
        String TAG_ID = "id";

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfD = new SimpleDateFormat("yy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfT = new SimpleDateFormat("HH:mm");

        try {
            JSONObject jsonObject = new JSONObject(catejsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와

            for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                JSONObject item = jsonArray.getJSONObject(i);


                //반복문인점 주의!
                String Title = item.getString(TAG_TITLE); //그럼 거기서 이제 "title"에 해당하는 문자열 값 가져와서 저장
                String Date = item.getString(TAG_DATE);
                String Time = item.getString(TAG_TIME);
                String EndDate = item.getString(TAG_ENDDATE);
                String EndTime = item.getString(TAG_ENDTIME);
                //int ID = item.getInt(TAG_ID);
                //int division = item.getInt(TAG_DIVISION);

                /*Calendar cal = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                cal.set(Calendar.YEAR, Integer.parseInt(Date.substring(0,4)));
                cal.set(Calendar.MONTH, Integer.parseInt(Date.substring(5,7)));
                cal.set(Calendar.DATE, Integer.parseInt(Date.substring(8,10)));

                cal.set(Calendar.HOUR, Integer.parseInt(Time.substring(0,2)));
                cal.set(Calendar.MINUTE, Integer.parseInt(Time.substring(3,4)));

                cal2.set(Calendar.YEAR, Integer.parseInt(EndDate.substring(0,4)));
                cal2.set(Calendar.MONTH, Integer.parseInt(EndDate.substring(5,7)));
                cal2.set(Calendar.DATE, Integer.parseInt(EndDate.substring(8,10)));

                cal2.set(Calendar.HOUR, Integer.parseInt(EndTime.substring(0,2)));
                cal2.set(Calendar.MINUTE, Integer.parseInt(EndTime.substring(3,4)));

                int syear = cal.get(Calendar.YEAR);
                int smonth = cal.get(Calendar.MONTH) + 1;
                int sdate = cal.get(Calendar.DATE);
                int shour = cal.get(Calendar.HOUR);
                int sminute = cal.get(Calendar.MINUTE);

                int ehour = cal2.get(Calendar.HOUR);
                int eminute = cal2.get(Calendar.MINUTE);*/

                //실험


                /*Date dfj = sdfD.parse(Date);
                int Day = dfj.getDate();
                int Month = dfj.getMonth() - 0;
                int Year = dfj.getYear();
                //set time
                Date tfj = sdfT.parse(Time);
                int Hour = tfj.getHours();
                int Minute = tfj.getMinutes();
                //String endTimeP = EndTime;
                //int endTimePeriod = Integer.valueOf(endTimeP);
                Date etfj = sdfT.parse(EndTime);
                assert etfj != null;
                int EndHour = etfj.getHours();
                int EndMinute = etfj.getMinutes();*/

                //set name
                String Name = Title;
                //Rand Colors for Events
                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                int randomColor = Color.rgb(r,g,b);
                if(Color.rgb(r,g,b) == getResources().getColor(R.color.event_color_01)){
                    randomColor = getResources().getColor(R.color.event_color_02);
                }

                //DB 저장
                WeekCalendarSub we = new WeekCalendarSub(Title);

                //we.setStartyear(Year);
                //we.setStartmonth(Month);
                //we.setStartday(Day);
                //we.setStarthour(Hour);
               // we.setStartminute(Minute);
               // we.setEndhour(EndHour);
               // we.setEndminute(EndMinute);

                we.setTitle(Title);
                we.setDate(Date);
                we.setTime(Time);
                we.setEnddate(EndDate);
                we.setEndtime(EndTime);

                eventList.add(we);


                /*//Set StarTime
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, Hour);
                startTime.set(Calendar.MINUTE, Minute);
                startTime.set(Calendar.YEAR, Year);
                startTime.set(Calendar.MONTH, Month);
                startTime.set(Calendar.DAY_OF_MONTH, Day);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, EndHour);
                endTime.set(Calendar.MINUTE, EndMinute);

                WeekViewEvent weekViewEvent = new WeekViewEvent(1, Name, startTime, endTime);
                weekViewEvent.setColor(randomColor);
                mevents.add(weekViewEvent);



                /*WeekCalendarSub weekCalendarSub = new WeekCalendarSub(Title);
                weekCalendarSub.setTitle(Title);
                weekCalendarSub.setDate(Date);
                weekCalendarSub.setTime(Time);
                weekCalendarSub.setEnddate(EndDate);
                weekCalendarSub.setEndtime(EndTime);
                weekCalendarSub.setId(ID);

                weekCalendarSubArrayList.add(weekCalendarSub);*/

                //categoryArrayList.add(categoryItem); //받아온값이들어있는 dayHabit 객체들을 ArrayList<DayHabit>에 차례로 집어넣고

            }


        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        } //catch (ParseException e) {
            //e.printStackTrace();
        //}


    }


    public  List<WeekViewEvent> loadDateFromJson(int year , int month) {
        for (int i = 0; i < eventList.size(); i++) {


            String Title = eventList.get(i).getTitle();
            String StartDate = eventList.get(i).getDate();
            String StartTime = eventList.get(i).getTime();
            String EndDate = eventList.get(i).getEnddate();
            String EndTime = eventList.get(i).getEndtime();

            int Id = eventList.get(i).getId();

            int shour = Integer.parseInt(StartTime.substring(0,2));
            int sminute = Integer.parseInt(StartTime.substring(3,5));
            int syear = Integer.parseInt(StartDate.substring(0,4));
            int smonth = Integer.parseInt(StartDate.substring(5,7))-1;
            int sdate = Integer.parseInt(StartDate.substring(8,10));


            int ehour = Integer.parseInt(EndTime.substring(0,2));
            int eminute = Integer.parseInt(EndTime.substring(3,5));
            int emonth = Integer.parseInt(EndDate.substring(5,7));
            int edate = Integer.parseInt(EndDate.substring(8,10));


            /*Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, StartHour);
            startTime.set(Calendar.MINUTE, StartMinute);
            startTime.set(Calendar.YEAR, StartYear);
            startTime.set(Calendar.MONTH, StartMonth);
            startTime.set(Calendar.DAY_OF_MONTH, StartDay);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, EndHour);
            endTime.set(Calendar.MINUTE, EndMinute);

            WeekViewEvent event = new WeekViewEvent(1, Title, startTime, endTime);
            event.setColor(R.color.event_color_01);
            mevents.add(weekViewEvent);*/

            String Name = eventList.get(i).getTitle();
            //Rand Colors for Events
            Random rand = new Random();
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            int randomColor = Color.rgb(r,g,b);
            if(Color.rgb(r,g,b) == getResources().getColor(R.color.event_color_01)){
                randomColor = getResources().getColor(R.color.event_color_02);
            }
            //Set StarTime
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, shour);
            startTime.set(Calendar.MINUTE, sminute);
            startTime.set(Calendar.YEAR, year);
            startTime.set(Calendar.MONTH, smonth);
            startTime.set(Calendar.DAY_OF_MONTH, sdate);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, ehour);
            endTime.set(Calendar.MINUTE, eminute);
            WeekViewEvent weekViewEvent = new WeekViewEvent(Id, Name, startTime, endTime);
            weekViewEvent.setColor(randomColor);
            myEvents.add(weekViewEvent);

        }
        return myEvents;
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
