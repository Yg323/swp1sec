package com.example.swp1sec;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.library.WeekView;
import com.example.library.WeekViewEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateSubjectEdit extends AppCompatActivity {

    public WeekView weekView;
    private EditText et_subject_title,et_subject_memo;
    private EditText Title;
    private String alm_text_time;
    private String alm_text_date;
    public int year, month, day, hour, minute;
    //private EditText mEditTextTime;
    //private EditText mEditTextMemo;
    //private EditText mEditTextEnd;
    private TextView Result;
    private TextView start_date, start_time, end_date, end_time;
    //알람
    private TextView alm_set, alm_date_set;
    Date currentDateTime;

    private Button btn_subject_save, btn_subject_cancel;
    private RatingBar sub_star;
    private AlertDialog dialog;
    TimePicker t_picker;
    DatePicker d_picker;
    AlarmManager alarmManager;

    private static String IP_ADDRESS = "159.89.193.200/nm_set_alm.php";
    //private static String URL = "http://159.89.193.200//plusSubject.php";
    //private static String alm_url = "http://159.89.193.200/alarm_insert.php";
    private static String TAG = "setsubject";
    private TimePicker Alarm;

    //weekview이벤트 받아오기
    WeekViewEvent event;



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


    DatePickerDialog.OnDateSetListener Alm_Date_Set = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //updateLabel3();
        }
    };

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        TextView et_date = (TextView) findViewById(R.id.sub_start_date);
        et_date.setText(sdf.format(date));

    }




    /*private void updateLabel3() {
        String myFormat = "yyyy년 MM월 dd일 ";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView et_date = (TextView) findViewById(R.id.alm_date_set);
        et_date.setText(sdf.format(myCalendar.getTime()));

        alm_text_date = et_date.getText().toString();
    }*/

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

        end_time = findViewById(R.id.sub_end_time);
        t_picker = (TimePicker)findViewById(R.id.timePicker);
        t_picker.setIs24HourView(true);
        d_picker = (DatePicker)findViewById(R.id.datePicker);

        //Title= (EditText)findViewById(R.id.editText_main_title);
        Alarm = (TimePicker)findViewById(R.id.timePicker);

        //Result.setMovementMethod(new ScrollingMovementMethod());

        sub_star = findViewById(R.id.sub_ratingBar);


        //저장된 값으로 설정되어 창에 뜨게끔.
        //event = getIntent().getParcelableExtra()
        event = (WeekViewEvent)getIntent().getParcelableExtra("eventsub");
        et_subject_title.setText(event.getName());
        et_subject_memo.setText(event.getmMemo());
        start_date.setText(event.getmStartDate());
        start_time.setText(event.getsTime().substring(0, 5));
        end_date.setText(event.getmEndDate());
        end_time.setText(event.geteTime().substring(0, 5));
        sub_star.setRating(Float.valueOf(event.getmStar()));



        //final TimePicker t_picker=(TimePicker)findViewById(R.id.timePicker);
        //final DatePicker d_picker = (DatePicker)findViewById(R.id.datePicker);
        //t_picker.setIs24HourView(true);

        //알람설정 part.1
        // 이전 설정값으로 TimePicker 초기화
        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(Calendar.getInstance().getTimeInMillis());

        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));

        if (Build.VERSION.SDK_INT >= 23 ){
            t_picker.setHour(pre_hour);
            t_picker.setMinute(pre_minute);
        }
        else{
            t_picker.setCurrentHour(pre_hour);
            t_picker.setCurrentMinute(pre_minute);
        }
        //알람설정 part.1 end

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateSubjectEdit.this, StartDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //알람 데이트 피커
        /*alm_date_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateSubject.this, Alm_Date_Set, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateSubjectEdit.this, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(CreateSubjectEdit.this, new TimePickerDialog.OnTimeSetListener() {
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

        //알람 타임 피커
       /* alm_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateSubject.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        // EditText에 출력할 형식 지정
                        alm_set.setText(hourOfDay + ":" + minute);

                        Log.d(TAG, "hourOf= " + hourOfDay);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                //mTimePicker.get

            }
        });*/


        btn_subject_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_subject_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = PreferenceManager.getString(CreateSubjectEdit.this, "email");
                //String email = "14dnfnfn@gmail.com"; //임시


                final int id = event.getID();
                final String title = et_subject_title.getText().toString();
                final String memo = et_subject_memo.getText().toString();
                final String date = start_date.getText().toString();
                final String time = start_time.getText().toString();
                final String enddate = end_date.getText().toString();
                final String endtime = end_time.getText().toString();
                final int importance = (int) sub_star.getRating();
                final int getcateid = getIntent().getIntExtra("cateid", 1);


                int a_year, a_month, a_date, a_hour, a_hour_24, a_minute;
                String am_pm;
                //int year, month, pdate, hour, hour_24, minute;

                //

                //DB 업데이트

                final ProgressDialog progressDialog = new ProgressDialog(CreateSubjectEdit.this);
                progressDialog.setMessage("update..");
                progressDialog.show();

                StringRequest request = new StringRequest(Request.Method.POST,"http://159.89.193.200/editSubject.php",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(CreateSubjectEdit.this,response,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateSubjectEdit.this, CalendarView.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                            }
                        },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(CreateSubjectEdit.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }

                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String,String>();

                        params.put("id", String.valueOf(id));
                        params.put("email", email);
                        params.put("title", title);
                        params.put("memo", memo);
                        params.put("date", date);
                        params.put("time", time);
                        params.put("importance", String.valueOf(importance));
                        params.put("enddate", enddate);
                        params.put("endtime", endtime);

                        return params;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(CreateSubjectEdit.this);
                queue.add(request);


                //알람설정 part.2
                if (Build.VERSION.SDK_INT >= 23 ){
                    a_year = d_picker.getYear();
                    a_month = d_picker.getMonth();
                    a_date = d_picker.getDayOfMonth();
                    a_hour_24 = t_picker.getHour();
                    a_minute = t_picker.getMinute();
                }
                else{
                    a_year = d_picker.getYear();
                    a_month = d_picker.getMonth();
                    a_date = d_picker.getDayOfMonth();
                    a_hour_24 = t_picker.getCurrentHour();
                    a_minute = t_picker.getCurrentMinute();
                }
                if(a_hour_24 > 12) {
                    am_pm = "PM";
                    a_hour = a_hour_24 - 12;
                }
                else
                {
                    a_hour = a_hour_24;
                    am_pm="AM";
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.YEAR, a_year);
                calendar.set(Calendar.MONTH, a_month);
                calendar.set(Calendar.DATE, a_date);
                calendar.set(Calendar.HOUR_OF_DAY, a_hour_24);
                calendar.set(Calendar.MINUTE, a_minute);
                calendar.set(Calendar.SECOND, 0);
                Log.d(TAG, "calendar = " + calendar);

                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("yyyy-MM-dd-a hh-mm", Locale.getDefault()).format(currentDateTime);
                //triggertime = Long.parseLong(date_text);

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS, date_text);
                diaryNotification(calendar);
            }
        });
    }//onCreate 끝

    void diaryNotification(Calendar calendar) {
        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, Ex_AlarmReceiver.class);
        // alarmIntent.setAction(AlarmReceiver);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d(TAG, "cal_ddd= " + this.getClass());
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Log.d(TAG, "Yohoho" + result);
        }

        @Override
        protected String doInBackground(String...params){
            String severurl = (String)params[0];
            String alm = (String)params[1];
            String postParameters;

            postParameters = "alm=" + alm;
            //postParameters = "normal=" + Integer.toString(n_theme1) + "&premium=" + Integer.toString(p_theme1);

            Log.d(TAG, "postparam = " + postParameters);

            try {

                java.net.URL url = new URL(severurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

}

