package com.example.swp1sec;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class SearchActivity extends AppCompatActivity {
    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private EditText et_search;
    private Button btn_search,btn_callist,btn_todolist,btn_habitlist,btn_ddaylist;
    private RecyclerView recy_search;
    private Intent intent;
    // 리사이클러뷰
    private SearchAdapter searchAdapter;
    private ArrayList<SearchItem> searchArrayList;
    private String jsonString;
    private static String URL = "http://159.89.193.200//search.php";
    private static String TAG = "get";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent searchintent = getIntent();
        String search = searchintent.getExtras().getString("search");

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

        recy_search = findViewById(R.id.recy_search);

        ibtn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SearchActivity.this, CalendarView.class);
                startActivity(intent);
            }
        });
        ibtn_calenderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SearchActivity.this, CalendarListActivity.class);
                startActivity(intent);
            }
        });
        ibtn_calenderplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calclick();
            }
        });
        ibtn_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SearchActivity.this, DayTrackerActivity.class);
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
                intent = new Intent(SearchActivity.this, CalendarListActivity.class);
                startActivity(intent);
            }
        });
        btn_todolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SearchActivity.this, TodoListActivity.class);
                startActivity(intent);
            }
        });
        btn_habitlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SearchActivity.this, HabitListActivity.class);
                startActivity(intent);
            }
        });
        btn_ddaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SearchActivity.this, DDayListActivity.class);
                startActivity(intent);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SearchActivity.this, SearchActivity.class);
                intent.putExtra("search",et_search.getText().toString());
                startActivity(intent);
            }
        });
        String email = PreferenceManager.getString(SearchActivity.this, "email");
        // 일정 목록 출력
        recy_search = (RecyclerView) findViewById(R.id.recy_search);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recy_search.setLayoutManager(LayoutManager);
        searchArrayList = new ArrayList<>();

        searchAdapter = new SearchAdapter(this, searchArrayList);
        recy_search.setAdapter(searchAdapter);

        searchArrayList.clear();
        searchAdapter.notifyDataSetChanged();

        SearchActivity.GetData task = new SearchActivity.GetData(); //밑에 만들었던 클래스 만들고
        task.execute(URL, email, search); //task 실행

    }

    private class GetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SearchActivity.this,
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
            String search = (String)params[2]; //search

            String postParameters = "email=" + email + "&" + "search=" + search; //php 파일에 $_POST 변수가 받기 위한 코드


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


                    SearchItem searchItem = new SearchItem(Title);
                    searchItem.setCate_title(Cate_title);
                    searchItem.setTitle(Title);
                    searchItem.setDate(Date);

                    searchArrayList.add(searchItem); //받아온값이들어있는 dayHabit 객체들을 ArrayList<DayHabit>에 차례로 집어넣고
                    searchAdapter.notifyDataSetChanged(); //집어넣었으니까 어댑터한테 값 새로 들어갔다고 알려줌 -> 리사이클러뷰 새로고침
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
    public void calclick(){

        AlertDialog.Builder dlg = new AlertDialog.Builder(SearchActivity.this);
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
}
