package com.example.swp1sec;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class CategoryRequest extends StringRequest{
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://159.89.193.200//pluscategory.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public CategoryRequest(String email,String color, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 위 url에 post방식으로 값을 전송
        map = new HashMap<>();
        map.put("email",email);
        map.put("color",color);


    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
