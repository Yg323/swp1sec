package com.example.swp1sec;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class sb_dialog extends AppCompatActivity {
    private EditText et_category_title,et_pro_name,et_pro_email,et_day,et_lectureroom,et_day1;
    private TextView et_class_start,et_class_ends,et_class_start1,et_class_ends1;
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
        et_class_start1 = findViewById(R.id.et_class_start1);
        et_class_ends1 = findViewById(R.id.et_class_ends1);
        et_day1 = findViewById(R.id.et_day1);





        okButton = (Button)findViewById(R.id.sb_OK);
        cancleButton = (Button)findViewById(R.id.sb_Cancle);
        et_class_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(sb_dialog.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        /*if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }*/
                        // EditText에 출력할 형식 지정
                        et_class_start.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        et_class_ends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(sb_dialog.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        /*if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }*/
                        // EditText에 출력할 형식 지정
                        et_class_ends.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        et_class_start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(sb_dialog.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        /*if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }*/
                        // EditText에 출력할 형식 지정
                        et_class_start1.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        et_class_ends1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(sb_dialog.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        /*if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }*/
                        // EditText에 출력할 형식 지정
                        et_class_ends1.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });






        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = PreferenceManager.getString(sb_dialog.this, "email");
                String cal_title = PreferenceManager.getString(sb_dialog.this,"cal_title");

                /*String email = "14dnfnfn@gmail.com"; //임시*/
                String cate_title = et_category_title.getText().toString();
                String pro_name = et_pro_name.getText().toString();
                String pro_email = et_pro_email.getText().toString();
                String day = et_day.getText().toString();
                String class_start = et_class_start.getText().toString();
                String class_ends = et_class_ends.getText().toString();
                String lectureroom = et_lectureroom.getText().toString();
                String class_start1 = et_class_start1.getText().toString();
                String class_ends1 = et_class_ends1.getText().toString();
                String day1 = et_day1.getText().toString();

                if(cate_title.equals("")||pro_name.equals("")||pro_email.equals("")||day.equals("")||class_start.equals("")||class_ends.equals("")||lectureroom.equals("")||class_start1.equals("")||class_ends1.equals("")||day1.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(sb_dialog.this);
                    Toast toast = Toast.makeText(getApplicationContext(), "값을 모두 입력해주세요 ", Toast.LENGTH_SHORT);
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
                CategoryRequesttitle categoryRequesttitle=new CategoryRequesttitle(email,cal_title,cate_title,pro_name,pro_email,day,class_start,class_ends,lectureroom,class_start1,class_ends1,day1,responseListener);
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