package com.example.swp1sec;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class select_calendar extends StringRequest {
    final static private String URL = "http://159.89.193.200//select_calendar.php";
    private Map<String,String> map;

    public select_calendar(String email, String cal_title, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map=new HashMap<>();
        map.put("email",email);
        map.put("cal_title",cal_title);

    }
    @Override
    protected Map<String,String>getParams()throws AuthFailureError{
        return map;
    }
}
