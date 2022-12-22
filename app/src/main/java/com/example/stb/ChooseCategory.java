package com.example.stb;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class ChooseCategory extends  AppCompatActivity {
    public static String category_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecat);

        Button cancel = findViewById(R.id.CANCEL);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_end = new Intent(ChooseCategory.this, MainActivity.class);
                Toast.makeText(ChooseCategory.this, R.string.cancel_study, Toast.LENGTH_SHORT).show();

                startActivity(intent_end); //back to home
                ChooseCategory.this.finish();
            }
        });

    }

    public void SendMovement(View view) {
        category_type = "MOVEMENT";
        Intent intent = new Intent(this, BoostStudy.class);
        startActivity(intent);
        this.finish();
    }
    public void SendHistory(View view) {
        category_type = "HISTORY";
        Intent intent = new Intent(this, BoostStudy.class);
        startActivity(intent);
        this.finish();
    }

    public void SendRandom(View view) {
        category_type = "RANDOM";
        Intent intent = new Intent(this, BoostStudy.class);
        startActivity(intent);
        this.finish();
    }

}
