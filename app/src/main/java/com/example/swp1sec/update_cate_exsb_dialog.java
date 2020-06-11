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

public class update_cate_exsb_dialog extends AppCompatActivity {

    EditText uptitle,upid;
    private int position;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_cate_exsb);
        upid =findViewById(R.id.up_cate_exsb_id);
        uptitle = findViewById(R.id.up_category_title_exercise);

        position = getIntent().getIntExtra("position", 1);

        uptitle.setText(CalendarView.categoryArrayList.get(position).gettitle());
        upid.setText(CalendarView.categoryArrayList.get(position).getid());


    }

    public void up_cate_exsb_Ok(View view){
        final String cate_title=uptitle.getText().toString();
        final String id=upid.getText().toString();
        if(cate_title.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(update_cate_exsb_dialog.this);
            Toast toast = Toast.makeText(getApplicationContext(), "제목을 입력하세요 ", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("update..");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,"http://159.89.193.200/update_cate.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(update_cate_exsb_dialog.this,response,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(update_cate_exsb_dialog.this, CalendarView.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(update_cate_exsb_dialog.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();

                params.put("id",id);
                params.put("cate_title", cate_title);


                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(update_cate_exsb_dialog.this);
        queue.add(request);

    }
    public  void up_cate_exsb_Cancle(View v){

        finish();
    }
}
