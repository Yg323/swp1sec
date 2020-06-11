package com.example.swp1sec;

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

public class add_dialog extends AppCompatActivity {
    private EditText et_add;

    Button okButton,cancleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //상태바 제거(전체화면)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_dialog);
        et_add = findViewById(R.id.et_add);

        okButton = (Button)findViewById(R.id.add_OK);
        cancleButton = (Button)findViewById(R.id.add_Cancle);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = PreferenceManager.getString(add_dialog.this, "email");
                /*String user_email = "14dnfnfn@gmail.com"; //임시*/
                String cal_title = et_add.getText().toString();
                if(cal_title.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(add_dialog.this);
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
                            if (success) {//저장 완료1
                                Toast toast = Toast.makeText(getApplicationContext(), "캘린더가 등록되었습니다. ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                toast.show();
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
                add_dialog_request add_dialog_request=new add_dialog_request(user_email,cal_title,responseListener);
                RequestQueue queue= Volley.newRequestQueue(add_dialog.this);
                queue.add(add_dialog_request);
            }
        });

    }
    //동작 버튼 클릭

//    public void addOK(View v) {
//        finish();
//    }

    public  void addCancle(View v){

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

