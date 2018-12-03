package com.example.fitnes.fitnes.personal;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.fitnes.fitnes.ListProgram.ListProgram;
import com.example.fitnes.fitnes.ListProgram.ProgramItem;
import com.example.fitnes.fitnes.LoginActivity;
import com.example.fitnes.fitnes.R;
import com.example.fitnes.fitnes.service.PagerAdapter;
import com.example.fitnes.fitnes.service.RequestDB;
import com.example.fitnes.fitnes.testProgram.TestProgramItem;
import com.example.fitnes.fitnes.testProgram.TestTraining;
import com.google.android.material.tabs.TabLayout;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class PersonalActivity extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener {

    private SharedPreferences sharedPreferences = null;
    private PersonalActivity context = this;
    private EditText weight;
    private Switch flag;
    private RequestDB requestDB;
    private ResultSet result;
    private List<ProgramItem> listItems;
    private List<TestProgramItem> listTestItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
//        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Dmitriy Volkov")
                                .withEmail("dm8205@yandex.ru")
                                .withIcon(GoogleMaterial.Icon.gmd_supervisor_account)
                )
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();
        Drawer drawer = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .build();


        sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        action();

        //создание табов
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

            }
        });
    }

    private void action() {

        flag = findViewById(R.id.switch1);
        Button actionButton = findViewById(R.id.actionButton);

        actionButton.setOnClickListener(v -> {
            Boolean flagValue = true;
            if (flagValue) {
                //Основная тренеровка
                weight = findViewById(R.id.weight); //Вес
//            запрос для основной тренеровки
                requestDB = new RequestDB("SELECT * FROM `programs` WHERE `weight` >= " + weight.getText().toString() + ";");
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
                                result.getBoolean("switch")));
                    } while (result.next());
                    intent.putExtra("result", (Serializable) listItems);
                    startActivity(intent);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                //Тестовая тренеровка
                requestDB = new RequestDB("SELECT * FROM `dictionary_training` WHERE `program_type` LIKE '%test%'");
                requestDB.execute();
                try {
                    result = requestDB.get();

                    Intent intent = new Intent(context, TestTraining.class);
                    listTestItems = new ArrayList<>();

                    do {
                        listTestItems.add(new TestProgramItem(result.getString("title"),
                                result.getString("time"),
                                result.getString("description")));
                    }
                    while (result.next());
                    intent.putExtra("result", (Serializable) listTestItems);
                    startActivity(intent);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
            if (sharedPreferences.contains("sessionId")) {
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
