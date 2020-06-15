package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login,btn_register,btn_repass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        btn_repass=findViewById(R.id.btn_repass);

        btn_register.setOnClickListener(new View.OnClickListener() {//회원가입 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_repass.setOnClickListener(new View.OnClickListener() {//비밀번호 찾기 버튼을 클릭시 수행
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, CertifyActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=et_id.getText().toString();
                String password=et_pass.getText().toString();


                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject=new JSONObject(response);
                            boolean success=jasonObject.getBoolean("success");
                            if (success) {//로그인 성공한 경우
                                String email = jasonObject.getString("email");
                                String password = jasonObject.getString("password");
                                Intent intent = new Intent(LoginActivity.this, TutorialActivity.class);
                                PreferenceManager.setString(LoginActivity.this, "email",email);
                                //맨 처음 세팅
                                PreferenceManager.setBoolean(getApplicationContext(),"lunarBox",false);
                                PreferenceManager.setBoolean(getApplicationContext(),"holidayBox",false);
                                PreferenceManager.setBoolean(getApplicationContext(),"schoolBox",false);
                                PreferenceManager.setBoolean(getApplicationContext(),"week_remind",true);
                                PreferenceManager.setBoolean(getApplicationContext(),"day_remind",true);
                                PreferenceManager.setString(getApplicationContext(), "start_week_title", "월요일");
                                startActivity(intent);
                                finish();
                            }


                            else{//로그인 실패한 경우
                                Toast toast = Toast.makeText(getApplicationContext(), "이메일 혹은 비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                toast.show();
                                return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest=new LoginRequest(email,password,responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
