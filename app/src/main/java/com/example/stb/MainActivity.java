package com.example.stb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void SendToStudy(View view) {
        Intent intent = new Intent(this, StudyTime.class);
        startActivity(intent);
    }
    public void SendToSleep(View view) {
        Intent intent = new Intent(this, SleepTime.class);
        startActivity(intent);
    }

    public void SendToLocation(View view) {
        Intent intent = new Intent(this, LocationMap.class);
        startActivity(intent);
    }

    public void SendToTasks(View view) {
        Intent intent = new Intent(this, Tasks.class);
        startActivity(intent);
    }
}