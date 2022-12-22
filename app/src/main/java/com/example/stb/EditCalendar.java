package com.example.stb;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class EditCalendar extends AppCompatActivity {
    Button startDateBut;
    Button endDateBut;
    EditText titleText;
    EditText bodyText;
    CalendarView datePicker;
    Intent intent;
    Calendar calendar;

    Button addEvent; //to add the event in the calendar

    int hour, minute;
    int hour2, minute2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_edit);

        titleText = findViewById(R.id.titleCalendar);
        bodyText = findViewById(R.id.bodyCalendar);
        startDateBut = findViewById(R.id.startDate);
        endDateBut = findViewById(R.id.endDate);
        datePicker = findViewById(R.id.calendarId);

        //FIRST we need to set the day
        calendar = Calendar.getInstance();
        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
            }});

        addEvent = findViewById(R.id.confirmEvent);
        addEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!titleText.getText().toString().isEmpty() && !bodyText.getText().toString().isEmpty()) {
                    intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);

                    intent.putExtra(CalendarContract.Events.TITLE, titleText.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, bodyText.getText().toString());

                    //for the hours of the days
                    intent.putExtra(CalendarContract.Events.ALL_DAY, false);

                    //set start hour
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis());

                    //set end hour
                    calendar.set(Calendar.HOUR_OF_DAY, hour2);
                    calendar.set(Calendar.MINUTE, minute2);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTimeInMillis());

                    // open the calendar app of the android mobile
                    startActivity(intent);


                } else {
                    //if some of the fields are null
                    Toast.makeText(EditCalendar.this, R.string.please_fill,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                startDateBut.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Start");
        timePickerDialog.show();

    }

    public void popTimePickerEnd(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour2 = selectedHour;
                minute2 = selectedMinute;
                endDateBut.setText(String.format(Locale.getDefault(), "%02d:%02d", hour2, minute2));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener2, hour2, minute2, true);

        timePickerDialog.setTitle("Select End");
        timePickerDialog.show();

    }
}

