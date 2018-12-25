package com.example.fitnes.fitnes.ListProgram;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.fitnes.fitnes.R;
import com.example.fitnes.fitnes.common.NavigationBarConfig;
import com.example.fitnes.fitnes.service.ClickListener;
import com.example.fitnes.fitnes.service.RecyclerAdapter;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.util.List;

public class ListProgram extends AppCompatActivity {

    private RecyclerAdapter recyclerAdapter;
//    private List<ProgramItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_program);



        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NavigationBarConfig navigationBarConfig = new NavigationBarConfig(this,toolbar);
        navigationBarConfig.setToolBar();

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        //TODO: Intent intent = getIntent(); Получаем result, заполняем listItems
        Bundle arguments = getIntent().getExtras();
        List<ProgramItem> result = (List<ProgramItem>) arguments.get("result");
        recyclerAdapter = new RecyclerAdapter(result, this, true, (position, v,elementId) -> {

        });
        rv.setAdapter(recyclerAdapter);
}

}
