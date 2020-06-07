package com.example.swp1sec;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class update_cate_dialog extends AppCompatActivity {

    EditText uptitle,upproname,upproemail,upday,uplectureroom,upId,upday1;
    TextView upclassstart,upclassends,upclassstart1,upclassends1;
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
        upday1 = findViewById(R.id.up_day1);

        upclassstart = findViewById(R.id.up_class_start);
        upclassends = findViewById(R.id.up_class_ends);
        uplectureroom = findViewById(R.id.up_lectureroom);
        upclassstart1 = findViewById(R.id.up_class_start1);
        upclassends1 = findViewById(R.id.up_class_ends1);



        position = getIntent().getIntExtra("position", 1);

        upId.setText(CalendarView.categoryArrayList.get(position).getid());
        uptitle.setText(CalendarView.categoryArrayList.get(position).gettitle());
        upproname.setText(CalendarView.categoryArrayList.get(position).getpro_name());
        upproemail.setText(CalendarView.categoryArrayList.get(position).getpro_email());
        upday.setText(CalendarView.categoryArrayList.get(position).getday());
        upclassstart.setText(CalendarView.categoryArrayList.get(position).getclass_start());
        upclassends.setText(CalendarView.categoryArrayList.get(position).getclass_ends());
        uplectureroom.setText(CalendarView.categoryArrayList.get(position).getlectureroom());
        upday1.setText(CalendarView.categoryArrayList.get(position).getday1());
        upclassstart1.setText(CalendarView.categoryArrayList.get(position).getclass_start1());
        upclassends1.setText(CalendarView.categoryArrayList.get(position).getclass_ends1());
        upclassstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(update_cate_dialog.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        /*if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }*/
                        // EditText에 출력할 형식 지정
                        upclassstart.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        upclassstart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(update_cate_dialog.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        /*if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }*/
                        // EditText에 출력할 형식 지정
                        upclassstart1.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        upclassends1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(update_cate_dialog.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        /*if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }*/
                        // EditText에 출력할 형식 지정
                        upclassends1.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        upclassends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(update_cate_dialog.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        /*if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }*/
                        // EditText에 출력할 형식 지정
                        upclassends.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
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
        final String class_start1 =upclassstart1.getText().toString();
        final String class_ends1 =upclassends1.getText().toString();
        final String day1 =upday1.getText().toString();







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
                    params.put("day1", day1);
                    params.put("class_start1", class_start1);
                    params.put("class_ends1", class_ends1);

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
