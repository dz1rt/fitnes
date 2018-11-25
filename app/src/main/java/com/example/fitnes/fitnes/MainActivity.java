package com.example.fitnes.fitnes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.fitnes.fitnes.personal.PersonalActivity;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences = null;
    private MainActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        TextView userName = findViewById(R.id.greating);
        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        userName.setText("Привет "+login);

        Animation mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        userName.startAnimation(mFadeInAnimation);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(context,PersonalActivity.class);
                startActivity(intent);
            }
        },3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(sharedPreferences.contains("sessionId")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("sessionId");
            editor.apply();
            Log.d(String.valueOf(sharedPreferences.contains("sessionId")),"asdasda");
        }
    }
}

