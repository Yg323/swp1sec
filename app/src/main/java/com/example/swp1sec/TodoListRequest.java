package com.example.swp1sec;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TodoListRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://159.89.193.200//todocheck.php";
    private Map<String, String> map;

    public TodoListRequest(String email,String cal_title, String title,String cate_title, String dcheck, String performance, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("email",email);
        map.put("cal_title",cal_title);
        map.put("title", title);
        map.put("cate_title", cate_title);
        map.put("dcheck", dcheck);
        map.put("performance", performance);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}