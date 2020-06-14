package com.example.swp1sec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


public class Habit_Badge_Alarm extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent){

        ((habit_badge_dialog)habit_badge_dialog.habitContext).habitsetAlarm();


        Toast.makeText(context,"알람이 울립니다 ",Toast.LENGTH_SHORT).show();
        Log.e("알람","알람입니다");
    }


}
