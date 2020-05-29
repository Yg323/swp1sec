package com.example.swp1sec;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeekCalendar extends BaseActivity {

    private ArrayList<CategoryItem> categoryArrayList;
    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private Intent intent;
    private String catejsonString;
    private static String URL = "http://159.89.193.200//getweek.php";
    private static String TAG = "getweek";


    private com.example.mylibrary.CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        //시작 시간 기억
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.DATE, 0,0);
        startTime.set(Calendar.MINUTE, 0);
        //startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, 2020);
        //끝나는 시간 기억
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth - 1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3); //시
        startTime.set(Calendar.MINUTE, 30); // 분
        startTime.set(Calendar.MONTH, newMonth-1); //현재달-1?
        startTime.set(Calendar.YEAR, newYear); //현재 년
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 4); //시
        endTime.set(Calendar.MINUTE, 30); // 분
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime); // 함수선언 id??
        event.setColor(getResources().getColor(R.color.event_color_02)); // 색상지정
        events.add(event); //array에 기억

        // All day event until 00:00 next day
        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 10);
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, 11);
        event = new WeekViewEvent(8, getEventTitle(startTime), null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        return events;
    }



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
        String TAG_DIVISION = "division";

        try {
            JSONObject jsonObject = new JSONObject(catejsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와

            for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                JSONObject item = jsonArray.getJSONObject(i);
                //반복문인점 주의!
                String Title = item.getString(TAG_TITLE); //그럼 거기서 이제 "title"에 해당하는 문자열 값 가져와서 저장
                String Date = item.getString(TAG_DATE);
                String Time = item.getString(TAG_TIME);
                int division = item.getInt(TAG_DIVISION);

                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Date));
                startTime.set(Calendar.HOUR, Integer.parseInt(Time));
                startTime.set(Calendar.MINUTE, Integer.parseInt(Time));
                Calendar endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Time) + 1); //시
                endTime.set(Calendar.MINUTE, Integer.parseInt(Time) + 30); // 분
                WeekViewEvent event = new WeekViewEvent(1, getEventTitle(Title), startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_01));

                //categoryArrayList.add(categoryItem); //받아온값이들어있는 dayHabit 객체들을 ArrayList<DayHabit>에 차례로 집어넣고

            }


        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
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
