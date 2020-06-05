package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class t_r_PopupActivity extends AppCompatActivity {

    String TAG = "Popup";
    TextView txtText;
    Button okBtn, cancelBtn;
    int mnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_activity);

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
                Log.d(TAG, "num= " + mnum);

                if (mnum < 70) {
                    Toast.makeText(getApplicationContext(), "일반 테마 1을 획득하셨습니다!", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(v, "일반 테마", Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "프리미엄 테마 1을 획득하셨습니다!", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(v, "프리미엄 테마", Snackbar.LENGTH_SHORT).show();
                }

                //디비입력
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //디비입력
                finish();
            }
        });
    }
}