package com.example.swp1sec;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import me.leolin.shortcutbadger.ShortcutBadger;

/*import com.github.arturogutierrez.Badges;
import com.github.arturogutierrez.BadgesNotSupportedException;*/

public class badge_dialog extends AppCompatActivity {



    //todo
    private String badge_todojsonString;
    private static String badge_todoURL = "http://159.89.193.200//badge_todo.php";
    private static String badge_todoTAG = "getbadge_todo";
    //habit
    private String badge_habitjsonString;
    private static String badge_habitURL = "http://159.89.193.200//badge_habit.php";
    private static String badge_habitTAG = "getbadge_habit";
    Button todo, habit, todo_habit;
    Thread thread;
    public String email;

    //알람
    private TimePicker timepicker;
    private AlarmManager alarmManager;
    private int badgehour, badgeminute;
    TextView todo_text,badge;


    //추가
    public static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //추가
        mContext = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.badge_dialog);

        //알람
        timepicker = findViewById(R.id.badgetimepicker);

        //badge 투두

        email = PreferenceManager.getString(badge_dialog.this, "email");
        badge_todo_Data badge_todotask = new badge_todo_Data(); //밑에 만들었던 클래스 만들고
        badge_todotask.execute(badge_todoURL, email); //task 실행


        //badge 해빗
        email = PreferenceManager.getString(badge_dialog.this, "email");
        badge_habit_Data badge_habittask = new badge_habit_Data(); //밑에 만들었던 클래스 만들고
        badge_habittask.execute(badge_habitURL, email); //task 실행


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setAlarm(){

        todocreateNotification(badge_todo_data.getbadge_todo());
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void todohabitsetAlarm(){

        todohabitcreateNotification(badge_habit_data.getbadge_habit() + badge_todo_data.getbadge_todo());

        finish();
    }

    /*todo+habit badge*/
    public static final String todohabitnotificationChannelId = "todohabit";

    @TargetApi(Build.VERSION_CODES.O)
    public void todohabitcreateNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager)getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(todohabitnotificationChannelId, "남은 할일 습관", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public static int todohabitnotificationId = 12312;

    public void todohabitcreateNotification(int badgeCount) {
        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

        //하위 버전에서 동작하라고...??, 아직 하위버전은 테스트 해보지 못함.
        ShortcutBadger.applyCount(this, badgeCount);
        todohabitcreateNotificationChannel();
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("남은 할일 습관")
                .setContentText("")
                .setNumber(badgeCount)
                //statusBar 및 notification view에 표시되는 작은 아이콘
                .setSmallIcon(R.drawable.img_1sec)
                //클릭 시 자동 cancel(삭제)
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
            builder.setChannelId(todohabitnotificationChannelId);

        }

        //클릭했을 때, 해당 Activity로 이동시키기 위해 추가
        Intent intent = new Intent(this, HabitListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();

        //폰 제조사가 Xiaomi일 경우만, 뭔가 별도 처리하는 듯. 일단 추가
        ShortcutBadger.applyNotification(this, notification, badgeCount);
        notificationManager.notify(todohabitnotificationId, notification);
    }


    public void todohabitremoveNotification() {
        NotificationManager notificationManager = (NotificationManager)getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        //하위 버전에서 동작하라고...??, 아직 하위버전은 테스트 해보지 못함.
        ShortcutBadger.removeCount(this);
        notificationManager.cancel(todohabitnotificationId);
    }

    /*habit badge*/
    public static final String habitnotificationChannelId = "habit";

    @TargetApi(Build.VERSION_CODES.O)
    public void habitcreateNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(habitnotificationChannelId, "남은 습관", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public static int habitnotificationId = 1231;

    public void habitcreateNotification(int badgeCount) {
        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

        //하위 버전에서 동작하라고...??, 아직 하위버전은 테스트 해보지 못함.
        ShortcutBadger.applyCount(this, badgeCount);
        habitcreateNotificationChannel();
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("남은 습관")
                .setContentText("")
                .setNumber(badgeCount)
                //statusBar 및 notification view에 표시되는 작은 아이콘
                .setSmallIcon(R.drawable.img_1sec)
                //클릭 시 자동 cancel(삭제)
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
            builder.setChannelId(habitnotificationChannelId);

        }

        //클릭했을 때, 해당 Activity로 이동시키기 위해 추가
        Intent intent = new Intent(this, HabitListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();

        //폰 제조사가 Xiaomi일 경우만, 뭔가 별도 처리하는 듯. 일단 추가
        ShortcutBadger.applyNotification(this, notification, badgeCount);
        notificationManager.notify(habitnotificationId, notification);
    }


    public void habitremoveNotification() {
        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        //하위 버전에서 동작하라고...??, 아직 하위버전은 테스트 해보지 못함.
        ShortcutBadger.removeCount(this);
        notificationManager.cancel(habitnotificationId);
    }


    /*todo badge*/

    public  static final String todonotificationChannelId = "todo";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void todocreateNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(todonotificationChannelId, "남은 할일", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public static int todonotificationId = 123;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void todocreateNotification(int badgeCount) {
        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        todocreateNotificationChannel();
        //하위 버전에서 동작하라고...??, 아직 하위버전은 테스트 해보지 못함.
        ShortcutBadger.applyCount(badge_dialog.this, badgeCount);

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("남은 할일")
                .setContentText("")
                .setNumber(badgeCount)
                //statusBar 및 notification view에 표시되는 작은 아이콘
                .setSmallIcon(R.drawable.img_1sec)
                //클릭 시 자동 cancel(삭제)
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
            builder.setChannelId(todonotificationChannelId);

        }

        //클릭했을 때, 해당 Activity로 이동시키기 위해 추가
        Intent intent = new Intent(this, TodoListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();

        //폰 제조사가 Xiaomi일 경우만, 뭔가 별도 처리하는 듯. 일단 추가
        ShortcutBadger.applyNotification(this, notification, badgeCount);
        notificationManager.notify(todonotificationId, notification);
    }


    public void removeNotification() {
        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        //하위 버전에서 동작하라고...??, 아직 하위버전은 테스트 해보지 못함.
        ShortcutBadger.removeCount(this);
        notificationManager.cancel(todonotificationId);
    }

    //투두 데이타
    private class badge_todo_Data extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);


            //mTextViewResult.setText(result);
            Log.d(badge_todoTAG, "response - " + result);

            if (result == null) {
                //mTextViewResult.setText(errorString);
            } else {
                badge_todojsonString = result; //크롬으로 확인했던 문자열 받아오고
                badge_todoShowResult(); //밑에 dayHabitShowResult함수 실행
            }
        }

        @Override
        protected String doInBackground(String... params) { //task.excute로 넘겨주는 매개변수들

            String serverURL = params[0]; //PHPURL
            String email = (String) params[1]; //email

            String postParameters = "email=" + email; //php 파일에 $_POST 변수가 받기 위한 코드

            try { //여기부턴 php코드 한줄씩 읽는거니까 그냥 읽기만 해봐

                java.net.URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(badge_todoTAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(badge_todoTAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void badge_todoShowResult() { //이부분 잘봐

        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그
        String TAG_title = "performance";
        String TAG_todo = "todocount";


        try {
            JSONObject jsonObject = new JSONObject(badge_todojsonString);
            boolean success = jsonObject.getBoolean("success");
            if (success) {
                String todoCount = jsonObject.getString("todocount");// 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와


                badge_todo_data badge_todo_data = new badge_todo_data(todoCount);
                int todoCountint = Integer.parseInt(todoCount);
                badge_todo_data.setbadge_todo(todoCountint);
            }


        } catch (JSONException e) {
            Log.d(badge_todoTAG, "showResult : ", e);
        }
    }


    private class badge_habit_Data extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);


            //mTextViewResult.setText(result);
            Log.d(badge_habitTAG, "response - " + result);

            if (result == null) {
                //mTextViewResult.setText(errorString);
            } else {
                badge_habitjsonString = result; //크롬으로 확인했던 문자열 받아오고
                badge_habitShowResult(); //밑에 dayHabitShowResult함수 실행
            }
        }

        @Override
        protected String doInBackground(String... params) { //task.excute로 넘겨주는 매개변수들

            String serverURL = params[0]; //PHPURL
            String email = (String) params[1]; //email

            String postParameters = "email=" + email; //php 파일에 $_POST 변수가 받기 위한 코드

            try { //여기부턴 php코드 한줄씩 읽는거니까 그냥 읽기만 해봐

                java.net.URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(badge_habitTAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(badge_habitTAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void badge_habitShowResult() { //이부분 잘봐

        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그
        String TAG_title = "performance";
        String TAG_habit = "habitcount";


        try {
            JSONObject jsonObject = new JSONObject(badge_habitjsonString);
            boolean success = jsonObject.getBoolean("success");
            if (success) {
                String habitCount = jsonObject.getString("habitcount");// 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와


                badge_habit_data badge_todo_data = new badge_habit_data(habitCount);
                int habitCountint = Integer.parseInt(habitCount);
                badge_todo_data.setbadge_habit(habitCountint);
            }


        } catch (JSONException e) {
            Log.d(badge_habitTAG, "showResult : ", e);
        }
    }



    public void regist(View view) {
        Intent intent = new Intent(this, Badge_Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            badgehour = timepicker.getHour();
            badgeminute = timepicker.getMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, badgehour);
        calendar.set(Calendar.MINUTE, badgeminute-1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        //지정한 시간에 매일 알림
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
        Toast.makeText(this, badgehour+" : "+badgeminute +" 할일 설정완료", Toast.LENGTH_SHORT).show();
        finish();




    }

    public void unregist(View view) {
        Intent intent = new Intent(this, Badge_Alarm.class);
        PendingIntent plntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(plntent);
        Toast.makeText(this, "할 일 알람취소 완료", Toast.LENGTH_SHORT).show();
        removeNotification();
        finish();


    }


}


