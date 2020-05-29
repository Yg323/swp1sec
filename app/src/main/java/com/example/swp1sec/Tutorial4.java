package com.example.swp1sec;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class Tutorial4 extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private Button btn_start;

    // newInstance constructor for creating fragment with arguments
    public static Tutorial4 newInstance(int page, String title) {
        Tutorial4 fragment = new Tutorial4();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutorial4, container, false);
        EditText tvLabel = (EditText) view.findViewById(R.id.editText);
        btn_start = (Button) view.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),CalendarView.class);
                startActivity(intent);
            }
        });
        tvLabel.setText(page + " -- " + title);
        return view;
    }
}
