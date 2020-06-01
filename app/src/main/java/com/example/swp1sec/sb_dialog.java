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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class sb_dialog extends AppCompatActivity {
    private EditText et_category_title,et_pro_name,et_pro_email,et_day,et_class_start,et_class_ends,et_lectureroom;

    Button okButton,cancleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //상태바 제거(전체화면)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sb_dialog);

        et_category_title = findViewById(R.id.et_category_title);
        et_pro_name = findViewById(R.id.et_pro_name);
        et_pro_email = findViewById(R.id.et_pro_email);
        et_day = findViewById(R.id.et_day);
        et_class_start = findViewById(R.id.et_class_start);
        et_class_ends = findViewById(R.id.et_class_ends);
        et_lectureroom = findViewById(R.id.et_lectureroom);


        okButton = (Button)findViewById(R.id.sb_OK);
        cancleButton = (Button)findViewById(R.id.sb_Cancle);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String email = PreferenceManager.getString(CreateHabit.this, "email");
                String email = "14dnfnfn@gmail.com"; //임시
                String cate_title = et_category_title.getText().toString();
                String pro_name = et_pro_name.getText().toString();
                String pro_email = et_pro_email.getText().toString();
                String day = et_day.getText().toString();
                String class_start = et_class_start.getText().toString();
                String class_ends = et_class_ends.getText().toString();
                String lectureroom = et_lectureroom.getText().toString();



                Response.Listener<String> responseListener=new Response.Listener<String>() {//volley
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);//Register2 php에 response
                            boolean success=jasonObject.getBoolean("success");//Register2 php에 sucess
                            if (success) {//저장 완료
                                Toast toast = Toast.makeText(getApplicationContext(), "과목이 등록되었습니다. ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                toast.show();
                                Intent intent = new Intent(sb_dialog.this, color_dialog.class);
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
                CategoryRequesttitle categoryRequesttitle=new CategoryRequesttitle(email,cate_title,pro_name,pro_email,day,class_start,class_ends,lectureroom,responseListener);
                RequestQueue queue= Volley.newRequestQueue(sb_dialog.this);
                queue.add(categoryRequesttitle);
            }
        });

    }
    //동작 버튼 클릭
   /* public  void sbOK(View v){
        Intent intent = new Intent(this,color_dialog.class);
        startActivity(intent);
        finish();
    }*/
    //취소 버튼 클릭
    public  void sbCancle(View v){
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