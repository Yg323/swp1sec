package com.example.swp1sec;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


public class CertifyActivity extends AppCompatActivity {
    Button mailButton, codeCheckButton, passUpdateButton;
    EditText emailText, codeText, newpass, passck;
    String code = SendMail.getCode();
    boolean check = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certify);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());
        emailText = (EditText) findViewById(R.id.et_email);
        codeText = (EditText) findViewById(R.id.et_code);
        newpass = (EditText) findViewById(R.id.et_newpass);
        passck = (EditText) findViewById(R.id.et_newpassck);
        mailButton = (Button) findViewById(R.id.btn_send);
        codeCheckButton = (Button) findViewById(R.id.btn_codecheck);
        passUpdateButton = (Button) findViewById(R.id.btn_passupdate);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                SendMail mailServer = new SendMail();
                mailServer.sendSecurityCode(getApplicationContext(),
                        emailText.getText().toString());
            }
        });
        codeCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code.equals(codeText.getText().toString())){
                    check = true ;
                    Toast toast = Toast.makeText(getApplicationContext(), "인증이 성공하였습니다!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "인증번호가 일치하지 않습니다!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                    toast.show();
                }
            }
        });
        passUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = newpass.getText().toString();
                String passcheck = passck.getText().toString();
                if(password.equals(passcheck)){
                    if(check){
                        HashMap<String, String> requestedParams = new HashMap<>();
                        requestedParams.put("email", email);
                        requestedParams.put("password", password);
                        Log.d("HashMap", requestedParams.get("email"));
                        PostRequestHandler postRequestHandler = new PostRequestHandler("http://159.89.193.200//PasswordUp.php", requestedParams);
                        postRequestHandler.execute();
                        Intent intent = new Intent(CertifyActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(), "인증번호 확인을 해주세요!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "비밀번호 확인을 해주세요!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                    toast.show();
                }

            }
        });
    }
}
