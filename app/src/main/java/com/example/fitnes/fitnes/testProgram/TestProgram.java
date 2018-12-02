package com.example.fitnes.fitnes.testProgram;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.fitnes.fitnes.R;

import androidx.appcompat.app.AppCompatActivity;

public class TestProgram extends AppCompatActivity {

    private Button goTestProgramBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_program);

        goTestProgramBtn = findViewById(R.id.goTestProgram);
        goTestProgramBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, TestTraining.class);
            startActivity(intent);
        });
    }
}
