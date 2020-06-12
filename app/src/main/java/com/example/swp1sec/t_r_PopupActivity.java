package com.example.swp1sec;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class t_r_PopupActivity extends AppCompatActivity {

    String TAG = "Popup";
    TextView txtText;
    Button okBtn, cancelBtn;
    int mnum;
    String outPut;
    int res;
    Intent mintent;
    String email;

    private static String IP_ADDRESS = "159.89.193.200/t_r_store.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_activity);

        email = PreferenceManager.getString(this, "email");

        String url = "http://159.89.193.200/get_money.php";

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

        okBtn = (Button) findViewById(R.id.ok);
        cancelBtn = (Button) findViewById(R.id.cancel);

        //UI 객체생성
        txtText = (TextView) findViewById(R.id.txtText);

        //데이터 가져오기
        Intent intent = getIntent();
        //Log.d(TAG, "intent= " + intent);
        String data = "테마 랜덤 박스를 구매하시겠습니까?";
        //Log.d(TAG, "data= " + data);
        txtText.setText(data);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rnd = new Random();
                mnum = rnd.nextInt(100);
                //Log.d(TAG, "num= " + mnum);
                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS, Integer.toString(mnum));

                if (mnum < 70) {
                    Toast.makeText(getApplicationContext(), "일반 테마 1을 획득하셨습니다!", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(v, "일반 테마", Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "프리미엄 테마 1을 획득하셨습니다!", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(v, "프리미엄 테마", Snackbar.LENGTH_SHORT).show();
                }

                mintent = new Intent(v.getContext(), Store_main.class);
                v.getContext().startActivity(mintent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class NetworkTask extends AsyncTask<String, Void, String> {

        private String url;
        private ContentValues values;
        private String tv_outPut;
        String errorString = null;
        private static final String TAG = "networktask";

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(String ... params) {
            String serverURL = params[0];
            String email = params[1];

            String postParameters = "email=" + email;

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            Log.d(TAG, "url = " + url);
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d(TAG, "result = " + result);

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
            int result = 0;
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("money");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String money = tt.getString("money");

                result = Integer.parseInt(money);
                //Log.d(TAG, "result = " + result);
            }

            res = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
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
            String num = (String)params[1];
            int mnum = Integer.parseInt(num);
            String postParameters;

            if(mnum < 70)
                postParameters = "normal=1" + "&money=" + res + "&email=" +email;
            else
                postParameters = "premium=1" + "&money=" + res + "&email=" +email;

            //postParameters = "normal=" + Integer.toString(n_theme1) + "&premium=" + Integer.toString(p_theme1);

            Log.d(TAG, "postparam = " + postParameters);

            try {

                URL url = new URL(severurl);
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