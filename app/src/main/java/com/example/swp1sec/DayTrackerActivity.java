package com.example.swp1sec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class DayTrackerActivity extends AppCompatActivity {
    // XML
    private RecyclerView todoRecyclerView, habitRecyclerView;
    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private Button btn_daytracker, btn_monthtracker;
    private TextView txt_todopercent,txt_habitpercent,txt_dayfeedback,txt_average;
    private Intent intent;
    // 할일 리사이클러뷰
    private TodoAdapter toDoAdapter;
    private ArrayList<Todo> todoArrayList;
    private String todojsonString;
    private static String TODOURL = "http://159.89.193.200//todotracker.php";
    private static String TODOTAG = "gettodo";


    // 습관 리사이클러뷰
    private DayHabitAdapter dayHabitAdapter;
    private ArrayList<DayHabit> habitArrayList;
    private String habitjsonString;
    private static String HABITURL = "http://159.89.193.200//habitdaytracker.php";
    private static String HABITTAG = "gethabit";


    private int todoper=0;
    private int dayhabitper=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daytracker);
        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist = findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus = findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);

        btn_daytracker = findViewById(R.id.btn_daytracker);
        btn_monthtracker = findViewById(R.id.btn_monthtracker);

        txt_todopercent=findViewById(R.id.txt_todopercent);
        txt_habitpercent=findViewById(R.id.txt_habitpercent);
        txt_average = findViewById(R.id.txt_average);

        txt_dayfeedback=findViewById(R.id.txt_dayfeedback);

        String email = PreferenceManager.getString(DayTrackerActivity.this, "email");

        ibtn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DayTrackerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ibtn_calenderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DayTrackerActivity.this, CalendarListActivity.class);
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
                intent = new Intent(DayTrackerActivity.this, DayTrackerActivity.class);
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

        btn_daytracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DayTrackerActivity.this, DayTrackerActivity.class);
                startActivity(intent);
            }
        });
        btn_monthtracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DayTrackerActivity.this, MonthTrackerActivity.class);
                startActivity(intent);
            }
        });

        // 할 일 목록 출력

        todoRecyclerView = (RecyclerView) findViewById(R.id.recy_todo);
        LinearLayoutManager todoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        todoRecyclerView.setLayoutManager(todoLayoutManager);
        todoArrayList = new ArrayList<>();
        toDoAdapter = new TodoAdapter(this,todoArrayList);
        todoRecyclerView.setAdapter(toDoAdapter);
        todoArrayList.clear();
        toDoAdapter.notifyDataSetChanged();
        TodoGetData todotask = new TodoGetData(); //밑에 만들었던 클래스 만들고
        todotask.execute(TODOURL, email); //task 실행



        // 습관 목록 출력
        habitRecyclerView = (RecyclerView) findViewById(R.id.recy_habit);
        LinearLayoutManager habitLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        habitRecyclerView.setLayoutManager(habitLayoutManager);
        habitArrayList = new ArrayList<>();

        dayHabitAdapter = new DayHabitAdapter(this, habitArrayList);
        habitRecyclerView.setAdapter(dayHabitAdapter);

        habitArrayList.clear();
        dayHabitAdapter.notifyDataSetChanged();

        DayHabitGetData habittask = new DayHabitGetData(); //밑에 만들었던 클래스 만들고
        habittask.execute(HABITURL, email); //task 실행



    }
    // 할일 리사이클러뷰
    private class TodoGetData extends AsyncTask<String, Void, String> {
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(DayTrackerActivity.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(HABITTAG, "response - " + result);

            if (result == null){

            }
            else {
                todojsonString = result; //크롬으로 확인했던 문자열 받아오고
                toDoShowResult(); //밑에 dayHabitShowResult함수 실행
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
                Log.d(TODOTAG, "response code - " + responseStatusCode);

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

                Log.d(TODOTAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void toDoShowResult() { //이부분 잘봐

        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그
        String TAG_SUCCESS = "success";
        String TAG_TITLE = "title";
        String TAG_PERFORMANCE = "performance";
        String TAG_CATEGORY_TITLE = "category_title";

        try {
            JSONObject jsonObject = new JSONObject(todojsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            boolean success = jsonObject.getBoolean(TAG_SUCCESS);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와
            if(success) {
                for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                    JSONObject item = jsonArray.getJSONObject(i);
                    //반복문인점 주의!
                    String Title = item.getString(TAG_TITLE); //그럼 거기서 이제 "title"에 해당하는 문자열 값 가져와서 저장
                    int performance = item.getInt(TAG_PERFORMANCE);
                    String categorytitle = item.getString(TAG_CATEGORY_TITLE);
                    Todo todo = new Todo(Title);
                    todo.setTitle(categorytitle + "_" + Title);
                    todo.setPerformance(performance);

                    todoArrayList.add(todo); //받아온값이들어있는 dayHabit 객체들을 ArrayList<DayHabit>에 차례로 집어넣고
                    toDoAdapter.notifyDataSetChanged(); //집어넣었으니까 어댑터한테 값 새로 들어갔다고 알려줌 -> 리사이클러뷰 새로고침
                }
                // 습관 퍼센트 출력
                todoper = jsonObject.getInt("percent");
                txt_todopercent.setText("오늘 할 일 완료도는 " + todoper + "%입니다.");
            }
            else{
                txt_todopercent.setText("오늘 할 일이 존재하지 않습니다!");
            }

        } catch (JSONException e) {
            Log.d(TODOTAG, "showResult : ", e);
        }
    }

    // 습관 리사이클러뷰
    private class DayHabitGetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(DayTrackerActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(HABITTAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                habitjsonString = result; //크롬으로 확인했던 문자열 받아오고
                dayHabitShowResult(); //밑에 dayHabitShowResult함수 실행
                String percent = Integer.toString((dayhabitper + todoper)/2);
                txt_average.setText("오늘의 수행도는 "+percent+"%입니다!");
                switch ((dayhabitper + todoper) / 20){
                    case 0: case 1:
                        txt_dayfeedback.setText("분발하세요!");
                        break;
                    case 2: case 3:
                        txt_dayfeedback.setText("조금만 더 힘내볼까요");
                        break;
                    case 4: case 5:
                        txt_dayfeedback.setText("반은 했어요!");
                        break;
                    case 6: case 7:
                        txt_dayfeedback.setText("잘 할 수 있어요! 화이팅! ");
                        break;
                    case 8: case 9:
                        txt_dayfeedback.setText("고지가 코앞이에요! 조금더 수행해볼까요?");
                        break;
                    default:
                        txt_dayfeedback.setText("완벽합니다! 오늘 하루도 수고 많으셨어요!");
                }

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
                Log.d(HABITTAG, "response code - " + responseStatusCode);

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

                Log.d(HABITTAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void dayHabitShowResult() { //이부분 잘봐

        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그
        String TAG_TITLE = "title";
        String TAG_PERFORMANCE = "performance";

        try {
            JSONObject jsonObject = new JSONObject(habitjsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와

            for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                JSONObject item = jsonArray.getJSONObject(i);
                //반복문인점 주의!
                String Title = item.getString(TAG_TITLE); //그럼 거기서 이제 "title"에 해당하는 문자열 값 가져와서 저장
                int performance = item.getInt(TAG_PERFORMANCE);
                DayHabit dayHabit = new DayHabit(Title);
                dayHabit.setTitle(Title);
                dayHabit.setPerformance(performance);

                habitArrayList.add(dayHabit); //받아온값이들어있는 dayHabit 객체들을 ArrayList<DayHabit>에 차례로 집어넣고
                dayHabitAdapter.notifyDataSetChanged(); //집어넣었으니까 어댑터한테 값 새로 들어갔다고 알려줌 -> 리사이클러뷰 새로고침
            }
            // 습관 퍼센트 출력
            dayhabitper = jsonObject.getInt("percent");
            txt_habitpercent.setText("오늘 습관 완료도는 "+ dayhabitper +"%입니다.");


        } catch (JSONException e) {
            Log.d(HABITTAG, "showResult : ", e);
        }
    }
}

