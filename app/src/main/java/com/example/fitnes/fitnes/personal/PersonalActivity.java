package com.example.fitnes.fitnes.personal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fitnes.fitnes.LoginActivity;
import com.example.fitnes.fitnes.service.PagerAdapter;
import com.example.fitnes.fitnes.R;

public class PersonalActivity extends AppCompatActivity implements Tab1.OnFragmentInteractionListener,Tab2.OnFragmentInteractionListener,Tab3.OnFragmentInteractionListener  {

    private SharedPreferences sharedPreferences = null;
    private PersonalActivity context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        sharedPreferences = getSharedPreferences("userSession", Context.MODE_PRIVATE);

        //создание табов
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
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

@Override
public void onBackPressed() {
    // super.onBackPressed();
    //TODO:Проверить, последняя ли это активити, если да - openQuitDialog, нет - стандартное поведение кнопки
    openQuitDialog();
}

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
