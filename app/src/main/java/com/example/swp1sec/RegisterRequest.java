// URL에 POST 방식으로 파라미터를 전송하는 역할, 회원가입 정보를 PHP 서버에 보내서 DB에 저장
package com.example.swp1sec;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://159.89.193.200//SignUp.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public RegisterRequest(String email, String password,  Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null); // 위 url에 post방식으로 값을 전송

        map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
