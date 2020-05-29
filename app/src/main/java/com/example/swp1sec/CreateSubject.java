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

public class CreateSubject extends AppCompatActivity {
    private EditText et_subject_title,et_subject_memo;
    private TextView start_date, start_time, end_date, end_time;
    private Button btn_subject_save, btn_subject_cancel;
    private RatingBar sub_star;
    private AlertDialog dialog;
    private static String URL = "http://159.89.193.200//plusSubject.php";
    private static String TAG = "setsubject";

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

        TextView et_date = (TextView) findViewById(R.id.sub_start_date);
        et_date.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel2() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView et_date = (TextView) findViewById(R.id.sub_end_date);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject);
        et_subject_title = findViewById(R.id.et_subject_title);
        et_subject_memo = findViewById(R.id.et_subject_memo);
        btn_subject_save = findViewById(R.id.btn_subject_save);
        btn_subject_cancel = findViewById(R.id.btn_subject_cancel);

        start_date = findViewById(R.id.sub_start_date);
        start_time = findViewById(R.id.sub_start_time);
        end_date = findViewById(R.id.sub_end_date);
        end_time = findViewById(R.id.sub_end_time);

        sub_star = findViewById(R.id.sub_ratingBar);

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateSubject.this, StartDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateSubject.this, EndDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateSubject.this, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(CreateSubject.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        end_time.setText(state + " " + hourOfDay + "시 " + minute + "분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


       btn_subject_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_subject_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String email = PreferenceManager.getString(CreateHabit.this, "email");
                String email = "14dnfnfn@gmail.com"; //임시
                String title = et_subject_title.getText().toString();
                String memo = et_subject_memo.getText().toString();
                String date = start_date.getText().toString();
                String time = start_time.getText().toString();

                float importance = sub_star.getRating();

                if (title.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateSubject.this);
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
                                Toast toast = Toast.makeText(getApplicationContext(), "과목이 등록되었습니다. ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                                toast.show();
                                Intent intent = new Intent(CreateSubject.this, CalendarView.class);
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
                CreateSubjectRequest createSubjectRequest = new CreateSubjectRequest(email, title, memo, date, time, importance, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateSubject.this);
                queue.add(createSubjectRequest);
            }
        });
    }
}

