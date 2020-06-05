package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class a_c_PopUpActivity extends AppCompatActivity {

    String TAG = "Popup";
    TextView txtText;
    Button okBtn, cancelBtn;
    RadioGroup radioGroup;

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
        Log.d(TAG, "intent= " + intent);
        String data = "물품을 선택하세요.";
        Log.d(TAG, "data= " + data);
        txtText.setText(data);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rb = ((RadioGroup)radioGroup.findViewById(R.id.radioGroup)).getCheckedRadioButtonId();

                switch (rb){
                    case R.id.radioButton3:
                        Toast.makeText(getApplicationContext(),"일반 알람소리 1을 획득했습니다!", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case R.id.radioButton4:
                        Toast.makeText(getApplicationContext(),"프리미엄 알람소리 1을 획득했습니다!", Toast.LENGTH_SHORT).show();
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
}