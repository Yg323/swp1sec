package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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

public class CreateHabit extends AppCompatActivity {
    private EditText et_event_title,et_habitmemo;
    private Button btn_save, btn_cancel;
    private Button btn_mon,btn_tue,btn_wed,btn_thu,btn_fri,btn_sat,btn_sun;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        et_event_title = findViewById(R.id.et_event_title);
        et_habitmemo = findViewById(R.id.et_habitmemo);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_mon = findViewById(R.id.btn_mon);
        btn_tue = findViewById(R.id.btn_tue);
        btn_wed = findViewById(R.id.btn_wed);
        btn_thu = findViewById(R.id.btn_thu);
        btn_fri = findViewById(R.id.btn_fri);
        btn_sat = findViewById(R.id.btn_sat);
        btn_sun = findViewById(R.id.btn_sun);


        PreferenceManager.setString(CreateHabit.this,"mon","False");
        PreferenceManager.setString(CreateHabit.this,"tue","False");
        PreferenceManager.setString(CreateHabit.this,"wed","False");
        PreferenceManager.setString(CreateHabit.this,"thu","False");
        PreferenceManager.setString(CreateHabit.this,"fri","False");
        PreferenceManager.setString(CreateHabit.this,"sat","False");
        PreferenceManager.setString(CreateHabit.this,"sun","False");
        btn_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mon = PreferenceManager.getString(CreateHabit.this, "mon");
                if (mon != "True"){//선택이 꺼져있는 상태
                    PreferenceManager.setString(CreateHabit.this,"mon","True");
                    btn_mon.setBackground(getResources().getDrawable(R.drawable.habit_button_press));
                    btn_mon.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    PreferenceManager.setString(CreateHabit.this,"mon","False");
                    btn_mon.setBackground(getResources().getDrawable(R.drawable.habit_button_normal));
                    btn_mon.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        btn_tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tue = PreferenceManager.getString(CreateHabit.this, "tue");
                if (tue != "True"){//선택이 꺼져있는 상태
                    PreferenceManager.setString(CreateHabit.this,"tue","True");
                    btn_tue.setBackground(getResources().getDrawable(R.drawable.habit_button_press));
                    btn_tue.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    PreferenceManager.setString(CreateHabit.this,"tue","False");
                    btn_tue.setBackground(getResources().getDrawable(R.drawable.habit_button_normal));
                    btn_tue.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        btn_wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wed = PreferenceManager.getString(CreateHabit.this, "wed");
                if (wed != "True"){//선택이 꺼져있는 상태
                    PreferenceManager.setString(CreateHabit.this,"wed","True");
                    btn_wed.setBackground(getResources().getDrawable(R.drawable.habit_button_press));
                    btn_wed.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    PreferenceManager.setString(CreateHabit.this,"wed","False");
                    btn_wed.setBackground(getResources().getDrawable(R.drawable.habit_button_normal));
                    btn_wed.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        btn_thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thu = PreferenceManager.getString(CreateHabit.this, "thu");
                if (thu != "True"){//선택이 꺼져있는 상태
                    PreferenceManager.setString(CreateHabit.this,"thu","True");
                    btn_thu.setBackground(getResources().getDrawable(R.drawable.habit_button_press));
                    btn_thu.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    PreferenceManager.setString(CreateHabit.this,"thu","False");
                    btn_thu.setBackground(getResources().getDrawable(R.drawable.habit_button_normal));
                    btn_thu.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        btn_fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fri = PreferenceManager.getString(CreateHabit.this, "fri");
                if (fri != "True"){//선택이 꺼져있는 상태
                    PreferenceManager.setString(CreateHabit.this,"fri","True");
                    btn_fri.setBackground(getResources().getDrawable(R.drawable.habit_button_press));
                    btn_fri.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    PreferenceManager.setString(CreateHabit.this,"fri","False");
                    btn_fri.setBackground(getResources().getDrawable(R.drawable.habit_button_normal));
                    btn_fri.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        btn_sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sat = PreferenceManager.getString(CreateHabit.this, "sat");
                if (sat != "True"){//선택이 꺼져있는 상태
                    PreferenceManager.setString(CreateHabit.this,"sat","True");
                    btn_sat.setBackground(getResources().getDrawable(R.drawable.habit_button_press));
                    btn_sat.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    PreferenceManager.setString(CreateHabit.this,"sat","False");
                    btn_sat.setBackground(getResources().getDrawable(R.drawable.habit_button_normal));
                    btn_sat.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        btn_sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sun = PreferenceManager.getString(CreateHabit.this, "sun");
                if (sun != "True"){//선택이 꺼져있는 상태
                    PreferenceManager.setString(CreateHabit.this,"sun","True");
                    btn_sun.setBackground(getResources().getDrawable(R.drawable.habit_button_press));
                    btn_sun.setTextColor(getResources().getColor(R.color.white));
                }
                else{
                    PreferenceManager.setString(CreateHabit.this,"sun","False");
                    btn_sun.setBackground(getResources().getDrawable(R.drawable.habit_button_normal));
                    btn_sun.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = PreferenceManager.getString(CreateHabit.this, "email");
                String title = et_event_title.getText().toString();
                String memo = et_habitmemo.getText().toString();
                String mon = PreferenceManager.getString(CreateHabit.this, "mon");
                String tue = PreferenceManager.getString(CreateHabit.this, "tue");
                String wed = PreferenceManager.getString(CreateHabit.this, "wed");
                String thu = PreferenceManager.getString(CreateHabit.this, "thu");
                String fri = PreferenceManager.getString(CreateHabit.this, "fri");
                String sat = PreferenceManager.getString(CreateHabit.this, "sat");
                String sun = PreferenceManager.getString(CreateHabit.this, "sun");
                if(title.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateHabit.this);
                    dialog = builder.setMessage("값을 입력해주세요!")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {//volley
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);
                            boolean success=jasonObject.getBoolean("success");
                            if (success) {//저장 완료
                                Toast toast = Toast.makeText(getApplicationContext(), "습관이 등록되었습니다. ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                toast.show();
                                //Intent intent = new Intent(CreateHabit.this, CalendarView.class);
                                //startActivity(intent);
                                Intent intent = new Intent(getApplicationContext(),CalendarView.class);
                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                CreateHabitRequest createHabitRequest=new CreateHabitRequest(email,title,memo,mon,tue,wed,thu,fri,sat,sun,responseListener);
                RequestQueue queue= Volley.newRequestQueue(CreateHabit.this);
                queue.add(createHabitRequest);
            }
        });
    }
}
