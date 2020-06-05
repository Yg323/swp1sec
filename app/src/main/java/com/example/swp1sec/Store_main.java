package com.example.swp1sec;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Store_main extends AppCompatActivity {

    String TAG = "MainActivity";

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Purchase perchase;
    History history;
    Fragment p_frag;
    Fragment h_frag;
    TextView txtResult ;
    TextView textView2 ;
    TextView textView3 ;
    Button btn01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_main);

        fragmentManager = getSupportFragmentManager();
        perchase = new Purchase();
        history = new History();

        Button p_button = findViewById(R.id.p_button);
        Button h_button = findViewById(R.id.h_button);

        p_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, perchase).commit();
            }
        });

        h_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, history).commit();
            }
        });
    }

    public void onFragmentChange(int index){
        if(index == 0 ){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, perchase).commit();
        }else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, history).commit();
        }
    }
}