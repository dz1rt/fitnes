package com.example.fitnes.fitnes.personal;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.fitnes.fitnes.ListProgram.ListProgram;
import com.example.fitnes.fitnes.ListProgram.ProgramItem;
import com.example.fitnes.fitnes.LoginActivity;
import com.example.fitnes.fitnes.R;
import com.example.fitnes.fitnes.service.PagerAdapter;
import com.example.fitnes.fitnes.service.RequestDB;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class PersonalActivity extends AppCompatActivity implements Tab1.OnFragmentInteractionListener,Tab2.OnFragmentInteractionListener,Tab3.OnFragmentInteractionListener  {

    private SharedPreferences sharedPreferences = null;
    private PersonalActivity context = this;
    private EditText growth,weight;
    private Switch switchBtn;
    private Button actionButton;
    private RequestDB requestDB;
    private ResultSet result;
    private List<ProgramItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);

        //Кнопка Далее
//        Fragment frag1 = getFragmentManager().findFragmentById(R.id.pager);
//
//        growth =  findViewById(R.id.pager).findViewById(R.id.growthEdit);
//        growth = findViewById(R.id.pager);
//        weight = findViewById(R.id.weight);
//        editTextOther = findViewById(R.id.editTextOther);
        action();

        //создание табов
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }});
    }

    private void action() {

        actionButton = findViewById(R.id.actionButton);

        actionButton.setOnClickListener(v -> {

            growth = findViewById(R.id.growthEdit); //Рост
            weight = findViewById(R.id.weight); //Вес
            switchBtn = findViewById(R.id.switch1); //свич

            requestDB = new RequestDB("SELECT * FROM `programs` WHERE `weight` >= "+weight.getText().toString()+";");
            requestDB.execute();
            try {
                result = requestDB.get();

                Intent intent = new Intent(context, ListProgram.class);
                listItems = new ArrayList<>();

                do {
                    listItems.add(new ProgramItem(result.getString("programName"),
                            result.getString("description"),
                            result.getInt("weight"),
                            result.getInt("height"),
                            result.getBoolean("switch"))) ;
                }
                while (result.next());

                intent.putExtra("result", (Serializable) listItems);
                startActivity(intent);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
public void onBackPressed() {
    // super.onBackPressed();
    //TODO:Проверить, последняя ли это активити, если да - openQuitDialog, нет - стандартное поведение кнопки
    openQuitDialog();
}
    //TODO:Вынести в отдельный класс
    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                PersonalActivity.this);
        quitDialog.setTitle("Вы уверены что хотите выйти?");

        quitDialog.setPositiveButton("Да", (dialog, which) -> {
            if(sharedPreferences.contains("sessionId")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("sessionId");
                editor.apply();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
    }
            finish();
        });

        quitDialog.setNegativeButton("Нет", (dialog, which) -> {

        });

        quitDialog.show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
