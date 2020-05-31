package com.example.swp1sec;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateNormal extends AppCompatActivity {
    private EditText et_normal_title,et_normal_memo;
    private TextView start_date, start_time, end_date, end_time;
    private Button btn_normal_save, btn_normal_cancel;
    private RatingBar nor_star;
    private AlertDialog dialog;
    private static String URL = "http://159.89.193.200//plusNormal.php";
    private static String TAG = "setnormal";

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener StartDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    DatePickerDialog.OnDateSetListener EndDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }
    };

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView et_date = (TextView) findViewById(R.id.nor_start_date);
        et_date.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel2() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView et_date = (TextView) findViewById(R.id.nor_end_date);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_normal);
        et_normal_title = findViewById(R.id.et_normal_title);
        et_normal_memo = findViewById(R.id.et_normal_memo);
        btn_normal_save = findViewById(R.id.btn_normal_save);
        btn_normal_cancel = findViewById(R.id.btn_normal_cancel);

        start_date = findViewById(R.id.nor_start_date);
        start_time = findViewById(R.id.nor_start_time);
        end_date = findViewById(R.id.nor_end_date);
        end_time = findViewById(R.id.nor_end_time);

        nor_star = findViewById(R.id.nor_ratingBar);

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateNormal.this, StartDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateNormal.this, EndDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateNormal.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        /*if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }*/
                        // EditText에 출력할 형식 지정
                        start_time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateNormal.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        // EditText에 출력할 형식 지정
                        end_time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        btn_normal_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_normal_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String email = PreferenceManager.getString(CreateHabit.this, "email");
                String email = "14dnfnfn@gmail.com"; //임시
                String title = et_normal_title.getText().toString();
                String memo = et_normal_memo.getText().toString();
                String date = start_date.getText().toString();
                String time = start_time.getText().toString();
                String enddate = end_date.getText().toString();
                String endtime = end_time.getText().toString();

                int importance = (int) nor_star.getRating();

                if (title.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateNormal.this);
                    dialog = builder.setMessage("값을 입력해주세요!")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {//volley
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject = new JSONObject(response);//Register2 php에 response
                            boolean success = jasonObject.getBoolean("success");//Register2 php에 sucess
                            if (success) {//저장 완료
                                Toast toast = Toast.makeText(getApplicationContext(), "일정이 등록되었습니다. ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                Intent intent = new Intent(CreateNormal.this, CalendarView.class);
                                startActivity(intent);
                            } else {//저장 실패한 경우
                                Toast toast = Toast.makeText(getApplicationContext(), "업로드 되지 않았습니다.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 volley를 이용해서 요청을 함
                CreateNormalRequest createNormalRequest = new CreateNormalRequest(email, title, memo, date, time, enddate, endtime, importance, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateNormal.this);
                queue.add(createNormalRequest);
            }
        });
    }
}
