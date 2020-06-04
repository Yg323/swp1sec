package com.example.swp1sec;

import android.annotation.TargetApi;
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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

/*import com.github.arturogutierrez.Badges;
import com.github.arturogutierrez.BadgesNotSupportedException;*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.leolin.shortcutbadger.ShortcutBadger;

public class badge_dialog extends AppCompatActivity {
    private String badge_todojsonString;
    private static String badge_todoURL = "http://159.89.193.200//badge_todo.php";
    private static String badge_todoTAG = "getbadge_todo";







    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.badge_dialog);

        //badge 투두
        String email = "14dnfnfn@gmail.com";
        badge_todo_Data caltask = new badge_todo_Data(); //밑에 만들었던 클래스 만들고
        caltask.execute(badge_todoURL, email); //task 실행


    }


    public static final String notificationChannelId = "nofiticationChannelId";

    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel =new NotificationChannel(notificationChannelId, "notificationName", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
    }
    public static int notificationId = badge_todo_data.getbadge_todo();

    public void createNotification(int badgeCount) {
        NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        //하위 버전에서 동작하라고...??, 아직 하위버전은 테스트 해보지 못함.
        ShortcutBadger.applyCount(this, badgeCount);

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("알림 제목")
                .setContentText("알림 내용")
                .setNumber(badgeCount)
                //statusBar 및 notification view에 표시되는 작은 아이콘
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                //클릭 시 자동 cancel(삭제)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
            builder.setChannelId(notificationChannelId);
        }

        //클릭했을 때, 해당 Activity로 이동시키기 위해 추가
        Intent intent = new Intent(this, CalendarView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();
        //폰 제조사가 Xiaomi일 경우만, 뭔가 별도 처리하는 듯. 일단 추가
        /*ShortcutBadger.applyNotification(context, notification, badgeCount);
        notificationManager.notify(notificationId, notification);*/
    }
    public void removeNotification() {
        NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        //하위 버전에서 동작하라고...??, 아직 하위버전은 테스트 해보지 못함.
        ShortcutBadger.removeCount(this);
        notificationManager.cancel(notificationId);
    }

   /* public void todo_badge(View v){

        try{
            Badges.setBadge(badge_dialog.this,badge_todo_data.getbadge_todo());
        }catch (BadgesNotSupportedException error){
            Toast.makeText(this,"에러!",Toast.LENGTH_SHORT).show();
        }

        finish();
    }*/
    private class badge_todo_Data extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(badge_dialog.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(badge_todoTAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                badge_todojsonString = result; //크롬으로 확인했던 문자열 받아오고
                badge_todoShowResult(); //밑에 dayHabitShowResult함수 실행
            }
        }

        @Override
        protected String doInBackground(String... params) { //task.excute로 넘겨주는 매개변수들

            String serverURL = params[0]; //PHPURL
            String email = (String)params[1]; //email

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
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
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
        String TAG_todo ="todocount";


        try {
            JSONObject jsonObject = new JSONObject(badge_todojsonString);
            boolean success = jsonObject.getBoolean("success");
            if(success){
                String todoCount = jsonObject.getString("todocount");// 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와


                badge_todo_data badge_todo_data = new badge_todo_data(todoCount);
                int todoCountint=Integer.parseInt(todoCount);
                badge_todo_data.setbadge_todo(todoCountint);
            }


        } catch (JSONException e) {
            Log.d(badge_todoTAG, "showResult : ", e);
        }
    }

}


