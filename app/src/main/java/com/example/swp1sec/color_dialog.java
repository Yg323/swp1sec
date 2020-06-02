package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class color_dialog extends AppCompatActivity {

    Button okButton,cancleButton;
    private ImageButton ibtn_red,ibtn_orange,ibtn_yellow,ibtn_green,ibtn_blue,ibtn_puple;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //상태바 제거(전체화면)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.color_dialog);

        okButton = (Button)findViewById(R.id.color_OK);
        cancleButton = (Button)findViewById(R.id.color_Cancle);

        ibtn_red = findViewById(R.id.ibtn_red);
        ibtn_orange = findViewById(R.id.ibtn_orange);
        ibtn_yellow = findViewById(R.id.ibtn_yellow);
        ibtn_green = findViewById(R.id.ibtn_green);
        ibtn_blue = findViewById(R.id.ibtn_blue);
        ibtn_puple = findViewById(R.id.ibtn_purple);

        PreferenceManager.setString(color_dialog.this,"color","Null");

        ibtn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(color_dialog.this,"color","#F44336");
            }
        });
        ibtn_orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(color_dialog.this,"color","#FF9800");
            }
        });
        ibtn_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(color_dialog.this,"color","#F1D63D");
            }
        });
        ibtn_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(color_dialog.this,"color","#66ED48");
            }
        });
        ibtn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(color_dialog.this,"color","#1141E1");
            }
        });
        ibtn_puple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.setString(color_dialog.this,"color","#B621DC");
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String email = "14dnfnfn@gmail.com";//임시
                            //String email = PreferenceManager.getString(color_dialog.this,"email");
                            String color = PreferenceManager.getString(color_dialog.this,"color");
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

    //동작 버튼 클릭

    /*public void colorOK(View v) {
        String email = "14dnfnfn@gmail.com";//임시
        //String email = PreferenceManager.getString(color_dialog.this,"email");
        String color = PreferenceManager.getString(color_dialog.this,"color");
        Response.Listener<String> responseListener=new Response.Listener<String>() {//volley
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jasonObject=new JSONObject(response);//Register2 php에 response
                    boolean success=jasonObject.getBoolean("success");//Register2 php에 sucess
                    if (success) {//저장 완료
                        Toast toast = Toast.makeText(getApplicationContext(), "카테고리가 등록되었습니다. ", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                        toast.show();

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
        finish();
    }*/

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
}

