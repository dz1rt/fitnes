package com.example.fitnes.fitnes;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnect {
    private static final String TAG = DatabaseConnect.class.getName();

    private static String driver = "com.mysql.jdbc.Driver";

    private static String url = "jdbc:mysql://%s/%s";
    private static String host = "192.168.0.12:3306";
    private static String database = "data";
//
    private static String user = "root";
    private static String pass = "1234";

    public static Connection connect() {
        Log.i(TAG, "Init driver: " + driver);
        try {
            Class.forName(driver);
            Log.i(TAG, "Init connection");
            return DriverManager.getConnection(String.format(url, host, database), user, pass);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Can't init driver", e);
        } catch (Exception e) {
            Log.e(TAG, "Can't connect to database", e);
        }
        return null;
    }
}
