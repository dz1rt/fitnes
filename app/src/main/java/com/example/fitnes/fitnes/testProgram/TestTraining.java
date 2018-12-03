package com.example.fitnes.fitnes.testProgram;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnes.fitnes.R;
import com.example.fitnes.fitnes.service.ClickListener;
import com.example.fitnes.fitnes.service.RecyclerAdapter;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestTraining extends AppCompatActivity {

    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_training);

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

        RecyclerView rv = findViewById(R.id.training_array);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //TODO: Intent intent = getIntent(); Получаем result, заполняем listItems
        Bundle arguments = getIntent().getExtras();
        List<TestProgramItem> result = (List<TestProgramItem>) arguments.get("result");
        recyclerAdapter = new RecyclerAdapter(result, this, false, (position, v) -> {
               TextView tv =  v.findViewById(R.id.testTimer);
               Integer millisInFuture = Integer.parseInt(tv.getText().toString());
            new CountDownTimer(millisInFuture, 1000) {

                public void onTick(long millisUntilFinished) {
                    tv.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    tv.setText("done!");
                    recyclerAdapter.deleteItem(position);
                }
            }.start();
        }

        );
        rv.setAdapter(recyclerAdapter);
    }
}
