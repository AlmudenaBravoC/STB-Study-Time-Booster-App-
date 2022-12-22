package com.example.stb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class BoostStudy extends  AppCompatActivity {
    private AppDatabase db;
    TextView titQues;
    ProgressBar indicatorBar;
    int progress;
    CountDownTimer countDownTimer;
    int total_time; //5 mins (long 30mins)

    Button correct_but;

    List<Integer> id_quesShow = new ArrayList<Integer>(); // to save the id of the questions already showed
    Integer num_ques; //save the chosen id

    Button ans1_but;
    Button ans2_but;
    Button ans3_but;
    Button ans4_but;
    //category_type --> category

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boost);

        db = AppDatabase.getInstance(getApplicationContext());

        ans1_but = findViewById(R.id.ans1);
        ans2_but = findViewById(R.id.ans2);
        ans3_but = findViewById(R.id.ans3);
        ans4_but = findViewById(R.id.ans4);

        Button cancel = findViewById(R.id.CANCELB);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_end = new Intent(BoostStudy.this, MainActivity.class);
                Toast.makeText(BoostStudy.this, R.string.cancel_study, Toast.LENGTH_SHORT).show();

                startActivity(intent_end); //back to home
                BoostStudy.this.finish();
            }
        });

        indicatorBar = findViewById(R.id.progressBarQuestion);
        indicatorBar.setIndeterminate(false);
        indicatorBar.setProgress(0); //set to 0

        if (ChooseCategory.category_type == "MOVEMENT"){
            movement_run();
        }else{
            quiz_model();
        }
    }

    public void movement_run(){
        titQues = findViewById(R.id.titleQues);
        List<Integer> id_list = db.questionsDao().loadAllIDsByCategory(ChooseCategory.category_type);

        Random rand = new Random();
        Integer idx = id_list.get(rand.nextInt(id_list.size()));

        Questions ques = db.questionsDao().findById(idx);

        //change the button visibility and the size of the title text
        titQues.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
        ViewGroup.LayoutParams params = titQues.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = 1130;
        params.width = 800;
        titQues.setLayoutParams(params);

        for (Button button : Arrays.asList(ans1_but, ans2_but, ans3_but, ans4_but)) {
            button.setVisibility(View.INVISIBLE);
        }

        titQues.setText(ques.statement);

        run_progress();
    }

    public void quiz_model(){
        titQues = findViewById(R.id.titleQues);

        //change the button visibility and the size of the title text
        titQues.setTextSize(TypedValue.COMPLEX_UNIT_DIP,24);
        ViewGroup.LayoutParams params = titQues.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = 300;
        params.width = 800;
        titQues.setLayoutParams(params);

        for (Button button : Arrays.asList(ans1_but, ans2_but, ans3_but, ans4_but)) {
            button.setVisibility(View.VISIBLE);
        }

        generate_number();
        run_progress();
    }

    public void generate_number(){
        List<Integer> id_list = db.questionsDao().loadAllIDsByCategory(ChooseCategory.category_type);
        Random rand = new Random();
        num_ques = rand.nextInt(id_list.size());
        System.out.println(id_list);

        if (id_quesShow.contains(num_ques) && id_list.size()>id_quesShow.size()){ //the number is already in the list
            generate_number();
        }else if(id_quesShow.size() == id_list.size()){
            id_quesShow.clear(); //remove all elements
            id_quesShow.add(num_ques); //add new value
            Integer idx = id_list.get(num_ques);
            quiz_run(idx);
        }else{
            id_quesShow.add(num_ques); //add new value
            Integer idx = id_list.get(num_ques);
            quiz_run(idx);
        }
    }

    public void quiz_run(Integer idx){
        for (Button button : Arrays.asList(ans1_but, ans2_but, ans3_but, ans4_but)) {
            button.setBackgroundColor(getResources().getColor(R.color.ButtonColor));
        }

        Questions ques = db.questionsDao().findById(idx);

        titQues.setText(ques.statement);
        ans1_but.setText(ques.ans1);
        ans2_but.setText(ques.ans2);
        ans3_but.setText(ques.ans3);
        ans4_but.setText(ques.ans4);

        if (ques.correct==1) {
            correct_but = ans1_but;
        }else if (ques.correct ==2){
            correct_but = ans2_but;
        }else if (ques.correct==3){
            correct_but = ans3_but;
        }else{
            correct_but = ans4_but;
        }

        ans1_but.setOnClickListener(new View.OnClickListener() {
            //@SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                if (ques.correct == 1) {
                    ans1_but.setBackgroundColor(getResources().getColor(R.color.correct_but));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            generate_number();
                        }
                    }, 5000);
                }else{
                    ans1_but.setBackgroundColor(getResources().getColor(R.color.pomodoro));
                    correct_but.setBackgroundColor(getResources().getColor(R.color.correct_but));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            generate_number();
                        }
                    }, 5000);

                }
            }
        });

        ans2_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ques.correct == 2) {
                    ans2_but.setBackgroundColor(getResources().getColor(R.color.correct_but));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            generate_number();
                        }
                    }, 5000);
                }else{
                    ans2_but.setBackgroundColor(getResources().getColor(R.color.pomodoro));
                    correct_but.setBackgroundColor(getResources().getColor(R.color.correct_but));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            generate_number();
                        }
                    }, 5000);
                }
            }
        });

        ans3_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ques.correct == 3) {
                    ans3_but.setBackgroundColor(getResources().getColor(R.color.correct_but));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            generate_number();
                        }
                    }, 5000);
                }else{
                    ans3_but.setBackgroundColor(getResources().getColor(R.color.pomodoro));
                    correct_but.setBackgroundColor(getResources().getColor(R.color.correct_but));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            generate_number();
                        }
                    }, 5000);
                }
            }
        });

        ans4_but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ques.correct == 4) {
                    ans4_but.setBackgroundColor(getResources().getColor(R.color.correct_but));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            generate_number();
                        }
                    }, 5000);
                }else{
                    ans4_but.setBackgroundColor(getResources().getColor(R.color.pomodoro));
                    correct_but.setBackgroundColor(getResources().getColor(R.color.correct_but));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            generate_number();
                        }
                    }, 5000);
                }
            }
        });

    }

    public void run_progress(){
        progress=0;

        if (!StudyTime.longBreak){
            total_time=5*60*1000; //5min
        }else{
            total_time=25*60*1000; //30min
        }

        countDownTimer = new CountDownTimer(total_time, 1000) {
            @Override
            public void onTick(long l) {
                progress++;
                indicatorBar.setProgress((int) progress * 100 / (total_time / 1000));
            }

            @Override
            public void onFinish() {
                progress++;
                indicatorBar.setProgress(100);

                //once is finish --> study
                AlertDialog.Builder ques3 = new AlertDialog.Builder(BoostStudy.this);
                ques3.setTitle(R.string.title_dialog2);
                ques3.setMessage(R.string.message_dialog2);
                ques3.setPositiveButton(R.string.ques_positive,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent_boost = new Intent(BoostStudy.this, StartStudy.class);
                                // open the new window for the boost

                                if (!StudyTime.longBreak) {
                                    StudyTime.parts--;
                                    if (StudyTime.parts == 1) { //there is only 1 part left, the next break is long break
                                        StudyTime.longBreak = true;
                                    }

                                    //send to startStudy again
                                    startActivity(intent_boost);
                                    BoostStudy.this.finish();
                                } else {
                                    //end of the long break
                                    StudyTime.longBreak = false;
                                    StudyTime.numCycles--; //decrease one cycle from total
                                    if (StudyTime.numCycles != 0) {
                                        StudyTime.parts = 4; //restart the parts

                                        //send to the starStudy again
                                        startActivity(intent_boost);
                                    } else {
                                        Intent intent_end = new Intent(BoostStudy.this, StudyTime.class);
                                        Toast.makeText(BoostStudy.this, R.string.end_cycles, Toast.LENGTH_SHORT).show();

                                        startActivity(intent_end); //back to home
                                        BoostStudy.this.finish();
                                    }
                                }
                            }
                        });
                ques3.show();
            }
        };
        countDownTimer.start();
    }


}

