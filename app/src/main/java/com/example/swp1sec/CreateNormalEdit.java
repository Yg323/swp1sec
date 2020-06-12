package com.example.swp1sec;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateNormalEdit extends AppCompatActivity {

    private EditText et_normal_title,et_normal_memo;
    private TextView start_date, start_time, end_date, end_time;
    private Button btn_normal_save, btn_normal_cancel, btn_nor, btn_work;
    private RatingBar nor_star;
    private AlertDialog dialog;
    private static String URL = "http://159.89.193.200//plusNormal.php";
    private static String TAG = "setnormal";
    private RadioGroup radioGroup;
    private RadioButton r1, r2;
    Calendar myCalendar = Calendar.getInstance();

    WeekViewEvent event;

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

        radioGroup = findViewById(R.id.radio_normal);
        r1 = findViewById(R.id.radio_nor_check);
        r2 = findViewById(R.id.radio_work_check);

        PreferenceManager.setInt(CreateNormalEdit.this, "nor", 0);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){

                    case R.id.radio_nor_check :
                        PreferenceManager.setInt(CreateNormalEdit.this, "nor", 0);
                        break;
                    case R.id.radio_work_check:
                        PreferenceManager.setInt(CreateNormalEdit.this, "nor", 1);
                        break;
                }
            }
        });

        //저장된 값으로 설정되어 창에 뜨게끔.
        //event = getIntent().getParcelableExtra()
        event = (WeekViewEvent)getIntent().getParcelableExtra("eventnor");
        et_normal_title.setText(event.getName());
        et_normal_memo.setText(event.getmMemo());
        start_date.setText(event.getmStartDate());
        start_time.setText(event.getsTime().substring(0, 5));
        end_date.setText(event.getmEndDate());
        end_time.setText(event.geteTime().substring(0, 5));
        nor_star.setRating(Float.valueOf(event.getmStar()));


        final TimePicker picker = (TimePicker)findViewById(R.id.timePicker);
        picker.setIs24HourView(true);

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateNormalEdit.this, StartDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateNormalEdit.this, EndDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateNormalEdit.this, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(CreateNormalEdit.this, new TimePickerDialog.OnTimeSetListener() {
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
                final String email = PreferenceManager.getString(CreateNormalEdit.this, "email");
                //final String email = "14dnfnfn@gmail.com"; //임시
                final String title = et_normal_title.getText().toString();
                final String memo = et_normal_memo.getText().toString();
                final String date = start_date.getText().toString();
                final String time = start_time.getText().toString();
                final String enddate = end_date.getText().toString();
                final String endtime = end_time.getText().toString();
                final int division = PreferenceManager.getInt(CreateNormalEdit.this, "nor");
                final int importance = (int) nor_star.getRating();
                int getcateid = getIntent().getIntExtra("cateid", 1);
                final int id = event.getID();

                if (title.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateNormalEdit.this);
                    dialog = builder.setMessage("값을 입력해주세요!")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }

                //DB 업데이트

                final ProgressDialog progressDialog = new ProgressDialog(CreateNormalEdit.this);
                progressDialog.setMessage("update..");
                progressDialog.show();

                StringRequest request = new StringRequest(Request.Method.POST,"http://159.89.193.200/editNormal.php",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(CreateNormalEdit.this,response,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateNormalEdit.this, CalendarView.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                            }
                        },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(CreateNormalEdit.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
                        params.put("division", String.valueOf(division));


                        return params;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(CreateNormalEdit.this);
                queue.add(request);
            }
        });

        //알람 설정 파트
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);

        Date nextDate = nextNotifyTime.getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
        Toast.makeText(getApplicationContext(),"[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();


        // 이전 설정값으로 TimePicker 초기화
        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));


        if (Build.VERSION.SDK_INT >= 23 ){
            picker.setHour(pre_hour);
            picker.setMinute(pre_minute);
        }
        else{
            picker.setCurrentHour(pre_hour);
            picker.setCurrentMinute(pre_minute);
        }


        /*Button button = (Button) findViewById(R.id.n_alm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                int hour, hour_24, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour_24 = picker.getHour();
                    minute = picker.getMinute();
                }
                else{
                    hour_24 = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                if(hour_24 > 12) {
                    am_pm = "PM";
                    hour = hour_24 - 12;
                }
                else
                {
                    hour = hour_24;
                    am_pm="AM";
                }

                // 현재 지정된 시간으로 알람 시간 설정
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                Toast.makeText(getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

                //  Preference에 설정한 값 저장
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                editor.apply();


                diaryNotification(calendar);
            }
        });
    }//onCreate 끝

    void diaryNotification(Calendar calendar) {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }*/
    }
}
