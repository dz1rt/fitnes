package com.example.fitnes.fitnes.testProgram;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnes.fitnes.R;
import com.example.fitnes.fitnes.common.NavigationBarConfig;
import com.example.fitnes.fitnes.service.ClickListener;
import com.example.fitnes.fitnes.service.ModalVideo;
import com.example.fitnes.fitnes.service.RecyclerAdapter;
import com.example.fitnes.fitnes.service.YouTubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
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

public class TestTraining extends YouTubeBaseActivity {

    private RecyclerAdapter recyclerAdapter;
    private Button mvPlayBtn;

    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_training);

        mvPlayBtn = findViewById(R.id.mvPlayBtn);
        youTubePlayerView = findViewById(R.id.mvPlayer);
        YouTubeConfig onInitializedListener = new YouTubeConfig();

        mvPlayBtn.setOnClickListener(v -> {
            youTubePlayerView.initialize(
                    YouTubeConfig.getApiKey(),
                    onInitializedListener.onInitializedListenerSubscribe("X_Qo3Ayaxtg"));
        });

        findViewById(R.id.mvBackBtn).setOnClickListener(v -> {
//            Animation animation = AnimationUtils.loadAnimation(this,R.anim.fadeout);
//            findViewById(R.id.modal_video).startAnimation(animation);
            findViewById(R.id.modal_video).setVisibility(View.INVISIBLE);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
//        if (toolbar != null){
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

        NavigationBarConfig navigationBarConfig = new NavigationBarConfig(this,toolbar);
        navigationBarConfig.setToolBar();

        RecyclerView rv = findViewById(R.id.training_array);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //TODO: Intent intent = getIntent(); Получаем result, заполняем listItems
        Bundle arguments = getIntent().getExtras();
        List<TestProgramItem> result = (List<TestProgramItem>) arguments.get("result");
        recyclerAdapter = new RecyclerAdapter(result, this, false, (position, v, elementId) -> {
                if(v.findViewById(R.id.testStarterTimeBtn).getId() == elementId){
                    //Если нажата кнопка Начать
                    TextView tv =  v.findViewById(R.id.testTimer);
                    Integer millisInFuture = Integer.parseInt(tv.getText().toString());
                    //TODO: заблокировать кнопку от нажатия
                    new CountDownTimer(millisInFuture, 1000) {

                        public void onTick(long millisUntilFinished) {
                            tv.setText("" + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            tv.setText("done!");
                            recyclerAdapter.deleteItem(position);
                        }
                    }.start();
                }else if(v.findViewById(R.id.watchVideoBtn).getId() == elementId){
                    //Если нажата кнопка Смотреть
//                    Animation animation = AnimationUtils.loadAnimation(this,R.anim.fadein);
//                    findViewById(R.id.modal_video).startAnimation(animation);
                    findViewById(R.id.modal_video).setVisibility(View.VISIBLE);
                }

        }
        );
        rv.setAdapter(recyclerAdapter);
    }

    private void openModalWindow() {

    }
}
