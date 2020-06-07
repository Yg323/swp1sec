package com.example.swp1sec;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Store_main extends AppCompatActivity {

    String TAG = "MainActivity";

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
    TextView Coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_main);

        fragmentManager = getSupportFragmentManager();
        perchase = new Purchase();
        history = new History();
        String url = "http://159.89.193.200/get_money.php";

        Button p_button = findViewById(R.id.p_button);
        Button h_button = findViewById(R.id.h_button);
        Coin = findViewById(R.id.coin);

        NetworkTask networkTask = new NetworkTask(url, null);
        try{
            outPut = networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //outPut = networkTask.getTv_outPut();
        //String ex_title = tv_outPut;
        Log.d(TAG,"outPut: "+ outPut);

        money_doJSONParser(outPut);
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
            Log.d(TAG, "url = " + url);
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d(TAG, "result = " + result);

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
}