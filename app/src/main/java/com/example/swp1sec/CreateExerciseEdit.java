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

public class CreateExerciseEdit extends AppCompatActivity {
    private EditText et_exercise_title,et_exercise_memo;
    private TextView start_date, start_time, end_date, end_time;
    private Button btn_exercise_save, btn_exercise_cancel;
    private RatingBar ex_star;
    private AlertDialog dialog;
    TimePicker t_picker;
    DatePicker d_picker;
    long triggertime;
    AlarmManager alarmManager;
    WeekViewEvent event;

    private static String IP_ADDRESS = "159.89.193.200/set_alm.php";
    private static String URL = "http://159.89.193.200//plusExercise.php";
    private static String TAG = "setexercise";

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



    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView et_date = (TextView) findViewById(R.id.ex_start_date);
        et_date.setText(sdf.format(myCalendar.getTime()));

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        et_exercise_title = findViewById(R.id.et_exercise_title);
        et_exercise_memo = findViewById(R.id.et_exercise_memo);
        btn_exercise_save = findViewById(R.id.btn_exercise_save);
        btn_exercise_cancel = findViewById(R.id.btn_exercise_cancel);

        start_date = findViewById(R.id.ex_start_date);
        start_time = findViewById(R.id.ex_start_time);

        end_time = findViewById(R.id.ex_end_time);

        ex_star = findViewById(R.id.ex_ratingBar);

        t_picker = (TimePicker)findViewById(R.id.timePicker);
        t_picker.setIs24HourView(true);
        d_picker = (DatePicker)findViewById(R.id.datePicker);

        //저장된 값으로 설정되어 창에 뜨게끔.
        //event = getIntent().getParcelableExtra()
        event = (WeekViewEvent)getIntent().getParcelableExtra("eventex");
        et_exercise_title.setText(event.getName());
        et_exercise_memo.setText(event.getmMemo());
        start_date.setText(event.getmStartDate());
        start_time.setText(event.getsTime().substring(0, 5));
        end_date.setText(event.getmEndDate());
        end_time.setText(event.geteTime().substring(0, 5));
        ex_star.setRating(Float.valueOf(event.getmStar()));

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
                new DatePickerDialog(CreateExerciseEdit.this, StartDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateExerciseEdit.this, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(CreateExerciseEdit.this, new TimePickerDialog.OnTimeSetListener() {
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


        btn_exercise_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_exercise_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = PreferenceManager.getString(CreateExerciseEdit.this, "email");
                //String email = "14dnfnfn@gmail.com"; //임시
                final String title = et_exercise_title.getText().toString();
                final String memo = et_exercise_memo.getText().toString();
                final String date = start_date.getText().toString();
                final String time = start_time.getText().toString();
                final String enddate = end_date.getText().toString();
                final String endtime = end_time.getText().toString();
                int a_year, a_month, a_date, a_hour, a_hour_24, a_minute;
                String am_pm;
                int getcateid = getIntent().getIntExtra("cateid", 1);

                final int importance = (int) ex_star.getRating();
                final int id = event.getID();

                if (title.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateExerciseEdit.this);
                    dialog = builder.setMessage("값을 입력해주세요!")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }
                //DB 업데이트

                final ProgressDialog progressDialog = new ProgressDialog(CreateExerciseEdit.this);
                progressDialog.setMessage("update..");
                progressDialog.show();

                StringRequest request = new StringRequest(Request.Method.POST,"http://159.89.193.200/editExercise.php",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(CreateExerciseEdit.this,response,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateExerciseEdit.this, CalendarView.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                            }
                        },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(CreateExerciseEdit.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
                        //params.put("division", String.valueOf(division));

                        return params;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(CreateExerciseEdit.this);
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

                CreateExerciseEdit.InsertData task = new CreateExerciseEdit.InsertData();
                task.execute("http://" + IP_ADDRESS, date_text);
                diaryNotification(calendar);
            }
        });
    }//onCreate end.

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
