package com.example.fitnes.fitnes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.fitnes.fitnes.service.UserAuthTask;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class LoaderActivity extends AppCompatActivity {

    private LoaderActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        SharedPreferences sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("sessionId")) {
            String si = sharedPreferences.getString("sessionId", "");
            //Далее получаем данные пользователя по его sessionId
            UserAuthTask mAuthTask = new UserAuthTask(context, null, null, si,null);
            mAuthTask.execute();
        } else {

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            },1500);
        }
    }
}
