package com.example.swp1sec;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class color_dialog extends AppCompatActivity {

    Button okButton,cancleButton;
    private ImageButton ibtn_basic1,ibtn_basic2,ibtn_basic3,ibtn_basic4,
            ibtn_sbasic1,ibtn_sbasic2,ibtn_sbasic3,ibtn_sbasic4,
            ibtn_spremium1,ibtn_spremium2,ibtn_spremium3,ibtn_spremium4;

    private String email;
    private static String colorURL = "http://159.89.193.200//getcolor.php";
    private String jsonString;

    private String n_pal1,p_pal1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //상태바 제거(전체화면)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.color_dialog);

        okButton = (Button)findViewById(R.id.color_OK);
        cancleButton = (Button)findViewById(R.id.color_Cancle);

        ibtn_basic1 = (ImageButton)findViewById(R.id.ibtn_basic1);
        ibtn_basic2 = (ImageButton)findViewById(R.id.ibtn_basic2);
        ibtn_basic3 = (ImageButton)findViewById(R.id.ibtn_basic3);
        ibtn_basic4 = (ImageButton)findViewById(R.id.ibtn_basic4);

        ibtn_sbasic1 = (ImageButton)findViewById(R.id.ibtn_sbasic1);
        ibtn_sbasic2 = (ImageButton)findViewById(R.id.ibtn_sbasic2);
        ibtn_sbasic3 = (ImageButton)findViewById(R.id.ibtn_sbasic3);
        ibtn_sbasic4 = (ImageButton)findViewById(R.id.ibtn_sbasic4);

        ibtn_spremium1 = (ImageButton)findViewById(R.id.ibtn_spremium1);
        ibtn_spremium2 = (ImageButton)findViewById(R.id.ibtn_spremium2);
        ibtn_spremium3 = (ImageButton)findViewById(R.id.ibtn_spremium3);
        ibtn_spremium4 = (ImageButton)findViewById(R.id.ibtn_spremium4);

        email = PreferenceManager.getString(color_dialog.this,"email");

        PreferenceManager.setString(color_dialog.this,"color","#E9E9E9");

        GetData colortask = new GetData();
        colortask.execute(colorURL, email);

        ibtn_basic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");

                if (!color.equals("#000000")){//선택이 꺼져있는 상태
                    PreferenceManager.setString(color_dialog.this,"color","#000000");
                    ibtn_basic1.setAlpha((float) 0.5);
                    ibtn_basic2.setAlpha((float) 1.0);
                    ibtn_basic3.setAlpha((float) 1.0);
                    ibtn_basic4.setAlpha((float) 1.0);

                    ibtn_sbasic1.setAlpha((float) 1.0);
                    ibtn_sbasic2.setAlpha((float) 1.0);
                    ibtn_sbasic3.setAlpha((float) 1.0);
                    ibtn_sbasic4.setAlpha((float) 1.0);

                    ibtn_spremium1.setAlpha((float) 1.0);
                    ibtn_spremium2.setAlpha((float) 1.0);
                    ibtn_spremium3.setAlpha((float) 1.0);
                    ibtn_spremium4.setAlpha((float) 1.0);
                }
                else{
                    PreferenceManager.setString(color_dialog.this,"color","#E9E9E9");
                    ibtn_basic1.setAlpha((float) 1.0);
                }
            }
        });
        ibtn_basic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");

                if (!color.equals("#CCC5FF")){//선택이 꺼져있는 상태
                    PreferenceManager.setString(color_dialog.this,"color","#CCC5FF");
                    ibtn_basic1.setAlpha((float) 1.0);
                    ibtn_basic2.setAlpha((float) 0.5);
                    ibtn_basic3.setAlpha((float) 1.0);
                    ibtn_basic4.setAlpha((float) 1.0);

                    ibtn_sbasic1.setAlpha((float) 1.0);
                    ibtn_sbasic2.setAlpha((float) 1.0);
                    ibtn_sbasic3.setAlpha((float) 1.0);
                    ibtn_sbasic4.setAlpha((float) 1.0);

                    ibtn_spremium1.setAlpha((float) 1.0);
                    ibtn_spremium2.setAlpha((float) 1.0);
                    ibtn_spremium3.setAlpha((float) 1.0);
                    ibtn_spremium4.setAlpha((float) 1.0);
                }
                else{
                    PreferenceManager.setString(color_dialog.this,"color","#E9E9E9");
                    ibtn_basic2.setAlpha((float) 1.0);
                }
            }
        });
        ibtn_basic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");

                if (!color.equals("#FF9595")){//선택이 꺼져있는 상태
                    PreferenceManager.setString(color_dialog.this,"color","#FF9595");
                    ibtn_basic1.setAlpha((float) 1.0);
                    ibtn_basic2.setAlpha((float) 1.0);
                    ibtn_basic3.setAlpha((float) 0.5);
                    ibtn_basic4.setAlpha((float) 1.0);

                    ibtn_sbasic1.setAlpha((float) 1.0);
                    ibtn_sbasic2.setAlpha((float) 1.0);
                    ibtn_sbasic3.setAlpha((float) 1.0);
                    ibtn_sbasic4.setAlpha((float) 1.0);

                    ibtn_spremium1.setAlpha((float) 1.0);
                    ibtn_spremium2.setAlpha((float) 1.0);
                    ibtn_spremium3.setAlpha((float) 1.0);
                    ibtn_spremium4.setAlpha((float) 1.0);
                }
                else{
                    PreferenceManager.setString(color_dialog.this,"color","#E9E9E9");
                    ibtn_basic3.setAlpha((float) 1.0);
                }
            }
        });
        ibtn_basic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");

                if (!color.equals("#FFFFB4")){//선택이 꺼져있는 상태
                    PreferenceManager.setString(color_dialog.this,"color","#FFFFB4");
                    ibtn_basic1.setAlpha((float) 1.0);
                    ibtn_basic2.setAlpha((float) 1.0);
                    ibtn_basic3.setAlpha((float) 1.0);
                    ibtn_basic4.setAlpha((float) 0.5);

                    ibtn_sbasic1.setAlpha((float) 1.0);
                    ibtn_sbasic2.setAlpha((float) 1.0);
                    ibtn_sbasic3.setAlpha((float) 1.0);
                    ibtn_sbasic4.setAlpha((float) 1.0);

                    ibtn_spremium1.setAlpha((float) 1.0);
                    ibtn_spremium2.setAlpha((float) 1.0);
                    ibtn_spremium3.setAlpha((float) 1.0);
                    ibtn_spremium4.setAlpha((float) 1.0);
                }
                else{
                    PreferenceManager.setString(color_dialog.this,"color","#E9E9E9");
                    ibtn_basic4.setAlpha((float) 1.0);
                }
            }
        });

        ibtn_sbasic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");
                if(n_pal1.equals("1")) {
                    if (!color.equals("#ff5959")) {//선택이 꺼져있는 상태
                        PreferenceManager.setString(color_dialog.this, "color", "#ff5959");
                        ibtn_basic1.setAlpha((float) 1.0);
                        ibtn_basic2.setAlpha((float) 1.0);
                        ibtn_basic3.setAlpha((float) 1.0);
                        ibtn_basic4.setAlpha((float) 1.0);

                        ibtn_sbasic1.setAlpha((float) 0.5);
                        ibtn_sbasic2.setAlpha((float) 1.0);
                        ibtn_sbasic3.setAlpha((float) 1.0);
                        ibtn_sbasic4.setAlpha((float) 1.0);

                        ibtn_spremium1.setAlpha((float) 1.0);
                        ibtn_spremium2.setAlpha((float) 1.0);
                        ibtn_spremium3.setAlpha((float) 1.0);
                        ibtn_spremium4.setAlpha((float) 1.0);
                    } else {
                        PreferenceManager.setString(color_dialog.this, "color", "#E9E9E9");
                        ibtn_sbasic1.setAlpha((float) 1.0);
                    }
                }
            }
        });
        ibtn_sbasic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");
                if(n_pal1.equals("1")) {
                    if (!color.equals("#fdff6a")) {//선택이 꺼져있는 상태
                        PreferenceManager.setString(color_dialog.this, "color", "#fdff6a");
                        ibtn_basic1.setAlpha((float) 1.0);
                        ibtn_basic2.setAlpha((float) 1.0);
                        ibtn_basic3.setAlpha((float) 1.0);
                        ibtn_basic4.setAlpha((float) 1.0);

                        ibtn_sbasic1.setAlpha((float) 1.0);
                        ibtn_sbasic2.setAlpha((float) 0.5);
                        ibtn_sbasic3.setAlpha((float) 1.0);
                        ibtn_sbasic4.setAlpha((float) 1.0);

                        ibtn_spremium1.setAlpha((float) 1.0);
                        ibtn_spremium2.setAlpha((float) 1.0);
                        ibtn_spremium3.setAlpha((float) 1.0);
                        ibtn_spremium4.setAlpha((float) 1.0);
                    } else {
                        PreferenceManager.setString(color_dialog.this, "color", "#E9E9E9");
                        ibtn_sbasic2.setAlpha((float) 1.0);
                    }
                }
            }
        });
        ibtn_sbasic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");
                if(n_pal1.equals("1")) {
                    if (!color.equals("#5b4fff")) {//선택이 꺼져있는 상태
                        PreferenceManager.setString(color_dialog.this, "color", "#5b4fff");
                        ibtn_basic1.setAlpha((float) 1.0);
                        ibtn_basic2.setAlpha((float) 1.0);
                        ibtn_basic3.setAlpha((float) 1.0);
                        ibtn_basic4.setAlpha((float) 1.0);

                        ibtn_sbasic1.setAlpha((float) 1.0);
                        ibtn_sbasic2.setAlpha((float) 1.0);
                        ibtn_sbasic3.setAlpha((float) 0.5);
                        ibtn_sbasic4.setAlpha((float) 1.0);

                        ibtn_spremium1.setAlpha((float) 1.0);
                        ibtn_spremium2.setAlpha((float) 1.0);
                        ibtn_spremium3.setAlpha((float) 1.0);
                        ibtn_spremium4.setAlpha((float) 1.0);
                    } else {
                        PreferenceManager.setString(color_dialog.this, "color", "#E9E9E9");
                        ibtn_sbasic3.setAlpha((float) 1.0);
                    }
                }
            }
        });
        ibtn_sbasic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");
                if(n_pal1.equals("1")) {
                    if (!color.equals("#82ff79")) {//선택이 꺼져있는 상태
                        PreferenceManager.setString(color_dialog.this, "color", "#82ff79");
                        ibtn_basic1.setAlpha((float) 1.0);
                        ibtn_basic2.setAlpha((float) 1.0);
                        ibtn_basic3.setAlpha((float) 1.0);
                        ibtn_basic4.setAlpha((float) 1.0);

                        ibtn_sbasic1.setAlpha((float) 1.0);
                        ibtn_sbasic2.setAlpha((float) 1.0);
                        ibtn_sbasic3.setAlpha((float) 1.0);
                        ibtn_sbasic4.setAlpha((float) 0.5);

                        ibtn_spremium1.setAlpha((float) 1.0);
                        ibtn_spremium2.setAlpha((float) 1.0);
                        ibtn_spremium3.setAlpha((float) 1.0);
                        ibtn_spremium4.setAlpha((float) 1.0);
                    } else {
                        PreferenceManager.setString(color_dialog.this, "color", "#E9E9E9");
                        ibtn_sbasic4.setAlpha((float) 1.0);
                    }
                }
            }
        });

        ibtn_spremium1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");
                if(p_pal1.equals("1")) {
                    if (!color.equals("#ff78f0")) {//선택이 꺼져있는 상태
                        PreferenceManager.setString(color_dialog.this, "color", "#ff78f0");
                        ibtn_basic1.setAlpha((float) 1.0);
                        ibtn_basic2.setAlpha((float) 1.0);
                        ibtn_basic3.setAlpha((float) 1.0);
                        ibtn_basic4.setAlpha((float) 1.0);

                        ibtn_sbasic1.setAlpha((float) 1.0);
                        ibtn_sbasic2.setAlpha((float) 1.0);
                        ibtn_sbasic3.setAlpha((float) 1.0);
                        ibtn_sbasic4.setAlpha((float) 1.0);

                        ibtn_spremium1.setAlpha((float) 0.5);
                        ibtn_spremium2.setAlpha((float) 1.0);
                        ibtn_spremium3.setAlpha((float) 1.0);
                        ibtn_spremium4.setAlpha((float) 1.0);
                    } else {
                        PreferenceManager.setString(color_dialog.this, "color", "#E9E9E9");
                        ibtn_spremium1.setAlpha((float) 1.0);
                    }
                }
            }
        });
        ibtn_spremium2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");
                if(p_pal1.equals("1")) {
                    if (!color.equals("#a1fffe")) {//선택이 꺼져있는 상태
                        PreferenceManager.setString(color_dialog.this, "color", "#a1fffe");
                        ibtn_basic1.setAlpha((float) 1.0);
                        ibtn_basic2.setAlpha((float) 1.0);
                        ibtn_basic3.setAlpha((float) 1.0);
                        ibtn_basic4.setAlpha((float) 1.0);

                        ibtn_sbasic1.setAlpha((float) 1.0);
                        ibtn_sbasic2.setAlpha((float) 1.0);
                        ibtn_sbasic3.setAlpha((float) 1.0);
                        ibtn_sbasic4.setAlpha((float) 1.0);

                        ibtn_spremium1.setAlpha((float) 1.0);
                        ibtn_spremium2.setAlpha((float) 0.5);
                        ibtn_spremium3.setAlpha((float) 1.0);
                        ibtn_spremium4.setAlpha((float) 1.0);
                    } else {
                        PreferenceManager.setString(color_dialog.this, "color", "#E9E9E9");
                        ibtn_spremium2.setAlpha((float) 1.0);
                    }
                }
            }
        });
        ibtn_spremium3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");
                if(p_pal1.equals("1")) {
                    if (!color.equals("#ba7cff")) {//선택이 꺼져있는 상태
                        PreferenceManager.setString(color_dialog.this, "color", "#ba7cff");
                        ibtn_basic1.setAlpha((float) 1.0);
                        ibtn_basic2.setAlpha((float) 1.0);
                        ibtn_basic3.setAlpha((float) 1.0);
                        ibtn_basic4.setAlpha((float) 1.0);

                        ibtn_sbasic1.setAlpha((float) 1.0);
                        ibtn_sbasic2.setAlpha((float) 1.0);
                        ibtn_sbasic3.setAlpha((float) 1.0);
                        ibtn_sbasic4.setAlpha((float) 1.0);

                        ibtn_spremium1.setAlpha((float) 1.0);
                        ibtn_spremium2.setAlpha((float) 1.0);
                        ibtn_spremium3.setAlpha((float) 0.5);
                        ibtn_spremium4.setAlpha((float) 1.0);
                    } else {
                        PreferenceManager.setString(color_dialog.this, "color", "#E9E9E9");
                        ibtn_spremium3.setAlpha((float) 1.0);
                    }
                }
            }
        });
        ibtn_spremium4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color = PreferenceManager.getString(color_dialog.this,"color");
                if(p_pal1.equals("1")) {
                    if (!color.equals("#ffa11a")) {//선택이 꺼져있는 상태
                        PreferenceManager.setString(color_dialog.this, "color", "#ffa11a");
                        ibtn_basic1.setAlpha((float) 1.0);
                        ibtn_basic2.setAlpha((float) 1.0);
                        ibtn_basic3.setAlpha((float) 1.0);
                        ibtn_basic4.setAlpha((float) 1.0);

                        ibtn_sbasic1.setAlpha((float) 1.0);
                        ibtn_sbasic2.setAlpha((float) 1.0);
                        ibtn_sbasic3.setAlpha((float) 1.0);
                        ibtn_sbasic4.setAlpha((float) 1.0);

                        ibtn_spremium1.setAlpha((float) 1.0);
                        ibtn_spremium2.setAlpha((float) 1.0);
                        ibtn_spremium3.setAlpha((float) 1.0);
                        ibtn_spremium4.setAlpha((float) 0.5);
                    } else {
                        PreferenceManager.setString(color_dialog.this, "color", "#E9E9E9");
                        ibtn_spremium4.setAlpha((float) 1.0);
                    }
                }
            }
        });

        //색상값 등록
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String color = PreferenceManager.getString(color_dialog.this,"color");
                if(color.equals("")||color.equals("#E9E9E9")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(color_dialog.this);
                    Toast toast = Toast.makeText(getApplicationContext(), "색깔을 선택하세요 ", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                    toast.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {//volley
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);
                            boolean success=jasonObject.getBoolean("success");
                            if (success) {//저장 완료
                                Toast toast = Toast.makeText(getApplicationContext(), "카테고리가 등록되었습니다. ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                toast.show();
                                Intent intent = new Intent(color_dialog.this, CalendarView.class);
                                startActivity(intent);

                            }
                            else{//저장 실패한 경우
                                Toast toast = Toast.makeText(getApplicationContext(), "업로드 되지 않았습니다.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                toast.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 volley를 이용해서 요청을 함
                CategoryRequest categoryRequest=new CategoryRequest(email,color,responseListener);
                RequestQueue queue= Volley.newRequestQueue(color_dialog.this);
                queue.add(categoryRequest);
            }
        });
    }
    public  void colorCancle(View v){

        finish();
    }
    @Override
    public  boolean onTouchEvent(MotionEvent event){
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    //안드로이드 백버튼 막기
    @Override
    public void onBackPressed(){
        return;
    }


    private class GetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력

        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);


            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                jsonString = result;
                Log.d("TAG",jsonString);
                ShowResult();

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
                Log.d("CalTAG", "response code - " + responseStatusCode);

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

                Log.d("CalTAG", "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void ShowResult() { //이부분 잘봐
        String TAG_JSON = "data";
        String TAG_N_PAL1 = "n_pal1";
        String TAG_P_PAL1 = "p_pal1";

        try {
            JSONObject jsonObject = new JSONObject(jsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONObject item = jsonObject.getJSONObject(TAG_JSON);
            //반복문인점 주의!
            n_pal1 = item.getString(TAG_N_PAL1);
            p_pal1 = item.getString(TAG_P_PAL1);
            if(n_pal1.equals("1")){
                //ibtn_sbasic1.setBackground(getDrawable(R.drawable.icon_circle));
                ibtn_sbasic1.setBackground(getDrawable(R.drawable.circle_ff5959));
                ibtn_sbasic2.setBackground(getDrawable(R.drawable.circle_fdff6a));
                ibtn_sbasic3.setBackground(getDrawable(R.drawable.circle_5b4fff));
                ibtn_sbasic4.setBackground(getDrawable(R.drawable.circle_82ff79));
            }
            if(p_pal1.equals("1")){
                ibtn_spremium1.setBackground(getDrawable(R.drawable.circle_ff78f0));
                ibtn_spremium2.setBackground(getDrawable(R.drawable.circle_a1fffe));
                ibtn_spremium3.setBackground(getDrawable(R.drawable.circle_ba7cff));
                ibtn_spremium4.setBackground(getDrawable(R.drawable.circle_ffa11a));
            }


        } catch (JSONException e) {
            Log.d("color_dialog", "showResult : ", e);
        }
    }
}

