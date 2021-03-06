package com.example.fitnes.fitnes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnes.fitnes.service.DatabaseConnect;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import androidx.appcompat.app.AppCompatActivity;

public class RegActivity extends AppCompatActivity {

    private UserAuthTask mAuthTask = null;
    private EditText regLogin, regEmail, regPass, regPassConfirm;

    private RegActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        listenerEventOnButton();
    }

    private void listenerEventOnButton() {

        Button regBtn = findViewById(R.id.registration);
        regLogin = findViewById(R.id.regLogin);
        regEmail = findViewById(R.id.regEmail);
        regPass = findViewById(R.id.regPass);
        regPassConfirm = findViewById(R.id.regPassConfirm);
        Button backBtn = findViewById(R.id.back);

        regBtn.setOnClickListener(
                v -> {

                    String login = regLogin.getText().toString();
                    String email = regEmail.getText().toString();
                    String pass = regPass.getText().toString();
                    String passConfirm = regPassConfirm.getText().toString();
                    pass.equals(passConfirm);
                    if (!login.equals("") && !email.equals("") && pass.equals(passConfirm) && !pass.equals("")) {
                        mAuthTask = new UserAuthTask(context, login, email, pass);
                        mAuthTask.execute();
                    }
                }
        );
        backBtn.setOnClickListener(
                v -> {
                    Intent intent = new Intent(context,LoginActivity.class);
                    startActivity(intent);
                }
        );
    }

    public class UserAuthTask extends AsyncTask<Void, Void, Boolean> {
        //TODO: Вынести в сервис
        private final String login;
        private final String password;
        private final String email;
        private final Activity parentActivity;
        private ResultSet rs;
        private String toastMessage;

        UserAuthTask(Activity activity, String lg, String em, String pass) {
            login = lg;
            password = pass;
            email = em;
            parentActivity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Connection conn = DatabaseConnect.connect();
            if (conn != null) {
                try {
                    //Проверить есть ли такой пользак, если есть то сообщение, если нет, то создать и перекинуть на форму с логином.
                    //Проверить отдельно существование вводимого логина и отдельно email
                    // чтобы уведомить о том, что такой пользователь с таким логином/email уже существует.
                    // Не использовать конкатенацию строк в sql
                    String sql = "SELECT * FROM `users` WHERE `login` = '"
                            + login + "' AND `email` = '" + email + "';";
                    Statement statement = conn.createStatement();
                    rs = statement.executeQuery(sql);
                    if (rs.next()) {
                        toastMessage = "Такой пользователь уже существует";
                        conn.close();
                        return false;
                    } else {
                        //Регистрируем
                        //Создаём хэш
                        SecretKeySpec sks = null;
                        String hash;
                        try {
                            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
                            sr.setSeed("any data used as random seed".getBytes());
                            KeyGenerator kg = KeyGenerator.getInstance("AES");
                            kg.init(128, sr);
                            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
                        } catch (Exception e) {
                            Log.e("Crypto", "AES secret key spec error");
                        }

                        // Encode the original data with AES
                        byte[] encodedBytes = null;
                        try {
                            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
                            c.init(Cipher.ENCRYPT_MODE, sks);
                            encodedBytes = c.doFinal((login + password).getBytes());
                        } catch (Exception e) {
                            Log.e("Crypto", "AES encryption error");
                        }
                        hash = Base64.encodeToString(encodedBytes, Base64.DEFAULT);
                        sql = "INSERT INTO `users` (`id`, `login`, `password`, `email`, `sessionId`) VALUES (NULL, '" + login + "', '" + password + "', '" + email + "', '" + hash + "');";
                        statement.executeUpdate(sql);
                        //Проверять создался ли?

                        toastMessage = "Регистрация успешна";
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                // В отдельном потоке?
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
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            } else {
                //Очищать форму
            }

            parentActivity.runOnUiThread(() -> Toast.makeText(parentActivity, toastMessage, Toast.LENGTH_SHORT).show());
        }

        @Override
        protected void onCancelled() {
        }
    }
}
