package com.example.swp1sec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class Badge_Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){

        ((badge_dialog)badge_dialog.mContext).setAlarm();

        Toast.makeText(context,"알람이 울립니다 ",Toast.LENGTH_SHORT).show();
        Log.e("알람","알람입니다");
    }


}
