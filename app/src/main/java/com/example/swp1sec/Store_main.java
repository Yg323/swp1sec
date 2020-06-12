package com.example.swp1sec;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Store_main extends AppCompatActivity {

    String TAG = "MainActivity";
    private ImageButton ibtn_calender, ibtn_calenderlist, ibtn_calenderplus, ibtn_tracker, ibtn_store;
    private Intent intent1;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Purchase perchase;
    History history;
    Fragment p_frag;
    Fragment h_frag;
    TextView txtResult ;
    TextView textView2 ;
    TextView textView3 ;
    Button btn01;
    String outPut;
    String res;
    String result;
    TextView Coin;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_main);

        fragmentManager = getSupportFragmentManager();
        perchase = new Purchase();
        history = new History();
        String url = "http://159.89.193.200/get_money.php";

        email = PreferenceManager.getString(Store_main.this, "email");
        Log.d(TAG, "email= " + email);

        Button p_button = findViewById(R.id.p_button);
        Button h_button = findViewById(R.id.h_button);
        Coin = findViewById(R.id.coin);

        ibtn_calender = findViewById(R.id.ibtn_calendar);
        ibtn_calenderlist = findViewById(R.id.ibtn_calendarlist);
        ibtn_calenderplus = findViewById(R.id.ibtn_calendarplus);
        ibtn_tracker = findViewById(R.id.ibtn_tracker);
        ibtn_store = findViewById(R.id.ibtn_store);

        final Intent intent = getIntent();

        //InsertData task = new InsertData();
        //task.execute(url, email);

        NetworkTask networkTask = new NetworkTask(url, null);
        Log.d(TAG, "mlllll= " + url);
        //networkTask.execute(url,email);
        try{
            outPut = networkTask.execute(url, email).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //outPut = networkTask.getTv_outPut();
        //String ex_title = tv_outPut;
        Log.d(TAG,"outPut: "+ result);

        money_doJSONParser(result);
        Log.d(TAG, "money = " + res);
        Coin.setText(res);

        p_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, perchase).commit();
            }
        });

        h_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, history).commit();
            }
        });
        ibtn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent(Store_main.this, CalendarView.class);
                startActivity(intent1);
            }
        });
        ibtn_calenderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent(Store_main.this, CalendarListActivity.class);
                startActivity(intent1);
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
                intent1 = new Intent(Store_main.this, DayTrackerActivity.class);
                startActivity(intent1);
            }
        });
        ibtn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1=new Intent(Store_main.this, Store_main.class);
                startActivity(intent1);
            }
        });
    }

    public class NetworkTask extends AsyncTask<String , Void, String> {

        private String url;
        private ContentValues values;
        private String tv_outPut;
        private static final String TAG = "networktask";
        String errorString = null;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
            Log.d(TAG, "Nurl= " + url);
        }

        @Override
        protected String doInBackground(String ... params) {
            String severurl = (String)params[0];
            String email = (String)params[1];
            // 요청 결과를 저장할 변수.7

            String postParameters = "emaill=" + email;
            //Log.d(TAG, "postparams= " + postParameters);

            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            //Log.d(TAG, "url = " + url);
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d(TAG, "result = " + result);

            try { //여기부턴 php코드 한줄씩 읽는거니까 그냥 읽기만 해봐

                java.net.URL url = new URL(severurl);
                Log.d(TAG, "url- " +url);
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

                //return sb.toString().trim();
            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                //return null;
            }

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

    public void money_doJSONParser(String str){
        try{
            String result = new String();
            int rem_money = 0;
            int h_money = 0;
            int m_money = 0;
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("money");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String money = tt.getString("money");
                rem_money = Integer.parseInt(money);
                Log.d(TAG, "rem= " + rem_money);
                while(rem_money > 60){
                    if(rem_money >= 3600){
                        h_money = rem_money/3600;
                        rem_money = rem_money%3600;
                    }else{
                        m_money = rem_money/60;
                        rem_money = rem_money%60;
                    }
                }

                result = h_money + "시간 " + m_money + "분 " + rem_money + "초";

                //Log.d(TAG, "result = " + result);

            }
            res = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }

    public void onFragmentChange(int index){
        if(index == 0 ){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, perchase).commit();
        }else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, history).commit();
        }
    }
    public void calclick(){

        AlertDialog.Builder dlg = new AlertDialog.Builder(Store_main.this);
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

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Log.d(TAG, "Yohoho" + result);
        }

        @Override
        protected String doInBackground(String...params){
            String severurl = (String)params[0];
            String email = (String)params[1];
            String postParameters;

            postParameters = "email=" +email;

            //postParameters = "normal=" + Integer.toString(n_theme1) + "&premium=" + Integer.toString(p_theme1);

            Log.d(TAG, "postparam = " + postParameters);

            try {

                URL url = new URL(severurl);
                Log.d(TAG, "url= " +url);
                Log.d(TAG, "email = " + email);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

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
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}