package com.example.fitnes.fitnes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.fitnes.fitnes.service.UserAuthTask;
import com.mysql.jdbc.StringUtils;


import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private UserAuthTask mAuthTask = null;
    private EditText loginArea, passArea;
//    private RelativeLayout relativeLayout;
    private LoginActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        relativeLayout = findViewById(R.id.linearLayout);
//        relativeLayout.setOnClickListener(null);
            listenerEventOnButton();
    }

    public void listenerEventOnButton() {
        Button loginBtn = findViewById(R.id.loginBtn);
        Button regBtn = findViewById(R.id.regBtn);
        loginArea = findViewById(R.id.loginArea);
        passArea = findViewById(R.id.passArea);

        loginBtn.setOnClickListener(
                v -> {
                    String login = loginArea.getText().toString();
                    String password = passArea.getText().toString();

                    if (!StringUtils.isEmptyOrWhitespaceOnly(login) &&
                            !StringUtils.isEmptyOrWhitespaceOnly(password)) {

                        SharedPreferences sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);
                        mAuthTask = new UserAuthTask(context, login, password, null,sharedPreferences);
                        mAuthTask.execute();
                    }
                }
        );
        regBtn.setOnClickListener(
                v -> {
                    Intent intent = new Intent(".RegActivity");
                    startActivity(intent);
                }
        );
    }


}
