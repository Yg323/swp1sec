package com.example.swp1sec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class update_cate_dialog extends AppCompatActivity {

    EditText uptitle,upproname,upproemail,upday,upclassstart,upclassends,uplectureroom,upId;
    private int position;

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_cate_dialog);
        upId =findViewById(R.id.up_id);

        uptitle = findViewById(R.id.up_category_title);
        upproname = findViewById(R.id.up_pro_name);
        upproemail = findViewById(R.id.up_pro_email);
        upday = findViewById(R.id.up_day);
        upclassstart = findViewById(R.id.up_class_start);
        upclassends = findViewById(R.id.up_class_ends);
        uplectureroom = findViewById(R.id.up_lectureroom);

        position = getIntent().getIntExtra("position", 1);

        upId.setText(CalendarView.categoryArrayList.get(position).getid());
        uptitle.setText(CalendarView.categoryArrayList.get(position).gettitle());
        upproname.setText(CalendarView.categoryArrayList.get(position).getpro_name());
        upproemail.setText(CalendarView.categoryArrayList.get(position).getpro_email());
        upday.setText(CalendarView.categoryArrayList.get(position).getday());
        upclassstart.setText(CalendarView.categoryArrayList.get(position).getclass_start());
        upclassends.setText(CalendarView.categoryArrayList.get(position).getclass_ends());
        uplectureroom.setText(CalendarView.categoryArrayList.get(position).getlectureroom());
    }


    public void up_cate_OK(View view){


        final String cate_title =uptitle.getText().toString();
        final String pro_name =upproname.getText().toString();
        final String pro_email =upproemail.getText().toString();
        final String day =upday.getText().toString();
        final String class_start =upclassstart.getText().toString();
        final String class_ends =upclassends.getText().toString();
        final String lectureroom =uplectureroom.getText().toString();
        final String id = upId.getText().toString();



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("update..");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST,"http://159.89.193.200/update_cate.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(update_cate_dialog.this,response,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(update_cate_dialog.this, CalendarView.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                    },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(update_cate_dialog.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String,String>();

                    params.put("id",id);
                    params.put("cate_title", cate_title);
                    params.put("pro_name", pro_name);
                    params.put("pro_email", pro_email);
                    params.put("day", day);
                    params.put("class_start", class_start);
                    params.put("class_ends", class_ends);
                    params.put("lectureroom", lectureroom);

                    return params;
                }
            };
            RequestQueue queue= Volley.newRequestQueue(update_cate_dialog.this);
            queue.add(request);

        }


    public  void up_cate_cancle(View v){

        finish();
    }





}
