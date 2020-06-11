package com.example.swp1sec;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayRemind extends AppCompatActivity {
    private Button btn_redayclose;
    D_Reminder_RecylerViewAdapter adapter;
    String TAG = "dayremind";
    String outPut;
    String now;
    ArrayList res_title;
    ArrayList res_time;
    ArrayList res_date;
    ArrayList spend = new ArrayList();
    ArrayList<Data> list = new ArrayList<Data>();

    String url = "http://159.89.193.200/dayreminder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayremind);

        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy-MM-dd일", Locale.getDefault()).format(currentTime);
        now = date_text;

        Log.d(TAG, "now= " + now);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.day_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new D_Reminder_RecylerViewAdapter(list);
        recyclerView.setAdapter(adapter);

        NetworkTask networkTask = new NetworkTask(url, null);
        try{
            outPut = networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }

        date_doJSONParser(outPut);
        title_doJSONParser(outPut);
        time_doJSONParser(outPut);

        for(int i = 0; i < res_title.size(); i++){
            addItem(getDrawable(R.drawable.icon_personsetting), getDrawable(R.drawable.importance), res_title.get(i).toString(), res_time.get(i).toString());
        }

        btn_redayclose = findViewById(R.id.btn_redayclose);
        btn_redayclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayRemind.this, CalendarView.class);
                startActivity(intent);
            }
        });
    }

    public void addItem(Drawable logo, Drawable im_img, String title, String content) {
        Data item = new Data();

        item.set_l_ResId(logo);
        item.set_s_ResId(im_img);
        item.setTitle(title);
        item.setContent("X" + content);

        list.add(item);
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;
        private String tv_outPut;
        private static final String TAG = "networktask";

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            //Log.d(TAG, "url = " + url);
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            //Log.d(TAG, "result = " + result);

            return result;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            tv_outPut = new String();
            //Log.d(TAG, "response = " + s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //tv_outPut.setText(s);
            //doJSONParser(s);
            tv_outPut = s;
            //Log.d(TAG, "tv_output = " + tv_outPut);
        }
    }

    public void title_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("exercise");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                if(res_date.get(i).toString() == now) {
                    JSONObject tt = index.getJSONObject(i);
                    String title = tt.getString("title");

                    result.add(i, title);

                    //Log.d(TAG, "result = " + result);
                }
            }
            res_title = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }

    public void time_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("exercise");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                if(res_date.get(i).toString() == now) {
                    JSONObject tt = index.getJSONObject(i);
                    String time = tt.getString("time");

                    result.add(i, time);

                    //Log.d(TAG, "result = " + result);
                }
            }
            res_time = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }

    public void date_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("spend");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String title = tt.getString("date");

                result.add(i, title);

                //Log.d(TAG, "result = " + result);

            }
            res_date = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }
}