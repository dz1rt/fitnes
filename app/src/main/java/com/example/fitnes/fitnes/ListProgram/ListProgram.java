package com.example.fitnes.fitnes.ListProgram;

import android.content.Intent;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import com.example.fitnes.fitnes.R;
import com.example.fitnes.fitnes.service.RecyclerAdapter;

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
        }

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        //TODO: Intent intent = getIntent(); Получаем result, заполняем listItems
        Bundle arguments = getIntent().getExtras();
        List<ProgramItem> result = (List<ProgramItem>) arguments.get("result");
        recyclerAdapter = new RecyclerAdapter(result, this);
        rv.setAdapter(recyclerAdapter);
}

}
