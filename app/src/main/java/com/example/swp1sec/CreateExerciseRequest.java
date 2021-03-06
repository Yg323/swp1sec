package com.example.swp1sec;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateExerciseRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://159.89.193.200//plusExercise.php";
    private Map<String, String> map;

    //private Map<String, String>parameters;

    public CreateExerciseRequest(String email, String title, String memo, String date, String time, String endtime, int importance, int cateid, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 위 url에 post방식으로 값을 전송

        map = new HashMap<>();

        map.put("email", email);
        map.put("title", title);
        map.put("memo", memo);
        map.put("date", date);
        map.put("time", time);
        map.put("importance", String.valueOf(importance));
        //map.put("enddate", enddate);
        map.put("endtime", endtime);
        //카데고리 id 받아오는것
        map.put("category_id", String.valueOf(cateid));

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
