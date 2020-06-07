package com.example.swp1sec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class t_c_PopupActivity extends AppCompatActivity {
    String TAG = "Popup";
    TextView txtText;
    Button okBtn, cancelBtn;
    RadioGroup radioGroup;
    int mnum;
    private static String IP_ADDRESS = "159.89.193.200/t_r_store.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.c_popup_activity);

        okBtn = (Button)findViewById(R.id.ok);
        cancelBtn = (Button)findViewById(R.id.cancel);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //UI 객체생성
        txtText = (TextView)findViewById(R.id.txtText);

        //데이터 가져오기
        Intent intent = getIntent();
        //Log.d(TAG, "intent= " + intent);
        String data = "물품을 선택하세요";
        //Log.d(TAG, "data= " + data);
        txtText.setText(data);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rb = ((RadioGroup)radioGroup.findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
                InsertData task = new InsertData();

                switch (rb){
                    case R.id.radioButton3:
                        mnum = 0;
                        Toast.makeText(getApplicationContext(),"일반 테마 1을 획득했습니다!", Toast.LENGTH_SHORT).show();
                        task.execute("http://" + IP_ADDRESS, Integer.toString(mnum));
                        finish();
                        break;
                    case R.id.radioButton4:
                        mnum = 1;
                        Toast.makeText(getApplicationContext(),"프리미엄 테마 1을 획득했습니다!", Toast.LENGTH_SHORT).show();
                        task.execute("http://" + IP_ADDRESS, Integer.toString(mnum));
                        finish();
                        break;
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
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
            //int n_theme1 = 0;
            //int p_theme1 = 0;
            int mnum = Integer.parseInt(num);
            String postParameters;

            if(mnum == 0)
                postParameters = "normal=1";
            else
                postParameters = "premium=1";

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