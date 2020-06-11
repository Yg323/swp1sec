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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.library.WeekView;
import com.example.swp1sec.data.Event;

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
import java.util.Locale;

public class CreateSubject extends AppCompatActivity {

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
    private static String URL = "http://159.89.193.200//plusSubject.php";
    //private static String alm_url = "http://159.89.193.200/alarm_insert.php";
    private static String TAG = "setsubject";
    private TimePicker Alarm;


    private static final String INTENT_EXTRA_EVENT = "intent_extra_event";

    public static Intent makeIntent(Context context, @NonNull Event event) {
        return new Intent(context, CreateSubject.class).putExtra(INTENT_EXTRA_EVENT, event);
    }

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

    DatePickerDialog.OnDateSetListener Alm_Date_Set = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel3();
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

    private void updateLabel3() {
        String myFormat = "yyyy년 MM월 dd일 ";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView et_date = (TextView) findViewById(R.id.alm_date_set);
        et_date.setText(sdf.format(myCalendar.getTime()));

        alm_text_date = et_date.getText().toString();
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
        //알람
        alm_set = findViewById(R.id.alm_set);
        alm_date_set = findViewById(R.id.alm_date_set);

        //Title= (EditText)findViewById(R.id.editText_main_title);
        Alarm = (TimePicker)findViewById(R.id.timePicker);

        //Result.setMovementMethod(new ScrollingMovementMethod());

        sub_star = findViewById(R.id.sub_ratingBar);

        //final TimePicker t_picker=(TimePicker)findViewById(R.id.timePicker);
        //final DatePicker d_picker = (DatePicker)findViewById(R.id.datePicker);
        //t_picker.setIs24HourView(true);

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

        //알람 데이트 피커
        alm_date_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateSubject.this, Alm_Date_Set, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

                        // EditText에 출력할 형식 지정
                        end_time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        //알람 타임 피커
        alm_set.setOnClickListener(new View.OnClickListener() {
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
                //String alarm = Alarm.getText().toString();
                String alarm = alm_set.getText().toString();
                Log.d(TAG, "alarm= " + alarm);
                String memo = et_subject_memo.getText().toString();
                String date = start_date.getText().toString();
                String time = start_time.getText().toString();
                String enddate = end_date.getText().toString();
                String endtime = end_time.getText().toString();
                int getcateid = getIntent().getIntExtra("cateid", 1);
                String am_pm;

                int importance = (int) sub_star.getRating();
                //int year, month, pdate, hour, hour_24, minute;

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

                                //onBackPressed();
                                Intent intent = new Intent(CreateSubject.this, CalendarView.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
                CreateSubjectRequest createSubjectRequest = new CreateSubjectRequest(email, title, memo, date, time, enddate, endtime, importance, alarm, getcateid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateSubject.this);
                queue.add(createSubjectRequest);


                // 현재 지정된 시간으로 알람 시간 설정
                Calendar calendar = Calendar.getInstance();

                // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                //String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                //Toast.makeText(getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
                //alarm = date_text;

                //  Preference에 설정한 값 저장
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                editor.apply();

                //일정 추가 시, 알람시간을 DB로 넘겨줌
                //InsertData task = new InsertData();
                //task.execute(alm_url, alarm);
                //Title.setText("");
                //Alarm.setText("");

                //diaryNotification(calendar);

            }

        });

        //알람 설정 파트
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar(year, month, day, hour, minute);

        Date nextDate = nextNotifyTime.getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
        //Toast.makeText(getApplicationContext(),"[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();


        // 이전 설정값으로 TimePicker 초기화
        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));

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
            /*pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);*/

        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }

}

