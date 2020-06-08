package com.example.swp1sec;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Cate_check_request extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://159.89.193.200//catecheck.php";
    private Map<String, String> map;

    public Cate_check_request(String email, String cal_title, String cate_title, String performance, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("email",email);
        map.put("cal_title",cal_title);
        map.put("cate_title", cate_title);
        map.put("performance", performance);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}