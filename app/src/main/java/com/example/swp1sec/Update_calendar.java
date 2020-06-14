package com.example.swp1sec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Update_calendar extends AppCompatActivity {

    EditText uptitle,upid;
    private int position;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_calendar);
        upid =findViewById(R.id.up_cal_id);
        uptitle = findViewById(R.id.up_cal_title);

        position = getIntent().getIntExtra("position", 1);

        uptitle.setText(CalendarView.calArrayList.get(position).gettitle());
        upid.setText(CalendarView.calArrayList.get(position).getid());


    }

    public void up_cal_exsb_Ok(View view){
        final String cal_title=uptitle.getText().toString();
        final String id=upid.getText().toString();
        if(cal_title.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(Update_calendar.this);
            Toast toast = Toast.makeText(getApplicationContext(), "제목을 입력하세요 ", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("update..");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,"http://159.89.193.200/update_cal.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Update_calendar.this,response,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Update_calendar.this, CalendarView.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(Update_calendar.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();

                params.put("id",id);
                params.put("cal_title", cal_title);


                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(Update_calendar.this);
        queue.add(request);

    }
    public  void up_cal_exsb_Cancle(View v){

        finish();
    }
}
