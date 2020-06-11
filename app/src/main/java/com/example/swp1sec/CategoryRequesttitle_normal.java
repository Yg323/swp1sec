package com.example.swp1sec;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CategoryRequesttitle_normal extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://159.89.193.200//pluscategorytitle_normal.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public CategoryRequesttitle_normal(String email,String cate_title,String cal_title, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 위 url에 post방식으로 값을 전송
        map = new HashMap<>();
        map.put("email",email);
        map.put("cate_title",cate_title);
        map.put("cal_title",cal_title);


    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}

