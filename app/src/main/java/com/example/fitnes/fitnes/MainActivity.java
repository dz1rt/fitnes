package com.example.fitnes.fitnes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView userName;
    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        userName = findViewById(R.id.greating);
        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        userName.setText("Привет "+login);
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

