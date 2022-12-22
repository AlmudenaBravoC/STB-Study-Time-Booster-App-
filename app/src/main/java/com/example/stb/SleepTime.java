package com.example.stb;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

// NEW IMPORTS
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

public class SleepTime extends AppCompatActivity {
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    ToggleButton toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_time);

        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmTimePicker.setIs24HourView(true); //change to 24h format
        //int actualH = alarmTimePicker.getHour(); //take the actual hour
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());                   // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 8);      //sum 8h, because is what we suggest
        int newH = cal.getTime().getHours();
        alarmTimePicker.setHour(newH);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        toggle = (ToggleButton) findViewById(R.id.toggleButton);
    }

    public void onSetAlarm(View view){
        long time;
        if (toggle.isChecked()) {
            Toast.makeText(SleepTime.this, R.string.on_alarm, Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();

            // calendar is called to get current time in hour and minute
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

            // using intent i have class AlarmReceiver class which inherits
            // BroadcastReceiver
            Intent intent = new Intent(this, AlarmReceiver.class);

            // we call broadcast using pendingIntent
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                // setting time as AM and PM
                if (Calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }

            // Alarm rings continuously until toggle button is turned off
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
            // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            // we need to cancel the the ringtone also, so it stops ringing when canceling the alarm
            if(AlarmReceiver.ringtone.isPlaying())
                AlarmReceiver.ringtone.stop();
            Toast.makeText(SleepTime.this, R.string.off_alarm, Toast.LENGTH_SHORT).show();
        }
    }
}