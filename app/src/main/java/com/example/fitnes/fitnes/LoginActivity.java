package com.example.fitnes.fitnes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnes.fitnes.service.DatabaseConnect;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private UserAuthTask mAuthTask = null;
    private Button loginBtn, regBtn;
    private EditText loginArea, passArea;

    private SharedPreferences sharedPreferences = null;

    private LoginActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //TODO:Проверка соединения. Выводить сообщение об отсутствии соединения в случае ошибки
        //TODO:Возможно, нужно использовать один экземляр Connection
        //Закрывать соединение после авторизации?
        //Для сессии нужно сделать класс PreferenceManager
        sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("sessionId")) {
            String si = sharedPreferences.getString("sessionId", "");
            //Далее получаем данные пользователя по его sessionId
            mAuthTask = new UserAuthTask(context, null, null, si);
            mAuthTask.execute();
        } else {
            listenerEventOnButton();
        }
    }


    public void listenerEventOnButton() {
        loginBtn = findViewById(R.id.loginBtn);
        regBtn = findViewById(R.id.regBtn);
        loginArea = findViewById(R.id.loginArea);
        passArea = findViewById(R.id.passArea);

        loginBtn.setOnClickListener(
                v -> {
                    String login = loginArea.getText().toString();
                    String password = passArea.getText().toString();

                    if (!StringUtils.isEmptyOrWhitespaceOnly(login) &&
                            !StringUtils.isEmptyOrWhitespaceOnly(password)) {
                        mAuthTask = new UserAuthTask(context, login, password, null);
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

    public class UserAuthTask extends AsyncTask<Void, Void, Boolean> {

        private String login;
        private String password;
        private Activity parentActivity;
        private ResultSet rs;
        private String toastMessage;
        private String session;
        private String hash;

        UserAuthTask(Activity activity, String loginArea, String passArea, String s) {
            login = loginArea;
            password = passArea;
            parentActivity = activity;
            session = s;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Connection conn = DatabaseConnect.connect();
            String sql;

            if (conn != null) {
                Statement statement = null;
                try {
                    statement = conn.createStatement();
                    if (session != null) {
                        sql = "SELECT * FROM `users` WHERE `sessionId` = '" + session + "';";
                    } else {
                        sql = "SELECT * FROM `users` WHERE `login` = '" + login + "' AND `password` = '" + password + "';";
                    }
                    rs = statement.executeQuery(sql);
                    if (rs.next()) {
                        login = rs.getString("login");
                        hash = rs.getString("sessionId");

                        toastMessage = "Привет " + login;
                        conn.close();
                        return true;
                    } else {
                        toastMessage = login + "не найден";
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    toastMessage = "Ошибка доступа к базе данных";
                }
            } else {
                toastMessage = "Проверьте подключение к интернету";
            }

            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("login", login);
                startActivity(intent);
            }
            if (session == null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("sessionId", hash);
                editor.apply();
            }

            parentActivity.runOnUiThread(() -> Toast.makeText(parentActivity, toastMessage, Toast.LENGTH_SHORT).show());
        }

        @Override
        protected void onCancelled() {
        }
    }
}
