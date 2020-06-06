package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class color_theme_dialog extends AppCompatActivity {
    ImageButton ibtn_white,ibtn_blacke;
    Button ok,cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //상태바 제거(전체화면)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.color_theme_dialog);


        ibtn_white = findViewById(R.id.ibtn_white);
        ibtn_blacke = findViewById(R.id.ibtn_black);
    }

    public void btndefault (View view){

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        startActivity(new Intent(getApplicationContext(),color_theme_dialog.class));

    }
    public void btntheme (View view){

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        startActivity(new Intent(getApplicationContext(),color_theme_dialog.class));

    }
    public  void themeOk(View v){

        finish();
    }public  void themeCancle(View v){

        finish();
    }

}
