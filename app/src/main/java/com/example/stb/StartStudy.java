package com.example.stb;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class StartStudy extends  AppCompatActivity {


    int progress;
    CountDownTimer countDownTimer;
    int total_time = 5*1000; //25 mins for the pomodoro technique
    ProgressBar indicatorBar;
    TextView title_study;

    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    int break_time;
    AlertDialog.Builder ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_study);

        title_study = findViewById(R.id.title_study);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        indicatorBar = findViewById(R.id.progressBarPomodoro);
        indicatorBar.setIndeterminate(false);
        indicatorBar.setProgress(0); //set to 0

        pomodoro_run();
        }

    public void pomodoro_run(){
        System.out.println(StudyTime.parts);

        title_study.setText(R.string.pomodoro);
        progress=0;

        countDownTimer = new CountDownTimer(total_time, 1000) {
            @Override
            public void onTick(long l) {
                progress++;
                indicatorBar.setProgress((int)progress*100/(total_time/1000));
            }

            @Override
            public void onFinish() {
                progress++;
                indicatorBar.setProgress(100);

                //once is finish --> alarm
                setAlarmPomi();

                //once is finish --> boost
                ques = new AlertDialog.Builder(StartStudy.this);
                if (!StudyTime.longBreak){
                    ques.setTitle(R.string.title_dialog);
                }else{
                    ques.setTitle(R.string.title_dialog_long);
                }
                ques.setMessage(R.string.message_dialog);
                ques.setPositiveButton(R.string.ques_positive,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alarmOFF();
                                Intent intent_boost = new Intent(StartStudy.this, ChooseCategory.class);
                                // open the new window for the boost
                                StartStudy.this.finish();
                                startActivity(intent_boost);
                                }
                            });
                ques.setNegativeButton(R.string.ques_negative,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //alarm off
                                alarmOFF();

                                //change title
                                title_study.setText(R.string.title_dialog);

                                //break time
                                indicatorBar.setProgress(0);
                                progress = 0;
                                    //check if it is a long break or not
                                if (!StudyTime.longBreak){
                                    break_time=5*60*1000; //5min short break
                                }else{
                                    break_time=30*60*1000; //30 min long break
                                }
                                    //start the counter
                                countDownTimer = new CountDownTimer(break_time, 1000) {
                                    @Override
                                    public void onTick(long l) {
                                        progress++;
                                        indicatorBar.setProgress((int) progress * 100 / (break_time/ 1000));
                                    }

                                    @Override
                                    public void onFinish() {
                                        setAlarmPomi(); //set alarm (end break)
                                        progress++;
                                        indicatorBar.setProgress(100);

                                        //once is finish --> alarm
                                        AlertDialog.Builder ques2 = new AlertDialog.Builder(StartStudy.this);
                                        ques2.setTitle(R.string.title_dialog2);
                                        ques2.setMessage(R.string.message_dialog2);
                                        ques2.setPositiveButton(R.string.ques_positive,
                                                new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    //alarm off
                                                    alarmOFF();

                                                    if (!StudyTime.longBreak){
                                                        StudyTime.parts--;
                                                        if (StudyTime.parts==1){ //there is only 1 part left, the next break is long break
                                                            StudyTime.longBreak = true;
                                                        }
                                                        pomodoro_run();
                                                    }else{
                                                        //end of the long break
                                                        StudyTime.longBreak=false;
                                                        StudyTime.numCycles--; //decrease one cycle from total
                                                        if (StudyTime.numCycles!=0){
                                                            StudyTime.parts=4; //restart the 4 parts
                                                            pomodoro_run();
                                                        }else{
                                                            Intent intent_end = new Intent(StartStudy.this, StudyTime.class);
                                                            Toast.makeText(StartStudy.this, R.string.end_cycles, Toast.LENGTH_SHORT).show();

                                                            startActivity(intent_end); //back to home
                                                        }

                                                    }

                                                }
                                            }
                                        );
                                        ques2.show();
                                    }
                                };
                                countDownTimer.start();
                            }
                        });
                ques.show();
            }
        };
        countDownTimer.start();



    }

    public void setAlarmPomi(){
        Calendar calendar = Calendar.getInstance();
        Intent intentAlarm = new Intent(StartStudy.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(StartStudy.this, 0, intentAlarm, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void alarmOFF(){
        alarmManager.cancel(pendingIntent);
        if (AlarmReceiver.ringtone.isPlaying())
            AlarmReceiver.ringtone.stop();
        Toast.makeText(StartStudy.this, R.string.off_alarm, Toast.LENGTH_SHORT).show();
    }


}

