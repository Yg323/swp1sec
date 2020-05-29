package com.example.swp1sec;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateHabitRequest extends StringRequest{
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://159.89.193.200//plushabit.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public CreateHabitRequest(String email,String title, String memo,String mon,String tue,String wed,String thu,String fri,String sat,String sun,  Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 위 url에 post방식으로 값을 전송

        map = new HashMap<>();
        map.put("email",email);
        map.put("title", title);
        map.put("memo",memo);
        map.put("mon",mon);
        map.put("tue",tue);
        map.put("wed",wed);
        map.put("thu",thu);
        map.put("fri",fri);
        map.put("sat",sat);
        map.put("sun",sun);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}