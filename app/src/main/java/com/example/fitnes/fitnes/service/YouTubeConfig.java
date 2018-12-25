package com.example.fitnes.fitnes.service;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

public class YouTubeConfig extends YouTubeBaseActivity {
    private static final String API_KEY = "AIzaSyC_RPAV7SxaTaZBmwKsmdgDunHnbAgTAU0";

    YouTubePlayer.OnInitializedListener onInitializedListener;

    public YouTubeConfig() {
    }

    public YouTubePlayer.OnInitializedListener onInitializedListenerSubscribe(String videoId){
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        return onInitializedListener;
    }
    public static String getApiKey() {
        return API_KEY;
    }
}
