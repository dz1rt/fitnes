package com.example.fitnes.fitnes.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.fitnes.fitnes.LoginActivity;
import com.example.fitnes.fitnes.MainActivity;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAuthTask extends AsyncTask<Void, Void, Boolean> {

    private String login;
    private String password;
    private Activity parentActivity;
    private Activity targetActivity;
    private String toastMessage;
    private String session;
    private String hash;
    private SharedPreferences sharedPreferences;

    public UserAuthTask(Activity activity, String loginArea, String passArea, String s, SharedPreferences sp) {
        login = loginArea;
        password = passArea;
        parentActivity = activity;
        session = s;
        sharedPreferences = sp;

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
                ResultSet rs = statement.executeQuery(sql);
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
            Intent intent = new Intent(parentActivity, MainActivity.class);
            intent.putExtra("login", login);
            parentActivity.startActivity(intent);
        }
        if (session == null) {
//                Intent intent = new Intent(parentActivity, LoginActivity.class);
//                parentActivity.startActivity(intent);

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