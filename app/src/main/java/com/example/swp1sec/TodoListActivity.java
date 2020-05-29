package com.example.swp1sec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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


public class TodoListActivity extends AppCompatActivity {
    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private EditText et_search;
    private Button btn_search,btn_callist,btn_todolist,btn_habitlist,btn_ddaylist;
    private Button btn_calalign,btn_importancealign,btn_categoryalign;
    private RecyclerView recy_cal;
    private Intent intent;
    // 일정
    private TodoListAdapter todoListAdapter;
    private ArrayList<TodoList> todoListArrayList;
    private String jsonString;
    private static String URL = "http://159.89.193.200//gettodolist.php";
    private static String TAG = "gettodo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist = findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus = findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);

        btn_search = findViewById(R.id.btn_search);
        et_search = findViewById(R.id.et_search);

        btn_callist = findViewById(R.id.btn_callist);
        btn_todolist = findViewById(R.id.btn_todolist);
        btn_habitlist = findViewById(R.id.btn_habitlist);
        btn_ddaylist = findViewById(R.id.btn_ddaylist);

        btn_calalign = findViewById(R.id.btn_calalign);
        btn_importancealign = findViewById(R.id.btn_importancealign);
        btn_categoryalign = findViewById(R.id.btn_categoryalign);

        ibtn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ibtn_calenderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this, CalendarListActivity.class);
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
                intent = new Intent(TodoListActivity.this, DayTrackerActivity.class);
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
        btn_callist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this, CalendarListActivity.class);
                startActivity(intent);
            }
        });
        btn_todolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this, TodoListActivity.class);
                startActivity(intent);
            }
        });
        btn_habitlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this, HabitListActivity.class);
                startActivity(intent);
            }
        });
        btn_ddaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this, DDayListActivity.class);
                startActivity(intent);
            }
        });
        btn_calalign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this, TodoListActivity.class);
                startActivity(intent);
            }
        });
        btn_importancealign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this, TodoListImActivity.class);
                startActivity(intent);
            }
        });
        btn_categoryalign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this,TodoListCaActivity.class);
                startActivity(intent);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(TodoListActivity.this,SearchActivity.class);
                intent.putExtra("search",et_search.getText().toString());
                startActivity(intent);
            }
        });
        String email = PreferenceManager.getString(TodoListActivity.this, "email");

        //목록 출력
        recy_cal = (RecyclerView) findViewById(R.id.recy_cal);
        LinearLayoutManager calLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recy_cal.setLayoutManager(calLayoutManager);
        todoListArrayList = new ArrayList<>();

        todoListAdapter = new TodoListAdapter(this, todoListArrayList);
        recy_cal.setAdapter(todoListAdapter);

        todoListArrayList.clear();
        todoListAdapter.notifyDataSetChanged();

        TodoListActivity.GetData caltask = new TodoListActivity.GetData(); //밑에 만들었던 클래스 만들고
        caltask.execute(URL, email); //task 실행

        todoListAdapter.setOnCheckedChangeListener(new TodoListAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked, int pos) {
                TodoList todoList = todoListArrayList.get(pos);
                String email = PreferenceManager.getString(TodoListActivity.this, "email");
                String title = todoList.getTitle();
                String cate_title = todoList.getCate_title();
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
                TodoListRequest todoListRequest=new TodoListRequest(email,title,cate_title,Integer.toString(todoList.getDday()),Integer.toString(todoList.getPerformance()),responseListener);
                RequestQueue queue= Volley.newRequestQueue(TodoListActivity.this);
                queue.add(todoListRequest);
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(TodoListActivity.this,
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
                jsonString = result; //크롬으로 확인했던 문자열 받아오고
                ShowResult(); //밑에 dayHabitShowResult함수 실행
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
        String TAG_SUCCESS = "success";
        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그
        String TAG_TITLE = "title";
        String TAG_CATE_TITLE = "cate_title";
        String TAG_DATE = "date";
        String TAG_IMPORTANCE = "importance";
        String TAG_DDAY = "d_day";
        String TAG_PERFORMANCE = "performance";

        try {
            JSONObject jsonObject = new JSONObject(jsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            Boolean success = jsonObject.getBoolean(TAG_SUCCESS);
            if(success) {
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와
                for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                    JSONObject item = jsonArray.getJSONObject(i);
                    //반복문인점 주의!
                    String Title = item.getString(TAG_TITLE); //그럼 거기서 이제 "title"에 해당하는 문자열 값 가져와서 저장
                    String Cate_title = item.getString(TAG_CATE_TITLE);
                    String Date = item.getString(TAG_DATE);
                    int star = item.getInt(TAG_IMPORTANCE);
                    int d_day = item.getInt(TAG_DDAY);
                    int performance = item.getInt(TAG_PERFORMANCE);

                    TodoList todoList = new TodoList(Title);
                    todoList.setCate_title(Cate_title);
                    todoList.setTitle(Title);
                    todoList.setDate(Date);
                    todoList.setStar(star);
                    todoList.setDday(d_day);
                    todoList.setPerformance(performance);

                    todoListArrayList.add(todoList); //받아온값이들어있는 dayHabit 객체들을 ArrayList<DayHabit>에 차례로 집어넣고
                    todoListAdapter.notifyDataSetChanged(); //집어넣었으니까 어댑터한테 값 새로 들어갔다고 알려줌 -> 리사이클러뷰 새로고침
                }
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "일정이 존재하지 않습니다!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                toast.show();
            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
