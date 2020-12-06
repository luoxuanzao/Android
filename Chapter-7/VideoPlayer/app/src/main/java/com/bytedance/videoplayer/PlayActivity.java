package com.bytedance.videoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayActivity extends AppCompatActivity {
    private VideoView videoView;
    private Button button1;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        videoView = findViewById(R.id.play);
        videoView.setVideoPath(getVideoPath(R.raw.bilibili));
        videoView.setMediaController(new MediaController(this));
        button1 = findViewById(R.id.start);
        button2 = findViewById(R.id.pause);
        if(button1!=null) {
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.start();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    videoView.pause();
                }
            });
        }
    }
    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

}
