package com.example.swp1sec;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class CategoryRequesttitle extends StringRequest{
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://159.89.193.200//pluscategorytitle.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public CategoryRequesttitle(String email,String cal_title, String cate_title,String pro_name,String pro_email,String day,String class_start,String class_ends,String lectureroom,String class_start1,String class_ends1,String day1,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 위 url에 post방식으로 값을 전송
        map = new HashMap<>();
        map.put("email",email);
        map.put("cal_title",cal_title);

        map.put("cate_title",cate_title);
        map.put("pro_name",pro_name);
        map.put("pro_email",pro_email);
        map.put("day",day);
        map.put("class_start",class_start);
        map.put("class_ends",class_ends);
        map.put("lectureroom",lectureroom);
        map.put("class_start1",class_start1);
        map.put("class_ends1",class_ends1);
        map.put("day1",day1);






    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}