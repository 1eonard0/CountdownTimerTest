package com.dreadnought.countdowntest;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static long TIME_IN_FUTURE, INTERVAL=1000;
    private boolean running = false;
    private TextView time;
    private Button operation;
    private CountDownTimer countDownTimer;
    private boolean background;
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = false;
        constraintLayout = findViewById(R.id.layout);
        time = findViewById(R.id.textViewTime);
        operation = findViewById(R.id.buttonOp);
        operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeTime();
                InitTimer();
            }
        });
    }

    private void InitTimer(){
        if (running){
            countDownTimer.cancel();
            operation.setText("Start");
            time.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
            time.setTextColor(Color.GRAY);
            initializeTime();
            constraintLayout.setBackgroundResource(android.R.color.holo_orange_light);
            time.setEnabled(true);
            running = false;
            background = false;
        }else{
            operation.setText("Restart");
            time.setEnabled(false);
            countDownTimer = new CountDownTimer(TIME_IN_FUTURE, INTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished / 1000 <= 5){
                        time.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
                        time.setTextColor(Color.RED);

                        if (!background){
                            constraintLayout.setBackgroundResource(R.drawable.gradient_radial_list);
                            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
                            animationDrawable.start();
                            background = true;
                        }
                    }
                    time.setText(String.valueOf(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    constraintLayout.setBackgroundResource(android.R.color.holo_orange_light);
                    operation.setText("Start");
                    time.setText("");
                    initializeTime();
                    time.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
                    time.setTextColor(Color.GRAY);
                    running = false;
                    background = false;
                }
            }.start();
            running = true;
        }
    }

    private void initializeTime(){
        if (time.getText().toString().isEmpty()){
            TIME_IN_FUTURE = 10000;
        }else{
            TIME_IN_FUTURE = Integer.parseInt(time.getText().toString())*1000;
        }
        time.setText(String.valueOf(TIME_IN_FUTURE/1000));
    }
}
