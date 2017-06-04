package com.ajaydewari.alarmmanagersample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int REPEATINTERVAL=10000;
    private static final int STARTAFTER=5000;
    private boolean USER_CONTINUE_OR_NOT=true;
    AlarmManager alarmManager;
    Intent intent;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void startAlarm(boolean isNotification, boolean isRepeat){
        alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if(isNotification){
            intent=new Intent(MainActivity.this, AlarmToastReceiver.class);
            pendingIntent=PendingIntent.getBroadcast(this, 0, intent,0);
        }else{
            intent=new Intent(MainActivity.this, AlarmToastReceiver.class);
            pendingIntent=PendingIntent.getBroadcast(this, 0, intent,0);
        }

        if(isRepeat){
            alarmManager.setRepeating(AlarmManager.RTC, SystemClock.elapsedRealtime()+STARTAFTER,REPEATINTERVAL,pendingIntent);
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+STARTAFTER,pendingIntent);
        }
    }

    //    to stop the repeating alarm use alarmManager.cancel(pendingIntent);﻿
    private void stopRepeatingAlarm(){
        if(alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }
    }

    private void scheduleEveryOneHour(){
        Intent intent=new Intent(MainActivity.this, WakeupAfterOneHoure.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // wake up time every 1 hour
        Calendar wakeUpTime = Calendar.getInstance();
        wakeUpTime.add(Calendar.SECOND, 60 * 60);

        AlarmManager aMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        aMgr.set(AlarmManager.RTC_WAKEUP,
                wakeUpTime.getTimeInMillis(),
                pendingIntent);
    }

    class WakeupAfterOneHoure extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // if phone is lock use PowerManager to acquire lock

            // your code to handle operations every one hour...

            // after that call again your method to schedule again
            // if you have boolean if the user doesnt want to continue
            // create a Preference or store it and retrieve it here like
            boolean mContinue = USER_CONTINUE_OR_NOT;//

            if(mContinue){
                scheduleEveryOneHour();
            }
        }
    }

}
