package com.example.fitnes.fitnes.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.fitnes.fitnes.MainActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RequestDB extends AsyncTask<Void, Void, ResultSet> {

    private String sql,message;
    private Statement statement;
    private ResultSet result;
    private Context context;
    private Class targetActivity;
    private Connection connection;
    private Boolean isRedirect;

    public RequestDB(String sql) {
        this.sql=sql;
//        this.context = context;
//        this.targetActivity = targetActivity;
//        this.message = message;
//        this.isRedirect = isRedirect;
        statement = null;
    }

    @Override
    protected ResultSet doInBackground(Void... params) {
        connection = DatabaseConnect.connect();
        if (connection != null) {
            try {
                statement = connection.createStatement();
                result = statement.executeQuery(this.sql);
                if(result.next()){
                    return result;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ResultSet result) {

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onPostExecute(result);
    }
}
