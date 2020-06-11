package com.example.swp1sec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public  class Badge_Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){


        //badge_dialog.todocreateNotification(badge_todo_data.getbadge_todo());
        Toast.makeText(context,"알람",Toast.LENGTH_SHORT).show();
    }


}
