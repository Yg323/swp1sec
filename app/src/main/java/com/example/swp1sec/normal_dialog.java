package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class normal_dialog extends AppCompatActivity {
    private EditText et_category_title_normal;


    Button normal_OK,cancleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //상태바 제거(전체화면)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.normal_dialog);
        et_category_title_normal = findViewById(R.id.et_category_title_normal);

        normal_OK = (Button)findViewById(R.id.normal_OK);
        cancleButton = (Button)findViewById(R.id.normal_Cancle);
        normal_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cal_title = PreferenceManager.getString(normal_dialog.this,"cal_title");

                String email = PreferenceManager.getString(normal_dialog.this, "email");
                /*String email = "14dnfnfn@gmail.com"; //임시*/
                String cate_title = et_category_title_normal.getText().toString();
                if(cate_title.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(normal_dialog.this);
                    Toast toast = Toast.makeText(getApplicationContext(), "제목을 입력하세요 ", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }


                Response.Listener<String> responseListener=new Response.Listener<String>() {//volley
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);//Register2 php에 response
                            boolean success=jasonObject.getBoolean("success");//Register2 php에 sucess
                            if (success) {//저장 완료
                                Toast toast = Toast.makeText(getApplicationContext(), "일반 일정이 등록되었습니다. ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                toast.show();
                                Intent intent = new Intent(normal_dialog.this, color_dialog.class);
                                startActivity(intent);
                                finish();
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
                CategoryRequesttitle_normal categoryRequesttitle_normal=new CategoryRequesttitle_normal(email,cate_title,cal_title,responseListener);
                RequestQueue queue= Volley.newRequestQueue(normal_dialog.this);
                queue.add(categoryRequesttitle_normal);
            }
        });

    }
    //동작 버튼 클릭

    /*public void normalOK(View v) {
        Intent intent = new Intent(this,color_dialog.class);
        startActivity(intent);
        finish();
    }*/

    public  void normalCancle(View v){

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

