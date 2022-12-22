package com.example.stb;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.content.Intent;
import android.view.View;

public class StudyTime extends  AppCompatActivity{
    NumberPicker numpick;

    public static boolean longBreak;
    public static int parts; //for 4 times
    public static int numCycles; //to save the number of cycles choosen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time);

        numpick = findViewById(R.id.numpickPomodoro);

        //to set the values of number pick
        numpick.setMinValue(1);
        numpick.setMaxValue(5);
    }

    public void startStudy(View view) { //the function that works with the button
        Intent intent = new Intent(this, StartStudy.class);
        numCycles = numpick.getValue();
        parts = 4;
        longBreak = false;

        startActivity(intent);
        this.finish();
    }



}
