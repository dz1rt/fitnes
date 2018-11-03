package com.example.fitnes.fitnes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fitnes.fitnes.personal.PersonalActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView userName;
    private SharedPreferences sharedPreferences = null;
    private Timer timer;
    private MainActivity context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        userName = findViewById(R.id.greating);
        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        userName.setText("Привет "+login);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(context,PersonalActivity.class);
                startActivity(intent);
            }
        },1500);
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

