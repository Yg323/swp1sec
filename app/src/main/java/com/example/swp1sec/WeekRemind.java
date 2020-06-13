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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeekRemind extends AppCompatActivity {
    private Intent intent;
    private Button btn_reweekclose;
    private String TAG = "WeekRemind";
    String now;
    String outPut;
    Date d_now;
    //Calendar date;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
    Calendar cal = Calendar.getInstance();
    ArrayList res_date;
    ArrayList res_title;
    ArrayList res_imp;
    ArrayList<Data> list = new ArrayList<Data>();
    W_Reminder_RecyclerViewAdapter adapter;

    private String Mon_title1 = " ";
    private String Mon_title2 = " ";
    private String Tue_title1 = " ";
    private String Tue_title2 = " ";
    private String Wed_title1 = " ";
    private String Wed_title2 = " ";
    private String Thr_title1 = " ";
    private String Thr_title2 = " ";
    private String Fri_title1 = " ";
    private String Fri_title2 = " ";
    private String Sat_title1 = " ";
    private String Sat_title2 = " ";
    private String Sun_title1 = " ";
    private String Sun_title2 = " ";

    //List<String > listDate = Arrays.asList("월요일","화요일",);

    String url = "http://159.89.193.200/weekreminder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekremind);

        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);
        //date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(currentTime);
        now = date_text;
        d_now = currentTime;

        RecyclerView recyclerView = findViewById(R.id.week_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        adapter = new W_Reminder_RecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);

        NetworkTask networkTask = new NetworkTask(url, null);
        try{
            outPut = networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //Log.d(TAG, "outPut = " + outPut);

        doJSONParser(outPut);
        //Log.d(TAG, "res_date_length= " + res_date.size());
        //title_doJSONParser(outPut);
        //Log.d(TAG, "res_title_length= " + res_title.size());
        //imp_doJSONParser(outPut);
        Log.d(TAG, "res_date= " + res_date);
        List<String> listTitle = Arrays.asList(Mon_title1, Mon_title2, Tue_title1, Tue_title2, Wed_title1, Wed_title2, Thr_title1, Thr_title2, Fri_title1, Fri_title2, Sat_title1, Sat_title2, Sun_title1, Sun_title2);

        if(res_date.size() != 0) {
            //Log.d(TAG, "res_size = " + res_title.size());
            for (int i = 0; i < res_date.size(); i++) {
                Log.d(TAG, "res_date.size= "+res_date.size());
                Log.d(TAG, "title= " + res_date.get(i).toString());
                Log.d(TAG, "time= " + listTitle.get(i).toString());
                addItem(res_date.get(i).toString(), listTitle.get(i).toString(), getDrawable(R.drawable.icon_personsetting), getDrawable(R.drawable.week_imp));
            }
        }else
            addItem(" ", "예정된 일정이 없습니다.", getDrawable(R.drawable.icon_personsetting), getDrawable(R.drawable.week_imp));

        btn_reweekclose = findViewById(R.id.btn_reweekclose);

        btn_reweekclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WeekRemind.this,DayRemind.class);
                startActivity(intent);
            }
        });
    }

    public void addItem(String res_date, String title, Drawable logo, Drawable im_img) {
        Data item = new Data();
        if(item.getContent() == " ") {
            item.setTitle(res_date);
            item.setContent(title);
            item.set_l_ResId(logo);
            item.set_s_ResId(im_img);
            item.set_s_ResId(im_img);

            list.add(item);
        }else {
            item.setTitle(res_date);
            item.setContent1(title);
            item.set_l_ResId(logo);
            item.set_s_ResId(im_img);
            item.set_s_ResId(im_img);

            list.add(item);
        }
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

    /*public void title_doJSONParser(String str){
        try{
            int nWeek = cal.get(Calendar.DAY_OF_WEEK);
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("exercise");
            //Log.d(TAG, "res_date = " + res_date);
            //Log.d(TAG, "P_now = " + now);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String title = tt.getString("title");
                Date set_date = format.parse(title);
                long calDate = d_now.getTime() - set_date.getTime();
                long calDateDays = calDate/(24*60*60*1000);
                if(calDateDays < 7){
                    switch (nWeek){
                        case 1:
                            result.add(date + " 월요일");
                        case 2:
                            result.add(date + " 화요일");
                        case 3:
                            result.add(date + " 수요일");
                        case 4:
                            result.add(date + " 목요일");
                        case 5:
                            result.add(date + " 금요일");
                        case 6:
                            result.add(date + " 토요일");
                        case 7:
                            result.add(date + " 일요일");
                    }
                }
            }
            res_title = result;
            //Log.d(TAG,"res_title = " + res_title);
        }catch (JSONException | ParseException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }*/

    public void doJSONParser(String str){
        try{
            int nWeek = cal.get(Calendar.DAY_OF_WEEK);
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("exercise");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String date = tt.getString("date");
                String title = tt.getString("title");
                Log.d(TAG, "parsing_title= " + title);
                Date set_date = format.parse(date);
                long calDate = d_now.getTime() - set_date.getTime();
                long calDateDays = calDate/(24*60*60*1000);
                Log.d(TAG, "calDateDays = " + calDateDays);
                Log.d(TAG, "set_date.getTime() = " + set_date.getTime());
                if(calDateDays < 7){
                    switch ((int) calDateDays){
                        case 0:
                            result.add(date + " 월요일");
                            if(Mon_title1 == " ")
                                Mon_title1 = title;
                            else
                                Mon_title2 = title;
                            Log.d(TAG, "mon= " + Mon_title1);
                        case 1:
                            result.add(date + " 화요일");
                            if(Tue_title1 == " ")
                                Tue_title1 = title;
                            else
                                Tue_title2 = title;
                            Log.d(TAG, "tue= " + Tue_title1);
                        case 2:
                            result.add(date + " 수요일");
                            if(Wed_title1 == " ")
                                Wed_title1 = title;
                            else
                                Wed_title2 = title;
                            Log.d(TAG, "wed= " + Wed_title1);
                        case 3:
                            result.add(date + " 목요일");
                            if(Thr_title1 == " ")
                                Thr_title1 = title;
                            else
                                Thr_title2 = title;
                            Log.d(TAG, "thr= " + Thr_title1);
                        case 4:
                            result.add(date + " 금요일");
                            if(Fri_title1 == " ")
                                Fri_title1 = title;
                            else
                                Fri_title2 = title;
                            Log.d(TAG, "fri= " + Fri_title1);
                        case 5:
                            result.add(date + " 토요일");
                            if(Sat_title1 == " ")
                                Sat_title1 = title;
                            else
                                Sat_title2 = title;
                            Log.d(TAG, "sat= " + Sat_title1);
                        case 6:
                            result.add(date + " 일요일");
                            if(Sun_title1 == " ")
                                Sun_title1 = title;
                            else
                                Sun_title2 = title;
                            Log.d(TAG, "sun= " + Sun_title1);
                        default:
                            Log.d(TAG, "nWeek_strange_err");
                    }

                }

                result.add(date);

                //Log.d(TAG, "result = " + result);

            }
            res_date = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException | ParseException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }
}
