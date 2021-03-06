package com.example.swp1sec;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.example.swp1sec.CalendarView.badge_time_text;

public class habit_badge_dialog  extends AppCompatActivity {

    public String email;
    public static Context habitContext;
    private int badgehour, badgeminute;
    private TimePicker timepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        habitContext = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.habitbadge_dialog);
        timepicker=findViewById(R.id.habitimepicker);



    }
    /*habit badge*/
    public static final String habitnotificationChannelId = "habit";

    public void habitsetAlarm(){


        habitcreateNotification(badge_habit_data.getbadge_habit());
        finish();
    }
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

    public void habit_regist(View view) {

        Intent intent = new Intent(this, Habit_Badge_Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            badgehour = timepicker.getHour();
            badgeminute = timepicker.getMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, badgehour);
        calendar.set(Calendar.MINUTE, badgeminute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        //지정한 시간에 매일 알림
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
        Toast.makeText(this, badgehour+" : "+badgeminute +" 습관 설정완료", Toast.LENGTH_SHORT).show();
        PreferenceManager.setString(habit_badge_dialog.this,"badgetime",badgehour+" : "+badgeminute);
        PreferenceManager.setString(habit_badge_dialog.this,"badgetype","습관");
        habitremoveNotification();
        badge_time_text.setText(badgehour+" : "+badgeminute);
        finish();





    }

    public void habit_unregist(View view) {
        Intent intent = new Intent(this, Habit_Badge_Alarm.class);
        PendingIntent plntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(plntent);
        Toast.makeText(this, "알람취소 완료", Toast.LENGTH_SHORT).show();
        habitremoveNotification();
        badge_time_text.setText("");
        finish();


    }
}
